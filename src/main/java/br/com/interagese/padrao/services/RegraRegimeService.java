/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.syscontabil.models.RegraRegimeTributario;
import br.com.interagese.padrao.rest.util.PadraoService;
import java.util.List;
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
                consultaSQL = "o.regime_tributario = '" + complementoConsulta + "' ";
            }
        }

        return consultaSQL;
    }

    @Override
    public RegraRegimeTributario findById(Object id) {
        RegraRegimeTributario o = (RegraRegimeTributario) em.createQuery("SELECT o from RegraRegimeTributario o where o.id = :id ").setParameter("id", Long.parseLong(id.toString())).getSingleResult();

        return o;
    }

     /**
     * CORRIGIR SQL DE CONSULTA
     */
    public RegraRegimeTributario loadRegraRegimeTributario(String regime) {
        String sql = "select * from syscontabil.regra_regime_tributario where regime_tributario_id = :regime";

        TypedQuery<RegraRegimeTributario> lista = (TypedQuery<RegraRegimeTributario>) em.createNativeQuery(sql, RegraRegimeTributario.class).setParameter("regime", regime);

        return lista.getResultList().isEmpty() ? null : lista.getSingleResult();
    }

    @Override
    public List<RegraRegimeTributario> findRange(String complementoConsulta, int apartirDe, int quantidade, String ordernacao) {
        List<RegraRegimeTributario> result = super.findRange(complementoConsulta, apartirDe, quantidade, ordernacao); //To change body of generated methods, choose Tools | Templates.

        if (!result.isEmpty()) {
            result.forEach((rt) -> {
                rt.setNomeRegime(rt.getRegimeTributario().getDescricao());
            });
        }

        return result;
    }

}
