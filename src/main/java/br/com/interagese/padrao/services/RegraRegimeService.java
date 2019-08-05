/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.syscontabil.models.RegraRegimeTributario;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.domains.DominioRegime;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author adam
 */
@Service
public class RegraRegimeService extends PadraoService<RegraRegimeTributario> {

    //************************ create business rules ***************************
    @Override
    public String getWhere(String complementoConsulta) {
        String consultaSQL = "";

        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {
            if (Utils.somenteNumeros(complementoConsulta)) {
                consultaSQL = "o.id = '" + complementoConsulta;
            } else {
                consultaSQL = "o.regime_tributario_id = '" + complementoConsulta + "' ";
            }
        }

        return consultaSQL;
    }

    @Override
    public RegraRegimeTributario findById(Object id) {
        RegraRegimeTributario o = (RegraRegimeTributario) em.createQuery("SELECT o from RegraRegimeTributario o where o.id = :regime ").setParameter("regime", DominioRegime.valueOf(id.toString())).getSingleResult();

        return o;
    }

    public RegraRegimeTributario loadRegraRegimeTributario(String regime) {
        String sql = "select * from syscontabil.regra_regime_tributario where regime_tributario_id = :regime";

        TypedQuery<RegraRegimeTributario> lista = (TypedQuery<RegraRegimeTributario>) em.createNativeQuery(sql,RegraRegimeTributario.class).setParameter("regime", regime);

        return lista.getResultList().isEmpty() ? null : lista.getSingleResult();
    }

}
