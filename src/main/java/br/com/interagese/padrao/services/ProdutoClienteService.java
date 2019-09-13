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
import br.com.interagese.syscontabil.models.RegraRegimeTributario;
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

    public List<ClienteProdutoDto> loadProductClient() throws Exception {

        String sql = "SELECT "
                + "  c.id AS CODIGO,"
                + "  c.tipo_regime as REGIME,"
                + "  c.cpf_cnpj AS CPF_CNPJ,"
                + "  c.razao_social AS CLIENTE,"
                + "  (select count(*) from syscontabil.produto_cliente p where cliente_id = c.id) AS TOTAL"
                + " FROM syscontabil.cliente c ";

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
                produtoCenario.setDominioRegrasInformado(produtoCenario.getDominioRegras());
                if (produtoCenario.getProdutoCliente().getNcmPadrao() != null && produtoCenario.getProdutoCliente().getCestPadrao() != null) {
                    produtoCenario.getProdutoCliente().setIsProdutoGeral(true);
                }
            });
        }
        return result;
    }

    public void confirmClientRule(ProdutoCenario produtoCenario) throws Exception {

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
                geral.getAtributoPadrao().setNomeUsuario("ADMIN");
                geral.getAtributoPadrao().setDominioEvento(DominioEvento.I);

                produtoGeralService.create(geral);

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
                        regraProduto.setAtributoPadrao(produtoCenario.getAtributoPadrao());
                        regraProduto.setCenario(produtoCenario.getCenario());
                        regraProduto.setCliente(produtoCenario.getProdutoCliente().getCliente());
                        regraProduto.setCodigoProduto(produtoCenario.getProdutoCliente().getCodigoProduto());
                        regraProduto.setEanProduto(produtoCenario.getProdutoCliente().getEan());
                        regraProduto.setRegimeTributario(produtoCenario.getProdutoCliente().getCliente().getTipoRegime());
                        regraProduto.setTributoFederalPadrao(produtoCenario.getTributoFederalPadrao());
                        regraProduto.setTributoEstadualPadrao(produtoCenario.getTributoEstadualPadrao());
                        regraProdutoService.create(regraProduto);
                        produtoCenarioService.changeRule(DominioRegras.PRODUTO, regraProduto);
                    } else {
                        //********************* criar metodo ********************
                       produtoCenarioService.updateRule(DominioRegras.PRODUTO, regraProduto);
                    }

                    break;
                }
                case NCM: {
                    if (!produtoCenario.getDominioRegras().equals(produtoCenario.getDominioRegrasInformado())) {
                        RegraNcm regraNcm = new RegraNcm();
                        regraNcm.setAtributoPadrao(produtoCenario.getAtributoPadrao());
                        regraNcm.setNcm(produtoCenario.getProdutoCliente().getNcmInformado());
                        regraNcm.setRegimeTributario(produtoCenario.getProdutoCliente().getCliente().getTipoRegime());
                        regraNcm.setCenario(produtoCenario.getCenario());
                        regraNcm.setCliente(produtoCenario.getProdutoCliente().getCliente());
                        regraNcm.setTributoFederalPadrao(produtoCenario.getTributoFederalPadrao());
                        regraNcm.setTributoEstadualPadrao(produtoCenario.getTributoEstadualPadrao());
                    } else {

                    }
                    break;
                }
                case REGIME: {
//                    if (!produtoCenario.getDominioRegras().equals(produtoCenario.getDominioRegrasInformado())) {
//                        RegraRegimeTributario regraRegimeTributario = new RegraRegimeTributario();
//                        regraRegimeTributario.setAtributoPadrao(produtoCenario.getAtributoPadrao());
//                        regraRegimeTributario.setRegimeTributario(produtoCenario.getProdutoCliente().getCliente().getTipoRegime());
//                        regraRegimeTributario.setCenario(produtoCenario.getCenario());
//                        regraRegimeTributario.setTributoFederalPadrao(produtoCenario.getTributoFederalPadrao());
//                        regraRegimeTributario.setTributoEstadualPadrao(produtoCenario.getTributoEstadualPadrao());
//                    } else {
//
//                    }
                    break;
                }
            }

            produtoCenario.setDivergente(false);
            produtoCenario.setConfirmado(true);

            produtoCenarioService.update(produtoCenario);
        }
    }

}
