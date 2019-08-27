/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.domains.DominioRegras;
import br.com.interagese.syscontabil.models.ProdutoCenario;
import br.com.interagese.syscontabil.models.RegraNcm;
import br.com.interagese.syscontabil.models.RegraProduto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class ProdutoCenarioService extends PadraoService<ProdutoCenario> {

    public List<ProdutoCenario> loadProdutoCenarioByClienteById(Long idCliente, Long idCenario) {
        String sql = "SELECT o FROM ProdutoCenario o where o.produtoCliente.cliente.id = :cliente and o.cenario.id = :id";

        List<ProdutoCenario> result = em.createQuery(sql).setParameter("cliente", idCliente).setParameter("id", idCenario).getResultList();

        return result;
    }

    public void changeRule(DominioRegras dominioRegra, Object regra) throws Exception {
        if (dominioRegra != null && regra != null) {
            
//            if(dominioRegra == DominioRegras.REGIME){ 
//                RegraRegimeTributario rrt = (RegraRegimeTributario) regra;
//            }
            
            if(dominioRegra == DominioRegras.NCM){
                /*
                prioridade regra tributaria da menor para maior prioridade
                
                grupo de ncm < ncm especifico
                
                regra geral < regra com regime < regra com cliente
                
                regra sem cenario < regra com cenario 
                */
                
                RegraNcm regraNcm = (RegraNcm) regra;
                
                List<Long> listaRegraNcmGenerica = new ArrayList<>();
                String sqlRegraNcmGenerica = "";
                
                List<Long> listaCliente = new ArrayList<>();
                String sqlComplementarCliente = "";
                
                if ((regraNcm.getCliente() != null && regraNcm.getCliente().getId() != null)
                        ||(regraNcm.getRegimeTributario() != null)
                        ||(regraNcm.getCenario() != null && regraNcm.getCenario().getId() != null)) { 
                    
                    if (regraNcm.getRegimeTributario() != null) {
                        listaCliente = em.createQuery("Select o.id from Cliente o where o.tipoRegime = :regime ")
                                .setParameter("regime", regraNcm.getRegimeTributario()).getResultList();
                        
                        if(!listaCliente.isEmpty()){
                            sqlComplementarCliente += " and o.cliente.id in (:listaCliente) ";
                        }
                    }
                    
                    if (regraNcm.getCliente()!= null && regraNcm.getCliente().getId() != null) {
                        sqlComplementarCliente += " and o.cliente.id = :clienteId ";
                    }
                    
                    String sqlPrioridade = "";
                    
                    if ((regraNcm.getCliente()!= null && regraNcm.getCliente().getId() != null)
                        && (regraNcm.getCenario()!= null && regraNcm.getCenario().getId() != null)) {
                        sqlPrioridade = " or o.cliente.id = '" + regraNcm.getCliente().getId() + "' ";
                    }
                    
                    int ncmLen = regraNcm.getNcm().length();
                    
                    // lista de regras de ncm que podem ser substituidas
                    
                    Query queryCliente = em.createQuery("Select o.id from RegraNcm o "
                        + "where '" + regraNcm.getNcm() + "' like '%'||o.ncm||'%' "
                        + "and (LENGTH(o.ncm) < :ncmLen "
                        + "or (o.cliente.id is null " + sqlPrioridade + "))")
                            .setParameter("ncmLen", ncmLen);
                    
                    listaRegraNcmGenerica = queryCliente.getResultList();
                    
                    if(!listaRegraNcmGenerica.isEmpty()){
                        sqlRegraNcmGenerica = " or (dominio_regras = 'NCM' and regra_id in (:listaRegraNcmGenerica)) ";
                    }
                }

                Query queryCliente = em.createQuery("Select o.id from ProdutoCliente o "
                        + "where o.ncmPadrao like :ncm "+sqlComplementarCliente)
                        .setParameter("ncm", regraNcm.getNcm() + "%");
                
                if (regraNcm.getRegimeTributario() != null && !listaCliente.isEmpty()) {
                    queryCliente.setParameter("listaCliente", listaCliente);
                }
                
                if (regraNcm.getCliente()!= null && regraNcm.getCliente().getId() != null) {
                    queryCliente.setParameter("clienteId", regraNcm.getCliente().getId());
                }
                
                List<Long> listaProdutoCliente = queryCliente.getResultList();
                
                String sqlComplementar = "";
                
                
//                if (regraNcm.getRegimeTributario() != null) {
//                    sqlComplementar += " and o.produtoCliente.cliente.regimeTributario = :regime ";
//                }

                if (regraNcm.getCenario() != null) {
                    sqlComplementar += " and cenario_id = :cenarioId ";
                }
                
                if(!listaProdutoCliente.isEmpty()){
                
                    Query query = em.createNativeQuery("UPDATE syscontabil.produto_cenario set confirmado = false, "
                        + "dominio_regras = 'NCM', "
                        + "regra_id = :regraId, "
    //                            tributo federal
                        + "cst_pis_saida_padrao = :cstPisSaidaPadrao, "
                        + "aliquota_pis_saida_padrao = :aliquotaPisSaidaPadrao, "
                        + "cst_cofins_saida_padrao = :cstCofinsSaidaPadrao, "
                        + "aliquota_cofins_saida_padrao = :aliquotaCofinsSaidaPadrao, "
                        + "cst_ipi_saida_padrao = :cstIpiSaidaPadrao, "
                        + "aliquota_ipi_saida_padrao = :aliquotaIpiSaidaPadrao "
    //                            fim tributo federal
                        + " where produto_cliente_id in (:listaProdutoCliente) "
                        + " and (dominio_regras = 'REGIME' " + sqlRegraNcmGenerica + ")"
                        + " and rgevento <> 3 " + sqlComplementar);
                    
//                    tributo federal
                    query.setParameter("cstPisSaidaPadrao", regraNcm.getTributoFederalPadrao().getCstPisSaidaPadrao());
                    query.setParameter("aliquotaPisSaidaPadrao", regraNcm.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                    query.setParameter("cstCofinsSaidaPadrao", regraNcm.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                    query.setParameter("aliquotaCofinsSaidaPadrao", regraNcm.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                    query.setParameter("cstIpiSaidaPadrao", regraNcm.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                    query.setParameter("aliquotaIpiSaidaPadrao", regraNcm.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
//                    fim tributo federal
                    query.setParameter("regraId", regraNcm.getId());
                    query.setParameter("listaProdutoCliente", listaProdutoCliente);
                    

    //                if (regraNcm.getRegimeTributario()!= null) {
    //                    query.setParameter("regime", regraNcm.getRegimeTributario());
    //                }

                    if(!listaRegraNcmGenerica.isEmpty()){
                        query.setParameter("listaRegraNcmGenerica", listaRegraNcmGenerica);
                    }
    
                    if (regraNcm.getCenario() != null) {
                        query.setParameter("cenarioId", regraNcm.getCenario().getId());
                    }

                    query.executeUpdate();
                }
            }
            
            if(dominioRegra == DominioRegras.PRODUTO){
                /*
                prioridade regra tributaria da menor para maior prioridade

                regra geral < regra com regime < regra com cliente
                
                regra sem cenario < regra com cenario 
                */
                
                RegraProduto regraProduto = (RegraProduto) regra;
                
                List<Long> listaRegraProdutoGenerica = new ArrayList<>();
                String sqlRegraProdutoGenerica = "";
                
                List<Long> listaCliente = new ArrayList<>();
                String sqlComplementarCliente = "";
                
                if (regraProduto.getEanProduto() != null) {
                    sqlComplementarCliente += " o.ean = :ean ";
                }
                
                if (regraProduto.getCodigoProduto() != null) {
                    sqlComplementarCliente += " o.codigoProduto = :codigoProduto ";
                }
                
                if (regraProduto.getCenario() != null) {
                    sqlComplementarCliente += " and o.cenario.id = :cenarioId ";
                }

                if (regraProduto.getCliente()!= null && regraProduto.getCliente().getId() != null) {
                    sqlComplementarCliente += " and o.cliente.id = :clienteId ";
                }
                
                if ((regraProduto.getCliente() != null && regraProduto.getCliente().getId() != null)
                        ||(regraProduto.getRegimeTributario() != null)
                        ||(regraProduto.getCenario() != null && regraProduto.getCenario().getId() != null)) {
                    
                    if (regraProduto.getRegimeTributario() != null) {
                        listaCliente = em.createQuery("Select o.id from Cliente o where o.tipoRegime = :regime ")
                                .setParameter("regime", regraProduto.getRegimeTributario()).getResultList();
                        if(!listaCliente.isEmpty()){
                            sqlComplementarCliente += " and o.cliente.id in (:listaCliente) ";
                        }
                    }
                    
                    if (regraProduto.getCliente()!= null && regraProduto.getCliente().getId() != null) {
                        sqlComplementarCliente += " and o.cliente.id = :clienteId ";
                    }
                    
                    String sqlPrioridade = "";
                    
                    if (regraProduto.getRegimeTributario() != null && regraProduto.getCliente()== null) {
                        sqlPrioridade = " and o.regimeTributario is null ";
                    }
                    
                    if ((regraProduto.getCliente()!= null && regraProduto.getCliente().getId() != null)
                        && (regraProduto.getCenario()!= null && regraProduto.getCenario().getId() != null)) {
                        sqlPrioridade = " or o.cliente.id = '" + regraProduto.getCliente().getId() + "' ";
                    }
                    
                    // lista de regras de produto que podem ser substituidas
                    listaRegraProdutoGenerica = em.createQuery("Select o.id from RegraProduto o "
                        + "where o.cliente.id is null " + sqlPrioridade).getResultList();
                    
                    if(!listaRegraProdutoGenerica.isEmpty()){
                        sqlRegraProdutoGenerica = " or (dominio_regras = 'PRODUTO' and regra_id in (:listaRegraProdutoGenerica)) ";
                    }
                }

                Query queryCliente = em.createQuery("Select o.id from ProdutoCliente o "
                        + "where "+sqlComplementarCliente);
                
                if (regraProduto.getEanProduto() != null) {
                    queryCliente.setParameter("ean", regraProduto.getEanProduto());
                }
                
                if (regraProduto.getCodigoProduto() != null) {
                    queryCliente.setParameter("codigoProduto", regraProduto.getCodigoProduto());
                }

                if (regraProduto.getCenario() != null) {
                    queryCliente.setParameter("cenarioId", regraProduto.getCenario().getId());
                }

                if (regraProduto.getRegimeTributario() != null && !listaCliente.isEmpty()) {
                    queryCliente.setParameter("listaCliente", listaCliente);
                }
                
                if (regraProduto.getCliente()!= null && regraProduto.getCliente().getId() != null) {
                    queryCliente.setParameter("clienteId", regraProduto.getCliente().getId());
                }
                
                List<Long> listaProdutoCliente = queryCliente.getResultList();
                
                String sqlComplementar = "";
                
                
//                if (regraProduto.getRegimeTributario() != null) {
//                    sqlComplementar += " and o.produtoCliente.cliente.regimeTributario = :regime ";
//                }

                if (regraProduto.getCenario() != null) {
                    sqlComplementar += " and cenario_id = :cenarioId ";
                }
                
                if(!listaProdutoCliente.isEmpty()){
                
                    Query query = em.createNativeQuery("UPDATE syscontabil.produto_cenario set confirmado = false, "
                        + "dominio_regras = 'PRODUTO', "
                        + "regra_id = :regraId, "
    //                            tributo federal
                        + "cst_pis_saida_padrao = :cstPisSaidaPadrao, "
                        + "aliquota_pis_saida_padrao = :aliquotaPisSaidaPadrao, "
                        + "cst_cofins_saida_padrao = :cstCofinsSaidaPadrao, "
                        + "aliquota_cofins_saida_padrao = :aliquotaCofinsSaidaPadrao, "
                        + "cst_ipi_saida_padrao = :cstIpiSaidaPadrao, "
                        + "aliquota_ipi_saida_padrao = :aliquotaIpiSaidaPadrao "
    //                            fim tributo federal
                        + " where produto_cliente_id in (:listaProdutoCliente) "
                        + " and (dominio_regras = 'REGIME' or dominio_regras = 'NCM' " + sqlRegraProdutoGenerica + ")"
                        + " and rgevento <> 3 " + sqlComplementar);
                    
//                    tributo federal
                    query.setParameter("cstPisSaidaPadrao", regraProduto.getTributoFederalPadrao().getCstPisSaidaPadrao());
                    query.setParameter("aliquotaPisSaidaPadrao", regraProduto.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                    query.setParameter("cstCofinsSaidaPadrao", regraProduto.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                    query.setParameter("aliquotaCofinsSaidaPadrao", regraProduto.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                    query.setParameter("cstIpiSaidaPadrao", regraProduto.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                    query.setParameter("aliquotaIpiSaidaPadrao", regraProduto.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
//                    fim tributo federal
                    query.setParameter("regraId", regraProduto.getId());
                    query.setParameter("listaProdutoCliente", listaProdutoCliente);
                    

    //                if (regraProduto.getRegimeTributario()!= null) {
    //                    query.setParameter("regime", regraProduto.getRegimeTributario());
    //                }

                    if(!listaRegraProdutoGenerica.isEmpty()){
                        query.setParameter("listaRegraProdutoGenerica", listaRegraProdutoGenerica);
                    }
    
                    if (regraProduto.getCenario() != null) {
                        query.setParameter("cenarioId", regraProduto.getCenario().getId());
                    }

                    query.executeUpdate();
                }
                
            }
            
        }
    }
    
    public void updateRule(DominioRegras dominioRegra, Object regra) throws Exception {
        if (dominioRegra != null && regra != null) {
            
            String sqlTributoFederalPadrao = "";
            String sqlTributoFederalInformado = "";
            
            
//            if(dominioRegra == DominioRegras.REGIME){ 
//                RegraRegimeTributario rrt = (RegraRegimeTributario) regra;
//            }
            
            if(dominioRegra == DominioRegras.NCM){
                /*
                prioridade regra tributaria da menor para maior prioridade
                
                grupo de ncm < ncm especifico
                
                regra geral < regra com regime < regra com cliente
                
                regra sem cenario < regra com cenario 
                */
                
                RegraNcm regraNcm = (RegraNcm) regra;
                
                List<Long> listaRegraNcmGenerica = new ArrayList<>();
                String sqlRegraNcmGenerica = "";
                
                List<Long> listaCliente = new ArrayList<>();
                String sqlComplementarCliente = "";
                
                if ((regraNcm.getCliente() != null && regraNcm.getCliente().getId() != null)
                        ||(regraNcm.getRegimeTributario() != null)
                        ||(regraNcm.getCenario() != null && regraNcm.getCenario().getId() != null)) { 
                    
                    if (regraNcm.getRegimeTributario() != null) {
                        listaCliente = em.createQuery("Select o.id from Cliente o where o.tipoRegime = :regime ")
                                .setParameter("regime", regraNcm.getRegimeTributario()).getResultList();
                        
                        if(!listaCliente.isEmpty()){
                            sqlComplementarCliente += " and o.cliente.id in (:listaCliente) ";
                        }
                    }
                    
                    if (regraNcm.getCliente()!= null && regraNcm.getCliente().getId() != null) {
                        sqlComplementarCliente += " and o.cliente.id = :clienteId ";
                    }
                    
                    String sqlPrioridade = "";
                    
                    if ((regraNcm.getCliente()!= null && regraNcm.getCliente().getId() != null)
                        && (regraNcm.getCenario()!= null && regraNcm.getCenario().getId() != null)) {
                        sqlPrioridade = " or o.cliente.id = '" + regraNcm.getCliente().getId() + "' ";
                    }
                    
                    int ncmLen = regraNcm.getNcm().length();
                    
                    // lista de regras de ncm que podem ser substituidas
                    
                    Query queryCliente = em.createQuery("Select o.id from RegraNcm o "
                        + "where '" + regraNcm.getNcm() + "' like '%'||o.ncm||'%' "
                        + "and (LENGTH(o.ncm) < :ncmLen "
                        + "or (o.cliente.id is null " + sqlPrioridade + "))")
                            .setParameter("ncmLen", ncmLen);
                    
                    listaRegraNcmGenerica = queryCliente.getResultList();
                    
                    if(!listaRegraNcmGenerica.isEmpty()){
                        sqlRegraNcmGenerica = " or (dominio_regras = 'NCM' and regra_id in (:listaRegraNcmGenerica)) ";
                    }
                }

                Query queryCliente = em.createQuery("Select o.id from ProdutoCliente o "
                        + "where o.ncmPadrao like :ncm "+sqlComplementarCliente)
                        .setParameter("ncm", regraNcm.getNcm() + "%");
                
                if (regraNcm.getRegimeTributario() != null && !listaCliente.isEmpty()) {
                    queryCliente.setParameter("listaCliente", listaCliente);
                }
                
                if (regraNcm.getCliente()!= null && regraNcm.getCliente().getId() != null) {
                    queryCliente.setParameter("clienteId", regraNcm.getCliente().getId());
                }
                
                List<Long> listaProdutoCliente = queryCliente.getResultList();
                
                String sqlComplementar = "";
                
                
//                if (regraNcm.getRegimeTributario() != null) {
//                    sqlComplementar += " and o.produtoCliente.cliente.regimeTributario = :regime ";
//                }

                if (regraNcm.getCenario() != null) {
                    sqlComplementar += " and cenario_id = :cenarioId ";
                }
                
                if(!listaProdutoCliente.isEmpty()){
                
                    Query query = em.createNativeQuery("UPDATE syscontabil.produto_cenario set confirmado = false, "
                        + "dominio_regras = 'NCM', "
                        + "regra_id = :regraId, "
    //                            tributo federal
                        + "cst_pis_saida_padrao = :cstPisSaidaPadrao, "
                        + "aliquota_pis_saida_padrao = :aliquotaPisSaidaPadrao, "
                        + "cst_cofins_saida_padrao = :cstCofinsSaidaPadrao, "
                        + "aliquota_cofins_saida_padrao = :aliquotaCofinsSaidaPadrao, "
                        + "cst_ipi_saida_padrao = :cstIpiSaidaPadrao, "
                        + "aliquota_ipi_saida_padrao = :aliquotaIpiSaidaPadrao "
    //                            fim tributo federal
                        + " where produto_cliente_id in (:listaProdutoCliente) "
                        + " and (dominio_regras = 'REGIME' " + sqlRegraNcmGenerica + ")"
                        + " and rgevento <> 3 " + sqlComplementar);
                    
//                    tributo federal
                    query.setParameter("cstPisSaidaPadrao", regraNcm.getTributoFederalPadrao().getCstPisSaidaPadrao());
                    query.setParameter("aliquotaPisSaidaPadrao", regraNcm.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                    query.setParameter("cstCofinsSaidaPadrao", regraNcm.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                    query.setParameter("aliquotaCofinsSaidaPadrao", regraNcm.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                    query.setParameter("cstIpiSaidaPadrao", regraNcm.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                    query.setParameter("aliquotaIpiSaidaPadrao", regraNcm.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
//                    fim tributo federal
                    query.setParameter("regraId", regraNcm.getId());
                    query.setParameter("listaProdutoCliente", listaProdutoCliente);
                    

    //                if (regraNcm.getRegimeTributario()!= null) {
    //                    query.setParameter("regime", regraNcm.getRegimeTributario());
    //                }

                    if(!listaRegraNcmGenerica.isEmpty()){
                        query.setParameter("listaRegraNcmGenerica", listaRegraNcmGenerica);
                    }
    
                    if (regraNcm.getCenario() != null) {
                        query.setParameter("cenarioId", regraNcm.getCenario().getId());
                    }

                    query.executeUpdate();
                }
            }
            
            if(dominioRegra == DominioRegras.PRODUTO){
                /*
                prioridade regra tributaria da menor para maior prioridade

                regra geral < regra com regime < regra com cliente
                
                regra sem cenario < regra com cenario 
                */
                
                RegraProduto regraProduto = (RegraProduto) regra;
                
                List<Long> listaRegraProdutoGenerica = new ArrayList<>();
                String sqlRegraProdutoGenerica = "";
                
                List<Long> listaCliente = new ArrayList<>();
                String sqlComplementarCliente = "";
                
                if (regraProduto.getEanProduto() != null) {
                    sqlComplementarCliente += " o.ean = :ean ";
                }
                
                if (regraProduto.getCodigoProduto() != null) {
                    sqlComplementarCliente += " o.codigoProduto = :codigoProduto ";
                }
                
                if (regraProduto.getCenario() != null) {
                    sqlComplementarCliente += " and o.cenario.id = :cenarioId ";
                }

                if (regraProduto.getCliente()!= null && regraProduto.getCliente().getId() != null) {
                    sqlComplementarCliente += " and o.cliente.id = :clienteId ";
                }
                
                if ((regraProduto.getCliente() != null && regraProduto.getCliente().getId() != null)
                        ||(regraProduto.getRegimeTributario() != null)
                        ||(regraProduto.getCenario() != null && regraProduto.getCenario().getId() != null)) {
                    
                    if (regraProduto.getRegimeTributario() != null) {
                        listaCliente = em.createQuery("Select o.id from Cliente o where o.tipoRegime = :regime ")
                                .setParameter("regime", regraProduto.getRegimeTributario()).getResultList();
                        if(!listaCliente.isEmpty()){
                            sqlComplementarCliente += " and o.cliente.id in (:listaCliente) ";
                        }
                    }
                    
                    if (regraProduto.getCliente()!= null && regraProduto.getCliente().getId() != null) {
                        sqlComplementarCliente += " and o.cliente.id = :clienteId ";
                    }
                    
                    String sqlPrioridade = "";
                    
                    if (regraProduto.getRegimeTributario() != null && regraProduto.getCliente()== null) {
                        sqlPrioridade = " and o.regimeTributario is null ";
                    }
                    
                    if ((regraProduto.getCliente()!= null && regraProduto.getCliente().getId() != null)
                        && (regraProduto.getCenario()!= null && regraProduto.getCenario().getId() != null)) {
                        sqlPrioridade = " or o.cliente.id = '" + regraProduto.getCliente().getId() + "' ";
                    }
                    
                    // lista de regras de produto que podem ser substituidas
                    listaRegraProdutoGenerica = em.createQuery("Select o.id from RegraProduto o "
                        + "where o.cliente.id is null " + sqlPrioridade).getResultList();
                    
                    if(!listaRegraProdutoGenerica.isEmpty()){
                        sqlRegraProdutoGenerica = " or (dominio_regras = 'PRODUTO' and regra_id in (:listaRegraProdutoGenerica)) ";
                    }
                }

                Query queryCliente = em.createQuery("Select o.id from ProdutoCliente o "
                        + "where "+sqlComplementarCliente);
                
                if (regraProduto.getEanProduto() != null) {
                    queryCliente.setParameter("ean", regraProduto.getEanProduto());
                }
                
                if (regraProduto.getCodigoProduto() != null) {
                    queryCliente.setParameter("codigoProduto", regraProduto.getCodigoProduto());
                }

                if (regraProduto.getCenario() != null) {
                    queryCliente.setParameter("cenarioId", regraProduto.getCenario().getId());
                }

                if (regraProduto.getRegimeTributario() != null && !listaCliente.isEmpty()) {
                    queryCliente.setParameter("listaCliente", listaCliente);
                }
                
                if (regraProduto.getCliente()!= null && regraProduto.getCliente().getId() != null) {
                    queryCliente.setParameter("clienteId", regraProduto.getCliente().getId());
                }
                
                List<Long> listaProdutoCliente = queryCliente.getResultList();
                
                String sqlComplementar = "";
                
                
//                if (regraProduto.getRegimeTributario() != null) {
//                    sqlComplementar += " and o.produtoCliente.cliente.regimeTributario = :regime ";
//                }

                if (regraProduto.getCenario() != null) {
                    sqlComplementar += " and cenario_id = :cenarioId ";
                }
                
                if(!listaProdutoCliente.isEmpty()){
                
                    Query query = em.createNativeQuery("UPDATE syscontabil.produto_cenario set confirmado = false, "
                        + "dominio_regras = 'PRODUTO', "
                        + "regra_id = :regraId, "
    //                            tributo federal
                        + "cst_pis_saida_padrao = :cstPisSaidaPadrao, "
                        + "aliquota_pis_saida_padrao = :aliquotaPisSaidaPadrao, "
                        + "cst_cofins_saida_padrao = :cstCofinsSaidaPadrao, "
                        + "aliquota_cofins_saida_padrao = :aliquotaCofinsSaidaPadrao, "
                        + "cst_ipi_saida_padrao = :cstIpiSaidaPadrao, "
                        + "aliquota_ipi_saida_padrao = :aliquotaIpiSaidaPadrao "
    //                            fim tributo federal
                        + " where produto_cliente_id in (:listaProdutoCliente) "
                        + " and (dominio_regras = 'REGIME' or dominio_regras = 'NCM' " + sqlRegraProdutoGenerica + ")"
                        + " and rgevento <> 3 " + sqlComplementar);
                    
//                    tributo federal
                    query.setParameter("cstPisSaidaPadrao", regraProduto.getTributoFederalPadrao().getCstPisSaidaPadrao());
                    query.setParameter("aliquotaPisSaidaPadrao", regraProduto.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                    query.setParameter("cstCofinsSaidaPadrao", regraProduto.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                    query.setParameter("aliquotaCofinsSaidaPadrao", regraProduto.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                    query.setParameter("cstIpiSaidaPadrao", regraProduto.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                    query.setParameter("aliquotaIpiSaidaPadrao", regraProduto.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
//                    fim tributo federal
                    query.setParameter("regraId", regraProduto.getId());
                    query.setParameter("listaProdutoCliente", listaProdutoCliente);
                    

    //                if (regraProduto.getRegimeTributario()!= null) {
    //                    query.setParameter("regime", regraProduto.getRegimeTributario());
    //                }

                    if(!listaRegraProdutoGenerica.isEmpty()){
                        query.setParameter("listaRegraProdutoGenerica", listaRegraProdutoGenerica);
                    }
    
                    if (regraProduto.getCenario() != null) {
                        query.setParameter("cenarioId", regraProduto.getCenario().getId());
                    }

                    query.executeUpdate();
                }
                
            }
            
        }
    }
}
