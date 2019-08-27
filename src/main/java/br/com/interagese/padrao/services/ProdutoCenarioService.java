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

//            if(dominioRegra == DominioRegras.REGIME){ 
//                RegraRegimeTributario rrt = (RegraRegimeTributario) regra;
//            }
            if (dominioRegra == DominioRegras.NCM) {
                RegraNcm regraNcm = (RegraNcm) regra;

                List<Long> listaRegraNcmGenerica = new ArrayList<>();
                String sqlRegraNcmGenerica = "";

                String sqlComplementarCliente = "";

                if ((regraNcm.getCliente() != null && regraNcm.getCliente().getId() != null)
                        || (regraNcm.getCenario() != null && regraNcm.getCenario().getId() != null)) {

                    if (regraNcm.getCliente() != null && regraNcm.getCliente().getId() != null) {
                        sqlComplementarCliente += " and o.cliente.id = :clienteId ";
                    }

                    String sqlPrioridade = "";

                    if ((regraNcm.getCliente() != null && regraNcm.getCliente().getId() != null)
                            && (regraNcm.getCenario() != null && regraNcm.getCenario().getId() != null)) {
                        sqlPrioridade = " or o.cliente.id = '" + regraNcm.getCliente().getId() + "' ";
                    }

                    listaRegraNcmGenerica = em.createQuery("Select o.id from RegraNcm o "
                            + "where o.ncm = :ncm and (o.cliente.id is null " + sqlPrioridade + ")")
                            .setParameter("ncm", regraNcm.getNcm()).getResultList();

                    if (!listaRegraNcmGenerica.isEmpty()) {
                        sqlRegraNcmGenerica = " or (dominio_regras = 'NCM' and regra_id in (:listaRegraNcmGenerica)) ";
                    }
                }

                Query queryCliente = em.createQuery("Select o.id from ProdutoCliente o "
                        + "where o.ncmPadrao = :ncm " + sqlComplementarCliente).setParameter("ncm", regraNcm.getNcm());

                if (regraNcm.getCliente() != null && regraNcm.getCliente().getId() != null) {
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
                    if (!listaRegraNcmGenerica.isEmpty()) {
                        query.setParameter("listaRegraNcmGenerica", listaRegraNcmGenerica);
                    }

                    if (regraNcm.getCenario() != null) {
                        query.setParameter("cenarioId", regraNcm.getCenario().getId());
                    }

                    query.executeUpdate();
                }
            }

            if (dominioRegra == DominioRegras.PRODUTO) {
                RegraProduto regraProduto = (RegraProduto) regra;

                String sqlComplementar = "";

//                if (regraProduto.getRegimeTributario() != null) {
//                    sqlComplementar += " and o.produtoCliente.cliente.regimeTributario = :regime ";
//                }
                if (regraProduto.getEanProduto() != null) {
                    sqlComplementar += " and o.produtoCliente.ean = :ean ";
                }

                if (regraProduto.getCodigoProduto() != null) {
                    sqlComplementar += " and o.produtoCliente.codigoProduto = :codigoProduto ";
                }

                if (regraProduto.getCenario() != null) {
                    sqlComplementar += " and o.cenario.id = :cenarioId ";
                }

                if (regraProduto.getCliente() != null && regraProduto.getCliente().getId() != null) {
                    sqlComplementar += " and o.produtoCliente.cliente.id = :clienteId ";
                }

                Query query = em.createNativeQuery("UPDATE ProdutoCenario o set o.confirmado = false, o.dominioRegras = 'PRODUTO', o.regraId = '" + regraProduto.getId() + "' where (o.dominioRegras = :dominioRegime or o.dominioRegras = :dominioNcm) and o.atributoPadrao.dominioEvento <> 3 " + sqlComplementar);
                query.setParameter("dominioRegime", DominioRegras.REGIME);
                query.setParameter("dominioNcm", DominioRegras.NCM);
//                query.setParameter("dominioRegra", DominioRegras.NCM);
//                query.setParameter("regraId", regraProduto.getId());

//                if (regraProduto.getRegimeTributario()!= null) {
//                    query.setParameter("regime", regraProduto.getRegimeTributario());
//                }
                if (regraProduto.getEanProduto() != null) {
                    query.setParameter("ean", regraProduto.getEanProduto());
                }

                if (regraProduto.getCodigoProduto() != null) {
                    query.setParameter("codigoProduto", regraProduto.getCodigoProduto());
                }

                if (regraProduto.getCenario() != null) {
                    query.setParameter("cenarioId", regraProduto.getCenario().getId());
                }

                if (regraProduto.getCliente() != null && regraProduto.getCliente().getId() != null) {
                    query.setParameter("clienteId", regraProduto.getCliente().getId());
                }

                query.executeUpdate();
            }

        }
    }
}
