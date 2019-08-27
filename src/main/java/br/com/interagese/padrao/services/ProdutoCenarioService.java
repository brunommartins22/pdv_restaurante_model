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
import br.com.interagese.syscontabil.models.RegraRegimeTributario;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class ProdutoCenarioService extends PadraoService<ProdutoCenario> {

    public List<ProdutoCenario> loadProdutoCenarioByClienteById(Map resp) {
        
        Long clienteId = ((Integer) resp.get("clienteId")).longValue();
        Long cenarioId = ((Integer) resp.get("cenarioId")).longValue();
        String status = (String) resp.get("status");

        String sql = "SELECT o FROM ProdutoCenario o where o.produtoCliente.cliente.id = '" + clienteId + "' ";

        if (cenarioId != null) {
            sql += " and o.cenario.id = '" + cenarioId + "'";
        }
        if (status != null && !status.isEmpty()) {
            sql += " and o.divergente is " + status.equals("Pendente");
        }

        List<ProdutoCenario> result = em.createQuery(sql).getResultList();

        return result;
    }

    public void changeRule(DominioRegras dominioRegra, Object regra) throws Exception {
        if (dominioRegra != null && regra != null) {
            
            String sqlTributoFederalPadrao = "cst_pis_saida_padrao = :cstPisSaida, "
                + "aliquota_pis_saida_padrao = :aliquotaPisSaida, "
                + "cst_cofins_saida_padrao = :cstCofinsSaida, "
                + "aliquota_cofins_saida_padrao = :aliquotaCofinsSaida, "
                + "cst_ipi_saida_padrao = :cstIpiSaida, "
                + "aliquota_ipi_saida_padrao = :aliquotaIpiSaida ";
            
            String sqlTributoFederalInformado = sqlTributoFederalPadrao.replace("_padrao", "_informado");
            
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

                    if ((regraNcm.getCliente() != null && regraNcm.getCliente().getId() != null)
                            && (regraNcm.getCenario() != null && regraNcm.getCenario().getId() != null)) {
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

                if (!listaProdutoCliente.isEmpty()) {

                    Query query = em.createNativeQuery("UPDATE syscontabil.produto_cenario set "
                        + "dominio_regras = 'NCM', "
                        + "regra_id = :regraId, "
                        + sqlTributoFederalPadrao
                        + " where produto_cliente_id in (:listaProdutoCliente) "
                        + " and (dominio_regras = 'REGIME' " + sqlRegraNcmGenerica + ")"
                        + " and rgevento <> 3 " + sqlComplementar);
                    
//                    tributo federal
                    query.setParameter("cstPisSaida", regraNcm.getTributoFederalPadrao().getCstPisSaidaPadrao());
                    query.setParameter("aliquotaPisSaida", regraNcm.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                    query.setParameter("cstCofinsSaida", regraNcm.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                    query.setParameter("aliquotaCofinsSaida", regraNcm.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                    query.setParameter("cstIpiSaida", regraNcm.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                    query.setParameter("aliquotaIpiSaida", regraNcm.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
//                    fim tributo federal
                    query.setParameter("regraId", regraNcm.getId());
                    query.setParameter("listaProdutoCliente", listaProdutoCliente);

                    if (!listaRegraNcmGenerica.isEmpty()) {
                        query.setParameter("listaRegraNcmGenerica", listaRegraNcmGenerica);
                    }

                    if (regraNcm.getCenario() != null) {
                        query.setParameter("cenarioId", regraNcm.getCenario().getId());
                    }

                    query.executeUpdate();
                    
                    Query queryInformado = em.createNativeQuery("UPDATE syscontabil.produto_cenario set "
                        + "dominio_regras = 'NCM', "
                        + "regra_id = :regraId, "
                        + sqlTributoFederalInformado
                        + " where produto_cliente_id in (:listaProdutoCliente) "
                        + " and (dominio_regras = 'REGIME' " + sqlRegraNcmGenerica + ")"
                        + " and rgevento <> 3 and confirmado = true and divergente = false " + sqlComplementar);
                    
//                    tributo federal
                    queryInformado.setParameter("cstPisSaida", regraNcm.getTributoFederalPadrao().getCstPisSaidaPadrao());
                    queryInformado.setParameter("aliquotaPisSaida", regraNcm.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                    queryInformado.setParameter("cstCofinsSaida", regraNcm.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                    queryInformado.setParameter("aliquotaCofinsSaida", regraNcm.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                    queryInformado.setParameter("cstIpiSaida", regraNcm.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                    queryInformado.setParameter("aliquotaIpiSaida", regraNcm.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
//                    fim tributo federal
                    queryInformado.setParameter("regraId", regraNcm.getId());
                    queryInformado.setParameter("listaProdutoCliente", listaProdutoCliente);

                    if (!listaRegraNcmGenerica.isEmpty()) {
                        queryInformado.setParameter("listaRegraNcmGenerica", listaRegraNcmGenerica);
                    }

                    if (regraNcm.getCenario() != null) {
                        queryInformado.setParameter("cenarioId", regraNcm.getCenario().getId());
                    }

                    queryInformado.executeUpdate();
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
                
                    Query query = em.createNativeQuery("UPDATE syscontabil.produto_cenario set "
                        + "dominio_regras = 'PRODUTO', "
                        + "regra_id = :regraId, "
                        + sqlTributoFederalPadrao
                        + " where produto_cliente_id in (:listaProdutoCliente) "
                        + " and (dominio_regras = 'REGIME' or dominio_regras = 'NCM' " + sqlRegraProdutoGenerica + ")"
                        + " and rgevento <> 3 " + sqlComplementar);
                    
//                    tributo federal
                    query.setParameter("cstPisSaida", regraProduto.getTributoFederalPadrao().getCstPisSaidaPadrao());
                    query.setParameter("aliquotaPisSaida", regraProduto.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                    query.setParameter("cstCofinsSaida", regraProduto.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                    query.setParameter("aliquotaCofinsSaida", regraProduto.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                    query.setParameter("cstIpiSaida", regraProduto.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                    query.setParameter("aliquotaIpiSaida", regraProduto.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
//                    fim tributo federal
                    query.setParameter("regraId", regraProduto.getId());
                    query.setParameter("listaProdutoCliente", listaProdutoCliente);
                  
                    if(!listaRegraProdutoGenerica.isEmpty()){
                        query.setParameter("listaRegraProdutoGenerica", listaRegraProdutoGenerica);
                    }
    
                    if (regraProduto.getCenario() != null) {
                        query.setParameter("cenarioId", regraProduto.getCenario().getId());
                    }

                    query.executeUpdate();
                    
                    Query queryInformado = em.createNativeQuery("UPDATE syscontabil.produto_cenario set "
                        + "dominio_regras = 'PRODUTO', "
                        + "regra_id = :regraId, "
                        + sqlTributoFederalInformado
                        + " where produto_cliente_id in (:listaProdutoCliente) "
                        + " and (dominio_regras = 'REGIME' or dominio_regras = 'NCM' " + sqlRegraProdutoGenerica + ")"
                        + " and rgevento <> 3 and confirmado = true and divergente = false " + sqlComplementar);
                    
//                    tributo federal
                    queryInformado.setParameter("cstPisSaida", regraProduto.getTributoFederalPadrao().getCstPisSaidaPadrao());
                    queryInformado.setParameter("aliquotaPisSaida", regraProduto.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                    queryInformado.setParameter("cstCofinsSaida", regraProduto.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                    queryInformado.setParameter("aliquotaCofinsSaida", regraProduto.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                    queryInformado.setParameter("cstIpiSaida", regraProduto.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                    queryInformado.setParameter("aliquotaIpiSaida", regraProduto.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
//                    fim tributo federal
                    queryInformado.setParameter("regraId", regraProduto.getId());
                    queryInformado.setParameter("listaProdutoCliente", listaProdutoCliente);
                  
                    if(!listaRegraProdutoGenerica.isEmpty()){
                        queryInformado.setParameter("listaRegraProdutoGenerica", listaRegraProdutoGenerica);
                    }
    
                    if (regraProduto.getCenario() != null) {
                        queryInformado.setParameter("cenarioId", regraProduto.getCenario().getId());
                    }

                    queryInformado.executeUpdate();
                }
                
            }
            
        }
    }
    
    public void updateRule(DominioRegras dominioRegra, Object regra) throws Exception {
        if (dominioRegra != null && regra != null) {
            
            String sqlTributoFederalPadrao = "cst_pis_saida_padrao = :cstPisSaida, "
                + "aliquota_pis_saida_padrao = :aliquotaPisSaida, "
                + "cst_cofins_saida_padrao = :cstCofinsSaida, "
                + "aliquota_cofins_saida_padrao = :aliquotaCofinsSaida, "
                + "cst_ipi_saida_padrao = :cstIpiSaida, "
                + "aliquota_ipi_saida_padrao = :aliquotaIpiSaida ";
            
            String sqlTributoFederalInformado = sqlTributoFederalPadrao.replace("_padrao", "_informado");
            
            if(dominioRegra == DominioRegras.REGIME){ 
                RegraRegimeTributario rrt = (RegraRegimeTributario) regra;
                
                Query query = em.createNativeQuery("UPDATE syscontabil.produto_cenario set "
                        + sqlTributoFederalPadrao
                        + " where dominio_regras = 'REGIME' and regra_id = :regraId ");
                query.setParameter("regraId", rrt.getId());
                
                query.setParameter("cstPisSaida", rrt.getTributoFederalPadrao().getCstPisSaidaPadrao());
                query.setParameter("aliquotaPisSaida", rrt.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                query.setParameter("cstCofinsSaida", rrt.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                query.setParameter("aliquotaCofinsSaida", rrt.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                query.setParameter("cstIpiSaida", rrt.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                query.setParameter("aliquotaIpiSaida", rrt.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
                
                query.executeUpdate();
                
                Query queryInformado = em.createNativeQuery("UPDATE syscontabil.produto_cenario set "
                        + sqlTributoFederalInformado
                        + " where dominio_regras = 'REGIME' and regra_id = :regraId "
                        + "and confirmado = true and divergente = false ");
                queryInformado.setParameter("regraId", rrt.getId());
                
                queryInformado.setParameter("cstPisSaida", rrt.getTributoFederalPadrao().getCstPisSaidaPadrao());
                queryInformado.setParameter("aliquotaPisSaida", rrt.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                queryInformado.setParameter("cstCofinsSaida", rrt.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                queryInformado.setParameter("aliquotaCofinsSaida", rrt.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                queryInformado.setParameter("cstIpiSaida", rrt.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                queryInformado.setParameter("aliquotaIpiSaida", rrt.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
                
                queryInformado.executeUpdate();
            }
            
            if(dominioRegra == DominioRegras.NCM){
                RegraNcm regraNcm = (RegraNcm) regra;
                
                Query query = em.createNativeQuery("UPDATE syscontabil.produto_cenario set "
                        + sqlTributoFederalPadrao
                        + " where dominio_regras = 'NCM' and regra_id = :regraId ");
                query.setParameter("regraId", regraNcm.getId());
                
                query.setParameter("cstPisSaida", regraNcm.getTributoFederalPadrao().getCstPisSaidaPadrao());
                query.setParameter("aliquotaPisSaida", regraNcm.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                query.setParameter("cstCofinsSaida", regraNcm.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                query.setParameter("aliquotaCofinsSaida", regraNcm.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                query.setParameter("cstIpiSaida", regraNcm.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                query.setParameter("aliquotaIpiSaida", regraNcm.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
                
                query.executeUpdate();
                
                Query queryInformado = em.createNativeQuery("UPDATE syscontabil.produto_cenario set "
                        + sqlTributoFederalInformado
                        + " where dominio_regras = 'NCM' and regra_id = :regraId "
                        + "and confirmado = true and divergente = false ");
                queryInformado.setParameter("regraId", regraNcm.getId());
                
                queryInformado.setParameter("cstPisSaida", regraNcm.getTributoFederalPadrao().getCstPisSaidaPadrao());
                queryInformado.setParameter("aliquotaPisSaida", regraNcm.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                queryInformado.setParameter("cstCofinsSaida", regraNcm.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                queryInformado.setParameter("aliquotaCofinsSaida", regraNcm.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                queryInformado.setParameter("cstIpiSaida", regraNcm.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                queryInformado.setParameter("aliquotaIpiSaida", regraNcm.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
                
                queryInformado.executeUpdate();
            }
            
            if(dominioRegra == DominioRegras.PRODUTO){
                RegraProduto regraProduto = (RegraProduto) regra;
                
                Query query = em.createNativeQuery("UPDATE syscontabil.produto_cenario set "
                        + sqlTributoFederalPadrao
                        + " where dominio_regras = 'PRODUTO' and regra_id = :regraId ");
                query.setParameter("regraId", regraProduto.getId());
                
                query.setParameter("cstPisSaida", regraProduto.getTributoFederalPadrao().getCstPisSaidaPadrao());
                query.setParameter("aliquotaPisSaida", regraProduto.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                query.setParameter("cstCofinsSaida", regraProduto.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                query.setParameter("aliquotaCofinsSaida", regraProduto.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                query.setParameter("cstIpiSaida", regraProduto.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                query.setParameter("aliquotaIpiSaida", regraProduto.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
                
                query.executeUpdate();
                
                Query queryInformado = em.createNativeQuery("UPDATE syscontabil.produto_cenario set "
                        + sqlTributoFederalInformado
                        + " where dominio_regras = 'PRODUTO' and regra_id = :regraId "
                        + "and confirmado = true and divergente = false ");
                queryInformado.setParameter("regraId", regraProduto.getId());
                
                queryInformado.setParameter("cstPisSaida", regraProduto.getTributoFederalPadrao().getCstPisSaidaPadrao());
                queryInformado.setParameter("aliquotaPisSaida", regraProduto.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                queryInformado.setParameter("cstCofinsSaida", regraProduto.getTributoFederalPadrao().getCstCofinsSaidaPadrao());
                queryInformado.setParameter("aliquotaCofinsSaida", regraProduto.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                queryInformado.setParameter("cstIpiSaida", regraProduto.getTributoFederalPadrao().getCstIpiSaidaPadrao());
                queryInformado.setParameter("aliquotaIpiSaida", regraProduto.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
                
                queryInformado.executeUpdate();
             
            }

        }
    }
}
