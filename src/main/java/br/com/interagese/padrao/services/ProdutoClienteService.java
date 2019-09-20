/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.domains.DominioEvento;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.padrao.rest.util.TransformNativeQuery;
import br.com.interagese.syscontabil.domains.DominioRegras;
import br.com.interagese.syscontabil.dto.ClienteProdutoDto;
import br.com.interagese.syscontabil.models.ProdutoCenario;
import br.com.interagese.syscontabil.models.ProdutoCliente;
import br.com.interagese.syscontabil.models.ProdutoGeral;
import br.com.interagese.syscontabil.models.RegraNcm;
import br.com.interagese.syscontabil.models.RegraProduto;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class ProdutoClienteService extends PadraoService<ProdutoCliente> {

    @Autowired
    private RegraProdutoService regraProdutoService;
    @Autowired
    private RegraRegimeService regraRegimeTributarioService;
    @Autowired
    private RegraNcmService regraNcmService;
    @Autowired
    private ProdutoGeralService produtoGeralService;
    @Autowired
    private ProdutoCenarioService produtoCenarioService;
    @Autowired
    private CenarioService cenarioService;

    public List<ClienteProdutoDto> loadProductClient(Map resp) throws Exception {

        String sql = "SELECT "
                + "  c.id AS CODIGO,"
                + "  c.tipo_regime as REGIME,"
                + "  c.cpf_cnpj AS CPF_CNPJ,"
                + "  c.razao_social AS CLIENTE,"
                + "  (select count(*) from syscontabil.produto_cliente p where cliente_id = c.id) AS TOTAL"
                + " FROM syscontabil.cliente c where c.rgevento <> '3'";

        if (resp != null && resp.size() > 0) {
            Long codigo = (((String) resp.get("codigo")) == null || ((String) resp.get("codigo")).isEmpty()) ? null : Long.parseLong((String) resp.get("codigo"));
            String nome = (String) resp.get("nome");
            String tipoPessoa = (String) resp.get("tipoPessoa");
            String ordenacao = (String) resp.get("ordenacao");

            if (codigo != null) {
                sql += " and c.id = " + codigo + "";
            }
            if (nome != null && !nome.isEmpty()) {
                sql += " and c.razao_social like '%" + nome + "%'";
            }
            if (tipoPessoa != null && !tipoPessoa.isEmpty()) {
                sql += " and c.tipo_cliente = '" + tipoPessoa + "'";
            }
            if (ordenacao != null && !ordenacao.isEmpty()) {
                sql += " order by " + ordenacao;
            }
        }

        List<Object[]> result = em.createNativeQuery(sql).getResultList();

        List<ClienteProdutoDto> resultClienteProduto = new TransformNativeQuery(ClienteProdutoDto.class).execute(result);

        return resultClienteProduto;

    }

    public List<ProdutoCliente> loadProductClientById(BigInteger clienteId) {
        if (clienteId != null) {
            String sql = "select o from ProdutoCliente o where o.cliente.id = '" + clienteId + "' order by o.nomeProduto";
            TypedQuery<ProdutoCliente> query = em.createQuery(sql, ProdutoCliente.class);

            return query.getResultList();

        }
        return null;
    }

    public List<ProdutoCenario> loadProductClientRule(Map resp) throws Exception {

        List<ProdutoCenario> result = produtoCenarioService.loadProdutoCenarioByClienteById(resp);
        if (result == null || result.isEmpty()) {
            throw new Exception("Nenhum registro encontrado na base de dados !!");
        } else {
            result.forEach((produtoCenario) -> {
                produtoCenario.setStatus(produtoCenario.isDivergente() ? "Pendente" : "Validado");
                produtoCenario.setDominioRegrasInformado(produtoCenario.isConfirmado() ? produtoCenario.getDominioRegrasConfirmado() : produtoCenario.getDominioRegras());
                if ((produtoCenario.getProdutoCliente().getNcmPadrao() != null && !produtoCenario.getProdutoCliente().getNcmPadrao().isEmpty()) && (produtoCenario.getProdutoCliente().getCestPadrao() != null && !produtoCenario.getProdutoCliente().getCestPadrao().isEmpty())) {
                    produtoCenario.getProdutoCliente().setIsProdutoGeral(true);
                } else {
                    produtoCenario.getProdutoCliente().setIsProdutoGeral(false);
                }
            });
        }
        return result;
    }

    public ProdutoCenario confirmClientRule(ProdutoCenario produtoCenario) throws Exception {

        if (produtoCenario != null) {

            //***************** validation produto geral ***********************
            if (!produtoCenario.getProdutoCliente().isIsProdutoGeral()) {

                if (produtoCenario.getProdutoCliente().getNcmInformado() == null || produtoCenario.getProdutoCliente().getNcmInformado().equals("")) {
                    addErro("Ncm não informado !!");
                }

                if (produtoCenario.getProdutoCliente().getCestInformado() == null || produtoCenario.getProdutoCliente().getCestInformado().equals("")) {
                    addErro("Cest não informado !!");
                }

                ProdutoGeral geral = new ProdutoGeral();

                geral.setEan(produtoCenario.getProdutoCliente().getEan());
                geral.setNomeProduto(produtoCenario.getProdutoCliente().getNomeProduto());
                geral.setNcm(produtoCenario.getProdutoCliente().getNcmInformado());
                geral.setCest(produtoCenario.getProdutoCliente().getCestInformado());
                geral.getAtributoPadrao().setDataAlteracao(new Date());
                geral.getAtributoPadrao().setIdUsuario(1L);
                geral.getAtributoPadrao().setNomeUsuario("INTER");
                geral.getAtributoPadrao().setDominioEvento(DominioEvento.I);

                produtoGeralService.create(geral);

                produtoCenario.getProdutoCliente().setIsProdutoGeral(true);
                produtoCenario.getProdutoCliente().setNcmPadrao(produtoCenario.getProdutoCliente().getNcmInformado());
                produtoCenario.getProdutoCliente().setCestPadrao(produtoCenario.getProdutoCliente().getCestInformado());

            }

            produtoCenario.getProdutoCliente().setNcmConfirmado(produtoCenario.getProdutoCliente().getNcmInformado());
            produtoCenario.getProdutoCliente().setCestConfirmado(produtoCenario.getProdutoCliente().getCestInformado());

            if (produtoCenario.getTributoEstadualInformado() != null) {

                //****************************** ICMS **************************
                //****************************** Tributos de Saída ************************************
                //****************************** Update Client Exit ***********************************
                produtoCenario.getTributoEstadualPadrao().setCstIcmsSaidaPadrao(produtoCenario.getTributoEstadualInformado().getCstIcmsSaidaInformado());
                produtoCenario.getTributoEstadualPadrao().setAliquotaIcmsSaidaPadrao(produtoCenario.getTributoEstadualInformado().getAliquotaIcmsSaidaInformado());
                produtoCenario.getTributoEstadualConfirmado().setCstIcmsSaidaConfirmado(produtoCenario.getTributoEstadualInformado().getCstIcmsSaidaInformado());
                produtoCenario.getTributoEstadualConfirmado().setAliquotaIcmsSaidaConfirmado(produtoCenario.getTributoEstadualInformado().getAliquotaIcmsSaidaInformado());
            }

            if (produtoCenario.getTributoFederalInformado() != null) {

                //************************* Pis ********************************
                //****************************** Tributos de Saída ************************************
                //****************************** Update Client Exit ***********************************
                produtoCenario.getTributoFederalPadrao().setCstPisSaidaPadrao(produtoCenario.getTributoFederalInformado().getCstPisSaidaInformado());
                produtoCenario.getTributoFederalPadrao().setAliquotaPisSaidaPadrao(produtoCenario.getTributoFederalInformado().getAliquotaPisSaidaInformado());
                produtoCenario.getTributoFederalConfirmado().setCstPisSaidaConfirmado(produtoCenario.getTributoFederalInformado().getCstPisSaidaInformado());
                produtoCenario.getTributoFederalConfirmado().setAliquotaPisSaidaConfirmado(produtoCenario.getTributoFederalInformado().getAliquotaPisSaidaInformado());
                //************************* Cofins *****************************
                //****************************** Tributos de Saída ************************************
                //****************************** Update Client Exit ***********************************
                produtoCenario.getTributoFederalPadrao().setCstCofinsSaidaPadrao(produtoCenario.getTributoFederalInformado().getCstCofinsSaidaInformado());
                produtoCenario.getTributoFederalPadrao().setAliquotaCofinsSaidaPadrao(produtoCenario.getTributoFederalInformado().getAliquotaCofinsSaidaInformado());
                produtoCenario.getTributoFederalConfirmado().setCstCofinsSaidaConfirmado(produtoCenario.getTributoFederalInformado().getCstCofinsSaidaInformado());
                produtoCenario.getTributoFederalConfirmado().setAliquotaCofinsSaidaConfirmado(produtoCenario.getTributoFederalInformado().getAliquotaCofinsSaidaInformado());
                //**************************** Ipi *****************************
                //****************************** Tributos de Saída ************************************
                //****************************** Update Client Exit ***********************************
                produtoCenario.getTributoFederalPadrao().setCstIpiSaidaPadrao(produtoCenario.getTributoFederalInformado().getCstIpiSaidaInformado());
                produtoCenario.getTributoFederalPadrao().setAliquotaIpiSaidaPadrao(produtoCenario.getTributoFederalInformado().getAliquotaIpiSaidaInformado());
                produtoCenario.getTributoFederalConfirmado().setCstIpiSaidaConfirmado(produtoCenario.getTributoFederalInformado().getCstIpiSaidaInformado());
                produtoCenario.getTributoFederalConfirmado().setAliquotaIpiSaidaConfirmado(produtoCenario.getTributoFederalInformado().getAliquotaIpiSaidaInformado());

            }

            switch (produtoCenario.getDominioRegrasInformado()) {
                case PRODUTO: {
                    RegraProduto regraProduto = null;
                    if (!produtoCenario.getDominioRegras().equals(produtoCenario.getDominioRegrasInformado())) {
                        regraProduto = new RegraProduto();
                        regraProduto.getAtributoPadrao().setDataAlteracao(new Date());
                        regraProduto.getAtributoPadrao().setIdUsuario(1L);
                        regraProduto.getAtributoPadrao().setNomeUsuario("ADMINISTRADOR");
                        regraProduto.getAtributoPadrao().setDominioEvento(DominioEvento.I);
                        regraProduto.setCenario(produtoCenario.getCenario());
                        if (produtoCenario.getProdutoCliente().getEan() != null) {
                            regraProduto.setEanProduto(produtoCenario.getProdutoCliente().getEan());
                        } else {
                            regraProduto.setCliente(produtoCenario.getProdutoCliente().getCliente());
                            regraProduto.setCodigoProduto(produtoCenario.getProdutoCliente().getCodigoProduto());
                        }
                        regraProduto.setRegimeTributario(produtoCenario.getProdutoCliente().getCliente().getTipoRegime());
                        regraProduto.setTributoFederalPadrao(produtoCenario.getTributoFederalPadrao());
                        regraProduto.setTributoEstadualPadrao(produtoCenario.getTributoEstadualPadrao());
                        regraProdutoService.create(regraProduto);
                        produtoCenario.setRegraId(regraProduto.getId());

                    } else {
                        regraProduto = regraProdutoService.findById(produtoCenario.getRegraId());
                        regraProduto.getAtributoPadrao().setDataAlteracao(new Date());
                        regraProduto.getAtributoPadrao().setIdUsuario(1L);
                        regraProduto.getAtributoPadrao().setNomeUsuario("ADMINISTRADOR");
                        regraProduto.getAtributoPadrao().setDominioEvento(DominioEvento.A);
                        if (produtoCenario.getProdutoCliente().getEan() != null) {
                            regraProduto.setEanProduto(produtoCenario.getProdutoCliente().getEan());
                        } else {
                            regraProduto.setCliente(produtoCenario.getProdutoCliente().getCliente());
                            regraProduto.setCodigoProduto(produtoCenario.getProdutoCliente().getCodigoProduto());
                        }
                        regraProduto.setTributoFederalPadrao(produtoCenario.getTributoFederalPadrao());
                        regraProduto.setTributoEstadualPadrao(produtoCenario.getTributoEstadualPadrao());
                        produtoCenarioService.updateRule(DominioRegras.PRODUTO, regraProduto);
                    }

                    break;
                }
                case NCM: {
                    RegraNcm regraNcm = null;
                    if (!produtoCenario.getDominioRegras().equals(produtoCenario.getDominioRegrasInformado())) {
                        regraNcm = new RegraNcm();
                        regraNcm.getAtributoPadrao().setDataAlteracao(new Date());
                        regraNcm.getAtributoPadrao().setIdUsuario(1L);
                        regraNcm.getAtributoPadrao().setNomeUsuario("ADMINISTRADOR");
                        regraNcm.getAtributoPadrao().setDominioEvento(DominioEvento.I);
                        regraNcm.setNcm(produtoCenario.getProdutoCliente().getNcmInformado());
                        regraNcm.setRegimeTributario(produtoCenario.getProdutoCliente().getCliente().getTipoRegime());
                        regraNcm.setCenario(produtoCenario.getCenario());
                        regraNcm.setTributoFederalPadrao(produtoCenario.getTributoFederalPadrao());
                        regraNcm.setTributoEstadualPadrao(produtoCenario.getTributoEstadualPadrao());
                        regraNcmService.create(regraNcm);
                        produtoCenario.setRegraId(regraNcm.getId());

                    } else {
                        regraNcm = regraNcmService.findById(produtoCenario.getRegraId());
                        regraNcm.getAtributoPadrao().setDataAlteracao(new Date());
                        regraNcm.getAtributoPadrao().setIdUsuario(1L);
                        regraNcm.getAtributoPadrao().setNomeUsuario("ADMINISTRADOR");
                        regraNcm.getAtributoPadrao().setDominioEvento(DominioEvento.A);
                        regraNcm.setNcm(produtoCenario.getProdutoCliente().getNcmInformado());
                        regraNcm.setTributoFederalPadrao(produtoCenario.getTributoFederalPadrao());
                        regraNcm.setTributoEstadualPadrao(produtoCenario.getTributoEstadualPadrao());
                        produtoCenarioService.updateRule(DominioRegras.NCM, regraNcm);
                    }
                    break;
                }
                case REGIME: {

                    break;
                }
            }

            produtoCenario.setDominioRegrasConfirmado(produtoCenario.getDominioRegrasInformado());
            produtoCenario.setDivergente(false);
            produtoCenario.setConfirmado(true);
            produtoCenario.setStatus("Validado");

            produtoCenarioService.update(produtoCenario);
        }

        return produtoCenario;
    }

}
