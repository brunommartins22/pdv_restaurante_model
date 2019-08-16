/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.models.RegraProduto;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class RegraProdutoService extends PadraoService<RegraProduto> {

 
    /**
     * CORRIGIR SQL DE CONSULTA
     */
    public RegraProduto loadRegraProduto(BigInteger clienteId, Long ean) {
        String sql = "select o from RegraProduto o where";
        if (ean != null) {
            sql += " o.eanProduto = '" + ean + "'";
        } else {
            sql += " o.cliente.id = '" + clienteId + "'";
        }

        TypedQuery<RegraProduto> query = em.createQuery(sql, RegraProduto.class);

        return query.getResultList().isEmpty() ? null : query.getSingleResult();
    }

    public boolean existeProdutoCenario(RegraProduto regraProduto) {

        String sqlComplementar = "";
        if (regraProduto.getId() != null) {
            sqlComplementar = " and o.id <> :id ";
        }

        if (regraProduto.getEanProduto()!= null) {
            sqlComplementar += " and o.eanProduto = :ean ";
        } else {
            sqlComplementar += " and o.eanProduto is null ";
        }
        
        if (regraProduto.getCliente()!= null) {
            sqlComplementar += " and o.cliente.id = :clienteId ";
        } else {
            sqlComplementar += " and o.cliente.id is null ";
        }
        
        if (regraProduto.getCodigoProduto()!= null) {
            sqlComplementar += " and o.codigoProduto = :codpro ";
        } else {
            sqlComplementar += " and o.codigoProduto is null ";
        }
        
        if (regraProduto.getCenario() != null) {
            sqlComplementar += " and o.cenario.id = :cenarioId ";
        } else {
            sqlComplementar += " and o.cenario.id is null ";
        }
        
        Query query = em.createQuery("SELECT o from RegraProduto o where o.atributoPadrao.dominioEvento <> 3 " + sqlComplementar);

        if (regraProduto.getEanProduto()!= null) {
            query.setParameter("ean", regraProduto.getEanProduto());
        }
        
        if (regraProduto.getCliente() != null) {
            query.setParameter("clienteId", regraProduto.getCliente().getId());
        }
        
        if (regraProduto.getCodigoProduto()!= null) {
            query.setParameter("codpro", regraProduto.getCodigoProduto());
        }
        
        if (regraProduto.getCenario() != null) {
            query.setParameter("cenarioId", regraProduto.getCenario().getId());
        }
        
        if (regraProduto.getId() != null) {
            query.setParameter("id", regraProduto.getId());
        }

        List<RegraProduto> lista = query.getResultList();

        return !lista.isEmpty();

    }
    
    @Override
    public RegraProduto create(RegraProduto obj) throws Exception {
        validar(obj);
        return super.create(obj);
    }
    
    @Override
    public RegraProduto update(RegraProduto obj) throws Exception {
        validar(obj);
        return super.update(obj);
    }

    public void validar(RegraProduto regraProduto) throws Exception {
        if (existeProdutoCenario(regraProduto)) {
            addErro("Ja existe uma regra para o produto e cenário informados!");
        }
    }
}
