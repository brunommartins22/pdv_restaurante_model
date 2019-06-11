/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.models.RegraProduto;
import java.math.BigInteger;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class RegraProdutoService extends PadraoService<RegraProduto> {

    public RegraProduto loadRegraProduto(BigInteger clienteId, Long ean) {
        String sql = "select o from RegraProduto o where";
        if (ean != null) {
            sql += " o.eanProduto = '" + ean + "'";
        } else {
            sql += " o.clienteId = '" + clienteId + "'";
        }

        TypedQuery<RegraProduto> query = em.createQuery(sql, RegraProduto.class);

        return query.getResultList().isEmpty() ? null : query.getSingleResult();
    }

}
