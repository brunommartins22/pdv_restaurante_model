/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.domains.DominioEvento;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.padrao.rest.util.TransformNativeQuery;
import br.com.interagese.syscontabil.domains.DominioRegras;
import br.com.interagese.syscontabil.dto.ClienteProdutoDto;
import br.com.interagese.syscontabil.models.ArquivosProcessar;
import br.com.interagese.syscontabil.models.ProdutoCenario;
import br.com.interagese.syscontabil.models.ProdutoCliente;
import br.com.interagese.syscontabil.models.ProdutoGeral;
import br.com.interagese.syscontabil.models.RegraNcm;
import br.com.interagese.syscontabil.models.RegraProduto;
import br.com.interagese.syscontabil.models.RegraRegimeTributario;
import br.com.interagese.syscontabil.models.fileProcessed.ArquivoItem;
import br.com.interagese.syscontabil.models.fileProcessed.Produto;
import br.com.interagese.syscontabil.models.fileProcessed.ProdutoCenarioJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.TypedQuery;
import org.apache.commons.lang.StringUtils;
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
    private RegraRegimeService regraRegimeService;
    @Autowired
    private RegraNcmService regraNcmService;
    @Autowired
    private ProdutoGeralService produtoGeralService;
    @Autowired
    private ProdutoCenarioService produtoCenarioService;

    public List<ClienteProdutoDto> loadProductClient(Map resp) throws Exception {

        String sql = "SELECT "
                + "  c.id AS CODIGO,"
                + "  c.tipo_regime as REGIME,"
                + "  c.cpf_cnpj AS CPF_CNPJ,"
                + "  c.razao_social AS CLIENTE,"
                + "  (select count(*) from syscontabil.produto_cliente p where cliente_id = c.id) AS TOTAL"
                + " FROM syscontabil.cliente c where c.rgevento <> '3' and ativo is true and tipo_cliente='JURIDICA' ";

        if (resp != null && resp.size() > 0) {
            String codigo = (((String) resp.get("codigo")) == null || ((String) resp.get("codigo")).isEmpty()) ? null : (String) resp.get("codigo");
            String nome = (String) resp.get("nome");
            String ordenacao = (String) resp.get("ordenacao");

            if (!StringUtils.isEmpty(codigo)) {
                codigo = Utils.retirarCaracteresEspeciais(codigo);
                if (!Utils.somenteNumeros(codigo)) {
                    addErro("Campo não pode conter Letras !!");
                }
                if (codigo.length() == 14) {
                    sql += " and c.cpf_cnpj = '" + Utils.formataStringCNPJ(codigo) + "'";
                } else {
                    addErro("Quantidade de Caracters Inválida !!");
                }
            }
            if (!StringUtils.isEmpty(nome)) {
                sql += " and c.razao_social like '%" + nome + "%'";
            }
            if (!StringUtils.isEmpty(ordenacao)) {
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
                produtoCenario.setIsCheck(false);
                produtoCenario.setStatus(produtoCenario.isDivergente() ? "Pendente" : "Validado");
                produtoCenario.setDominioRegrasInformadoBotaoDireito(produtoCenario.isConfirmado() ? produtoCenario.getDominioRegrasConfirmado() : produtoCenario.getDominioRegras());
                produtoCenario.setDominioRegrasInformado(produtoCenario.isConfirmado() ? produtoCenario.getDominioRegrasConfirmado() : produtoCenario.getDominioRegras());
                if (!StringUtils.isEmpty(produtoCenario.getProdutoCliente().getNcmPadrao()) && produtoCenario.getProdutoCliente().getCestPadrao() != null) {
                    produtoCenario.getProdutoCliente().setIsProdutoGeral(true);
                } else {
                    produtoCenario.getProdutoCliente().setIsProdutoGeral(false);
                }
                produtoCenario.setIsEdited(false);
            });
        }
        return result;
    }

    public ProdutoCenario confirmClientRule(ProdutoCenario produtoCenario) throws Exception {

        if (produtoCenario != null && produtoCenario.getProdutoCliente().isAtivo()) {

            //***************** validation produto geral ***********************
            if (StringUtils.isEmpty(produtoCenario.getProdutoCliente().getNcmInformado())) {
                addErro("Ncm não informado !!");
            } else {
                if (produtoCenario.getProdutoCliente().getNcmInformado().length() < 8) {
                    addErro("Ncm com quantidade de caracteres menor que 8 dígitos !!");
                }
            }

            if (!produtoCenario.getProdutoCliente().isIsProdutoGeral() && produtoCenario.getProdutoCliente().getEan() != null) {

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

                //******* update all product in table produtoCliente ***********
                String sqlUpdateProductClient = "update syscontabil.produto_cliente set "
                        + "ncm_padrao = '" + produtoCenario.getProdutoCliente().getNcmInformado() + "',"
                        + "cest_padrao = '" + produtoCenario.getProdutoCliente().getCestInformado() + "'"
                        + "where ean =" + produtoCenario.getProdutoCliente().getEan() + " or ncm_cliente = '" + produtoCenario.getProdutoCliente().getNcmCliente() + "'";

                em.createNativeQuery(sqlUpdateProductClient).executeUpdate();

            } else if (produtoCenario.getProdutoCliente().getEan() != null) {
                if (!produtoCenario.getProdutoCliente().getNcmInformado().equals(produtoCenario.getProdutoCliente().getNcmPadrao())) {
                    addErro("Ncm informado precisa ser corrigido para o sugerido !!");
                }

                if (!produtoCenario.getProdutoCliente().getCestInformado().equals(produtoCenario.getProdutoCliente().getCestPadrao())) {
                    addErro("Cest informado precisa ser corrigido para o sugerido !!");
                }
            }

            produtoCenario.getProdutoCliente().setNcmConfirmado(produtoCenario.getProdutoCliente().getNcmInformado());
            produtoCenario.getProdutoCliente().setCestConfirmado(produtoCenario.getProdutoCliente().getCestInformado());

            if (produtoCenario.getTributoFederalInformado() != null) {

                if (!produtoCenario.isIsEdited()) {
                    if (!produtoCenario.getTributoFederalInformado().getCstPisSaidaInformado().equals(produtoCenario.getTributoFederalPadrao().getCstPisSaidaPadrao())) {
                        addErro("CST do Pis informado precisa ser alterado para o sugerido !!");
                    }
                    if (!produtoCenario.getTributoFederalInformado().getAliquotaPisSaidaInformado().equals(produtoCenario.getTributoFederalPadrao().getAliquotaPisSaidaPadrao())) {
                        addErro("Alíquota do Icms informado precisa ser alterado para o sugerido !!");
                    }
                    if (!produtoCenario.getTributoFederalInformado().getCstCofinsSaidaInformado().equals(produtoCenario.getTributoFederalPadrao().getCstCofinsSaidaPadrao())) {
                        addErro("CST do Cofins informado precisa ser alterado para o sugerido !!");
                    }
                    if (!produtoCenario.getTributoFederalInformado().getAliquotaCofinsSaidaInformado().equals(produtoCenario.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao())) {
                        addErro("Alíquota do Cofins informado precisa ser alterado para o sugerido !!");
                    }
                    if (!produtoCenario.getTributoFederalInformado().getCstIpiSaidaInformado().equals(produtoCenario.getTributoFederalPadrao().getCstIpiSaidaPadrao())) {
                        addErro("CST do Ipi informado precisa ser alterado para o sugerido !!");
                    }
                    if (!produtoCenario.getTributoFederalInformado().getAliquotaIpiSaidaInformado().equals(produtoCenario.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao())) {
                        addErro("Alíquota do Ipi informado precisa ser alterado para o sugerido !!");
                    }
                }

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

            if (produtoCenario.getTributoEstadualInformado() != null) {

                if (!produtoCenario.isIsEdited()) {
                    if (!produtoCenario.getTributoEstadualInformado().getCstIcmsSaidaInformado().equals(produtoCenario.getTributoEstadualPadrao().getCstIcmsSaidaPadrao())) {
                        addErro("CST do Icms informado precisa ser alterado para o sugerido !!");
                    }
                    if (!produtoCenario.getTributoEstadualInformado().getAliquotaIcmsSaidaInformado().equals(produtoCenario.getTributoEstadualPadrao().getAliquotaIcmsSaidaPadrao())) {
                        addErro("Alíquota do Icms informado precisa ser alterado para o sugerido !!");
                    }
                }

                //****************************** ICMS **************************
                //****************************** Tributos de Saída ************************************
                //****************************** Update Client Exit ***********************************
                produtoCenario.getTributoEstadualPadrao().setCstIcmsSaidaPadrao(produtoCenario.getTributoEstadualInformado().getCstIcmsSaidaInformado());
                produtoCenario.getTributoEstadualPadrao().setAliquotaIcmsSaidaPadrao(produtoCenario.getTributoEstadualInformado().getAliquotaIcmsSaidaInformado());
                produtoCenario.getTributoEstadualConfirmado().setCstIcmsSaidaConfirmado(produtoCenario.getTributoEstadualInformado().getCstIcmsSaidaInformado());
                produtoCenario.getTributoEstadualConfirmado().setAliquotaIcmsSaidaConfirmado(produtoCenario.getTributoEstadualInformado().getAliquotaIcmsSaidaInformado());
            }

            switch (produtoCenario.getDominioRegrasInformado()) {
                case PRODUTO: {
                    RegraProduto regraProduto = null;
                    if (!produtoCenario.getDominioRegras().equals(produtoCenario.getDominioRegrasInformado()) && !produtoCenario.getDominioRegrasInformadoBotaoDireito().equals(produtoCenario.getDominioRegrasInformado()) && produtoCenario.isIsEdited()) {
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
                    if (!produtoCenario.getDominioRegras().equals(produtoCenario.getDominioRegrasInformado()) && !produtoCenario.getDominioRegrasInformadoBotaoDireito().equals(produtoCenario.getDominioRegrasInformado()) && produtoCenario.isIsEdited()) {
                        regraNcm = new RegraNcm();
                        regraNcm.getAtributoPadrao().setDataAlteracao(new Date());
                        regraNcm.getAtributoPadrao().setIdUsuario(1L);
                        regraNcm.getAtributoPadrao().setNomeUsuario("ADMINISTRADOR");
                        regraNcm.getAtributoPadrao().setDominioEvento(DominioEvento.I);
                        regraNcm.setNcm(produtoCenario.getProdutoCliente().getNcmInformado());
                        regraNcm.setNcmCliente(produtoCenario.getProdutoCliente().getNcmCliente());
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
            produtoCenario.setIsEdited(false);

            produtoCenarioService.update(produtoCenario);
        }else{
            addErro("Produto Inativo para confirmação !!");
        }

        return produtoCenario;
    }

    //*************************** Methods Job **********************************
    public List<Produto> loadProdutos(ArquivosProcessar arquivoSelecionado) throws Exception {
        List<Produto> produtos = new ArrayList<>();
        File arquivo = new File(arquivoSelecionado.getCaminho());

        if (!arquivo.exists()) {
            throw new Exception("Caminho inserido na base de dados inválido !!");
        }

        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        int size = 0;

        while (br.ready()) {

            String linha = br.readLine();
            if (size != 0) {
                Produto produto = null;
                System.out.println("posição(arquivo) : " + size + " registro(s)");

                ArquivoItem arquivoItem = getItem(linha, size);

                produto = produtos.stream().filter((p) -> {
                    return (p.getCodigoProduto() != null ? p.getCodigoProduto().equals(arquivoItem.getCodigoProduto()) : p.getEan() != null ? p.getEan().equals(arquivoItem.getEan()) : null);
                }).findFirst().orElse(null);

                if (produto != null && produto.isAtivo()) {
                    updateProduto(produto, arquivoItem);
                } else {
                    produto = insertProduto(arquivoSelecionado.getAtributoPadrao().getIdUsuario(), arquivoSelecionado.getAtributoPadrao().getNomeUsuario(), arquivoSelecionado.getCliente().getId(), arquivoItem);
                    produtos.add(produto);

                }

            }
            size++;
        }
        return produtos;
    }

    private Produto insertProduto(Long codUsuario, String nmUsuario, Long clienteId, ArquivoItem item) throws Exception {

        //************************ PRODUTO *************************************
        Produto produtoJob = new Produto();
        //********************** USUARIO ******************************
        produtoJob.getAtributoPadrao().setIdUsuario(codUsuario);
        produtoJob.getAtributoPadrao().setNmUsuario(nmUsuario);
        produtoJob.getAtributoPadrao().setRgEvento(1);
        produtoJob.getAtributoPadrao().setRgData(new Date());
        //**********************************************************************
        //p.setId(idProduto);
        produtoJob.setClienteId(clienteId);
        produtoJob.setCodigoProduto(item.getCodigoProduto());
        produtoJob.setEan(item.getEan());
        produtoJob.setNmProduto(item.getNmProduto());
        produtoJob.setNcm(item.getNcm());
        produtoJob.setCest(item.getCest());
        //************************* PRODUTO CENARIO ****************************
        ProdutoCenarioJob produtoCenarioJob = new ProdutoCenarioJob();
        //********************** USUARIO ******************************
        //produtoCenario.setId(idProdutoCenario);
        produtoCenarioJob.getAtributoPadrao().setIdUsuario(codUsuario);
        produtoCenarioJob.getAtributoPadrao().setNmUsuario(nmUsuario);
        produtoCenarioJob.getAtributoPadrao().setRgEvento(1);
        produtoCenarioJob.getAtributoPadrao().setRgData(new Date());
        //**********************************************************************
        produtoCenarioJob.setIdCenario(item.getCenarioId());
        //************************ tributo estadual ****************************
        //******************** ICMS ***********************
        //******************** Saida ***********************
        produtoCenarioJob.getTributoEstadualCliente().setCstIcmsSaidaCliente(item.getCstIcmsSaida());
        produtoCenarioJob.getTributoEstadualCliente().setAliquotaIcmsSaidaCliente(item.getAliquotaIcmsSaida());
        //************************ tributo federal  ****************************
        //******************** PIS ************************
        //******************** Saida ***********************
        produtoCenarioJob.getTributoFederalCliente().setCstPisSaidaCliente(item.getCstPisSaida());
        produtoCenarioJob.getTributoFederalCliente().setAliquotaPisSaidaCliente(item.getAliquotaPisSaida());
        //******************* COFINS **********************
        //******************** Saida ***********************
        produtoCenarioJob.getTributoFederalCliente().setCstCofinsSaidaCliente(item.getCstCofinsSaida());
        produtoCenarioJob.getTributoFederalCliente().setAliquotaCofinsSaidaCliente(item.getAliquotaCofinsSaida());
        //******************** IPI ************************
        //******************** Saida ***********************
        produtoCenarioJob.getTributoFederalCliente().setCstIpiSaidaCliente(item.getCstIpiSaida());
        produtoCenarioJob.getTributoFederalCliente().setAliquotaIpiSaidaCliente(item.getAliquotaIpiSaida());

        //**********************************************************************
        produtoJob.getListaProdutoCenario().add(produtoCenarioJob);

        String resp = loadValidationProductClient(item);

        if (StringUtils.isEmpty(resp)) {

            produtoJob.setAtivo(true);

            //*************************** validation Rule **************************
            String ncmPadrao = null;
            String cestPadrao = null;
            if (produtoJob.getEan() != null) {
                String sqlProdutoGeral = "SELECT o.ncm as ncm,o.cest as cest FROM syscontabil.produto_geral o WHERE o.ean =" + produtoJob.getEan();

                List<Object[]> result = em.createNativeQuery(sqlProdutoGeral).getResultList();
                if (!result.isEmpty()) {
                    ncmPadrao = (String) result.get(0)[0];
                    cestPadrao = (String) result.get(0)[1];
                }
            }

            produtoJob.setNcmPadrao(ncmPadrao);
            produtoJob.setCestPadrao(cestPadrao);

            produtoJob.setNcmInformado(produtoJob.getNcm() != null && !produtoJob.getNcm().isEmpty() ? produtoJob.getNcm() : produtoJob.getNcmPadrao() != null && !produtoJob.getNcmPadrao().isEmpty() ? produtoJob.getNcmPadrao() : null);
            produtoJob.setCestInformado(produtoJob.getCest() != null && !produtoJob.getCest().isEmpty() ? produtoJob.getCest() : produtoJob.getCestPadrao() != null && !produtoJob.getCestPadrao().isEmpty() ? produtoJob.getCestPadrao() : null);

            int cont = 1;
            boolean isExistRule = false;
            //******************************************************************
            switch (cont) {
                case 1: {
                    if (produtoJob.getEan() != null) {
                        //************************ Product Rule ****************************

                        RegraProduto regraProduto = regraProdutoService.loadRegraProduto(produtoCenarioJob.getIdCenario(), produtoJob.getEan(), produtoJob.getClienteId(), produtoJob.getCodigoProduto());

                        if (regraProduto != null) {
                            isExistRule = true;
                            produtoCenarioJob.setIdRegra(regraProduto.getId());
                            produtoCenarioJob.setNmRegra("PRODUTO");
                            produtoCenarioJob.setTributoEstadualPadrao(regraProduto.getTributoEstadualPadrao());
                            produtoCenarioJob.setTributoFederalPadrao(regraProduto.getTributoFederalPadrao());
                            break;
                        }
                    }
                    cont++;
                }
                case 2: {
                    if (produtoJob.getNcm() != null) {
                        //************************ Ncm Rule ****************************

                        RegraNcm regraNcm = regraNcmService.loadRegraNcm(produtoCenarioJob.getIdCenario(), produtoJob.getNcm(), produtoJob.getClienteId());

                        if (regraNcm != null) {
                            isExistRule = true;
                            produtoCenarioJob.setIdRegra(regraNcm.getId());
                            produtoCenarioJob.setNmRegra("NCM");
                            produtoCenarioJob.setTributoEstadualPadrao(regraNcm.getTributoEstadualPadrao());
                            produtoCenarioJob.setTributoFederalPadrao(regraNcm.getTributoFederalPadrao());
                            break;
                        }
                    }
                    cont++;
                }
                case 3: {
                    if (produtoJob.getClienteId() != null) {
                        //************************ Regime Tributário Rule ****************************

                        RegraRegimeTributario regimeTributario = regraRegimeService.loadRegraRegimeTributario(produtoCenarioJob.getIdCenario(), produtoJob.getClienteId());

                        if (regimeTributario != null) {
                            isExistRule = true;
                            produtoCenarioJob.setIdRegra(regimeTributario.getId());
                            produtoCenarioJob.setNmRegra("REGIME");
                            produtoCenarioJob.setTributoEstadualPadrao(regimeTributario.getTributoEstadualPadrao());
                            produtoCenarioJob.setTributoFederalPadrao(regimeTributario.getTributoFederalPadrao());
                        }

                    }
                }
            }

            if (!isExistRule) {
                throw new Exception("Nenhuma regra cadastrada para processar os dados submetidos do cliente !!");
            }
        } else {
            produtoJob.setAtivo(false);
            produtoJob.setLog(resp);
        }

        return produtoJob;

    }

    private void updateProduto(Produto produto, ArquivoItem a) throws Exception {

        ProdutoCenarioJob produtoCenario = produto.getListaProdutoCenario().stream().filter((pc) -> {
            return pc.getIdCenario().equals(a.getCenarioId()) && ((produto.getCodigoProduto() != null && !produto.getCodigoProduto().isEmpty() && produto.getCodigoProduto().equals(a.getCodigoProduto())) || (produto.getEan() != null && produto.getEan().equals(a.getEan())));
        }).findFirst().orElse(null);

        if (produtoCenario == null) {
            //************************* PRODUTO CENARIO ****************************
            produtoCenario = new ProdutoCenarioJob();
            //produtoCenario.setId(p.getListaProdutoCenario().get(p.getListaProdutoCenario().size() - 1).getId() + 1L);
            //********************** USUARIO ******************************
            produtoCenario.getAtributoPadrao().setIdUsuario(produto.getAtributoPadrao().getIdUsuario());
            produtoCenario.getAtributoPadrao().setNmUsuario(produto.getAtributoPadrao().getNmUsuario());
            produtoCenario.getAtributoPadrao().setRgEvento(1);
            produtoCenario.getAtributoPadrao().setRgData(new Date());
            //**********************************************************************
            //produtoCenario.setIdProduto(p.getId());
            produtoCenario.setIdCenario(a.getCenarioId());
            //************************ tributo estadual ****************************
            //******************** ICMS ***********************
            //******************** Saida ***********************
            produtoCenario.getTributoEstadualCliente().setCstIcmsSaidaCliente(a.getCstIcmsSaida());
            produtoCenario.getTributoEstadualCliente().setAliquotaIcmsSaidaCliente(a.getAliquotaIcmsSaida());

            //************************ tributo federal  ****************************
            //******************** PIS ************************
            //******************** Saida ***********************
            produtoCenario.getTributoFederalCliente().setCstPisSaidaCliente(a.getCstPisSaida());
            produtoCenario.getTributoFederalCliente().setAliquotaPisSaidaCliente(a.getAliquotaPisSaida());
            //******************* COFINS **********************
            //******************** Saida ***********************
            produtoCenario.getTributoFederalCliente().setCstCofinsSaidaCliente(a.getCstCofinsSaida());
            produtoCenario.getTributoFederalCliente().setAliquotaCofinsSaidaCliente(a.getAliquotaCofinsSaida());
            //******************** IPI ************************
            //******************** Saida ***********************
            produtoCenario.getTributoFederalCliente().setCstIpiSaidaCliente(a.getCstIpiSaida());
            produtoCenario.getTributoFederalCliente().setAliquotaIpiSaidaCliente(a.getAliquotaIpiSaida());

            produto.getListaProdutoCenario().add(produtoCenario);

            //************************ validation rule *************************
            int cont = 1;
            boolean isExistRule = false;
            //******************************************************************
            switch (cont) {
                case 1: {
                    if (produto.getEan() != null) {
                        //************************ Product Rule ****************************

                        RegraProduto regraProduto = regraProdutoService.loadRegraProduto(produtoCenario.getIdCenario(), produto.getEan(), produto.getClienteId(), produto.getCodigoProduto());

                        if (regraProduto != null) {
                            isExistRule = true;
                            produtoCenario.setIdRegra(regraProduto.getId());
                            produtoCenario.setNmRegra("PRODUTO");
                            produtoCenario.setTributoEstadualPadrao(regraProduto.getTributoEstadualPadrao());
                            produtoCenario.setTributoFederalPadrao(regraProduto.getTributoFederalPadrao());
                            break;
                        }
                    }
                    cont++;
                }
                case 2: {
                    if (produto.getNcm() != null) {
                        //************************ Ncm Rule ****************************

                        RegraNcm regraNcm = regraNcmService.loadRegraNcm(produtoCenario.getIdCenario(), produto.getNcm(), produto.getClienteId());

                        if (regraNcm != null) {
                            isExistRule = true;
                            produtoCenario.setIdRegra(regraNcm.getId());
                            produtoCenario.setNmRegra("NCM");
                            produtoCenario.setTributoEstadualPadrao(regraNcm.getTributoEstadualPadrao());
                            produtoCenario.setTributoFederalPadrao(regraNcm.getTributoFederalPadrao());
                            break;
                        }
                    }
                    cont++;
                }
                case 3: {
                    if (produto.getClienteId() != null) {
                        //************************ Regime Tributário Rule ****************************

                        RegraRegimeTributario regimeTributario = regraRegimeService.loadRegraRegimeTributario(produtoCenario.getIdCenario(), produto.getClienteId());

                        if (regimeTributario != null) {
                            isExistRule = true;
                            produtoCenario.setIdRegra(regimeTributario.getId());
                            produtoCenario.setNmRegra("REGIME");
                            produtoCenario.setTributoEstadualPadrao(regimeTributario.getTributoEstadualPadrao());
                            produtoCenario.setTributoFederalPadrao(regimeTributario.getTributoFederalPadrao());
                        }

                    }
                }
            }
            if (!isExistRule) {
                throw new Exception("Nenhuma regra cadastrada para processar os dados submetidos do cliente !!");
            }
        }
    }

    private ArquivoItem getItem(String linha, Integer index) {

        int i = 0;
        ArquivoItem arq = new ArquivoItem();
        arq.setIndex(index);

        for (String field : linha.split(";")) {

            switch (i) {
                case 0: {
                    arq.setCenarioId(field != null && !field.isEmpty() ? Long.parseLong(field) : null);
                    break;
                }
                case 1: {
                    arq.setCodigoProduto(field.trim());
                    break;
                }
                case 2: {
                    if (!Utils.somenteNumeros(field)) {
                        field = null;
                    }
                    arq.setEan(field != null && !field.isEmpty() ? Long.parseLong(field) : null);
                    break;
                }
                case 3: {
                    arq.setNmProduto(field);
                    break;
                }
                case 4: {
                    arq.setNcm(field.trim());
                    break;
                }
                case 5: {
                    arq.setCest(field.trim());
                    break;
                }
                case 6: {
                    arq.setCstIcmsSaida(field);
                    break;
                }
                case 7: {
                    arq.setAliquotaIcmsSaida(Double.parseDouble(field == null || field.isEmpty() ? "0.0" : field.contains(",") ? field.replace(".", "").trim().replace(",", ".") : field));
                    break;
                }
                case 8: {
                    arq.setCstPisSaida(field.trim());
                    break;
                }
                case 9: {
                    arq.setAliquotaPisSaida(Double.parseDouble(field == null || field.isEmpty() ? "0.0" : field.contains(",") ? field.replace(".", "").trim().replace(",", ".") : field));
                    break;
                }
                case 10: {
                    arq.setCstCofinsSaida(field.trim());
                    break;
                }
                case 11: {
                    arq.setAliquotaCofinsSaida(Double.parseDouble(field == null || field.isEmpty() ? "0.0" : field.contains(",") ? field.replace(".", "").trim().replace(",", ".") : field));
                    break;
                }
                case 12: {
                    arq.setCstIpiSaida(field.trim());
                    break;
                }
                case 13: {
                    arq.setAliquotaIpiSaida(Double.parseDouble(field == null || field.isEmpty() ? "0.0" : field.contains(",") ? field.replace(".", "").trim().replace(",", ".") : field));
                    break;
                }
            }
            i++;

        }

        return arq;
    }

    public Produto loadValidacaoProduto(Long clienteId, String codigoProduto, Long ean) throws Exception {

        List<Object[]> resultProduto = em.createNativeQuery("SELECT id,cliente_id,nome_produto,codigo_produto,ean,ncm_cliente,cest_cliente,ncm_padrao,cest_padrao,ncm_informado,cest_informado FROM syscontabil.produto_cliente where cliente_id =" + clienteId + " and (codigo_produto = '" + codigoProduto + "' or ean = " + ean + ")").getResultList();
        Produto produto = null;
        if (!resultProduto.isEmpty()) {
            //************************ PRODUTO *************************************
            produto = new Produto();
            //**********************************************************************
            produto.setId(resultProduto.get(0)[0] != null ? ((BigInteger) resultProduto.get(0)[0]).longValue() : null);
            produto.setClienteId(resultProduto.get(0)[1] != null ? ((BigInteger) resultProduto.get(0)[1]).longValue() : null);
            produto.setNmProduto(resultProduto.get(0)[2] != null ? (String) resultProduto.get(0)[2] : "");
            produto.setCodigoProduto(resultProduto.get(0)[3] != null ? (String) resultProduto.get(0)[3] : "");
            produto.setEan(resultProduto.get(0)[4] != null ? ((BigInteger) resultProduto.get(0)[4]).longValue() : null);
            produto.setNcm(resultProduto.get(0)[5] != null ? (String) resultProduto.get(0)[5] : "");
            produto.setCest(resultProduto.get(0)[6] != null ? (String) resultProduto.get(0)[6] : "");
            produto.setNcmPadrao(resultProduto.get(0)[7] != null ? (String) resultProduto.get(0)[7] : "");
            produto.setCestPadrao(resultProduto.get(0)[8] != null ? (String) resultProduto.get(0)[8] : "");
            produto.setNcmInformado(resultProduto.get(0)[9] != null ? (String) resultProduto.get(0)[9] : "");
            produto.setCestInformado(resultProduto.get(0)[10] != null ? (String) resultProduto.get(0)[10] : "");

        }

        if (produto != null) {
            List<Object[]> resultProdutoCenario = em.createNativeQuery("select id,produto_cliente_id,cenario_id,"
                    + "cst_icms_saida_cliente,aliquota_icms_saida_cliente,"
                    + "cst_pis_saida_cliente,aliquota_pis_saida_cliente,"
                    + "cst_cofins_saida_cliente,aliquota_cofins_saida_cliente,"
                    + "cst_ipi_saida_cliente,aliquota_ipi_saida_cliente "
                    + "from syscontabil.produto_cenario where produto_cliente_id='" + produto.getId() + "'").getResultList();

            for (Object[] o : resultProdutoCenario) {

                //************************* PRODUTO CENARIO ****************************
                ProdutoCenarioJob produtoCenario = new ProdutoCenarioJob();

                produtoCenario.setId(o[0] != null ? ((BigInteger) o[0]).longValue() : null);
                //**********************************************************************
                produtoCenario.setIdProduto(o[1] != null ? ((BigInteger) o[1]).longValue() : null);
                produtoCenario.setIdCenario(o[2] != null ? ((BigInteger) o[2]).longValue() : null);
                //************************ tributo estadual ****************************
                //******************** ICMS ***********************
                //******************** Saida ***********************
                produtoCenario.getTributoEstadualCliente().setCstIcmsSaidaCliente(o[3] != null ? (String) o[3] : "");
                produtoCenario.getTributoEstadualCliente().setAliquotaIcmsSaidaCliente(o[4] != null ? (Double) o[4] : 0.0);

                //************************ tributo federal  ****************************
                //******************** PIS ************************
                //******************** Saida ***********************
                produtoCenario.getTributoFederalCliente().setCstPisSaidaCliente(o[5] != null ? (String) o[5] : "");
                produtoCenario.getTributoFederalCliente().setAliquotaPisSaidaCliente(o[6] != null ? (Double) o[6] : 0.0);
                //******************* COFINS **********************
                //******************** Saida ***********************
                produtoCenario.getTributoFederalCliente().setCstCofinsSaidaCliente(o[7] != null ? (String) o[7] : "");
                produtoCenario.getTributoFederalCliente().setAliquotaCofinsSaidaCliente(o[8] != null ? (Double) o[8] : 0.0);
                //******************** IPI ************************
                //******************** Saida ***********************
                produtoCenario.getTributoFederalCliente().setCstIpiSaidaCliente(o[9] != null ? (String) o[9] : "");
                produtoCenario.getTributoFederalCliente().setAliquotaIpiSaidaCliente(o[10] != null ? (Double) o[10] : 0.0);

                //**********************************************************************
                produto.getListaProdutoCenario().add(produtoCenario);
            }
        }

        return produto;

    }

    public String loadValidationProductClient(ArquivoItem item) {
        String text = "";

        if (item.getEan() == null && StringUtils.isEmpty(item.getCodigoProduto())) {
            text = "Código do Produto fornecido pelo cliente encontra-se com valor nulo. \r\n";
        } else if (item.getEan().toString().length() < 8) {
            text = "Ean encontra-se com qtd de caracteres menor que 8 dígitos. \r\n";
        } else if (item.getEan().toString().length() > 8 && item.getEan().toString().length() < 12) {
            text = "Ean encontra-se com quantidade de caracteres inválida. \r\n";
        } else if (item.getEan().toString().length() > 14) {
            text = "Ean encontra-se com quantidade de caracteres acima do permitido. \r\n";
        }

        if (StringUtils.isEmpty(item.getNcm())) {
            text += "NCM fornecido pelo cliente encontra-se com valor nulo. \r\n";
        }

        if (StringUtils.isEmpty(item.getNmProduto())) {
            text += "Nome do produto fornecido pelo cliente enotra-se com valor nulo.";
        }

        return text;
    }

}
