/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.models.CenarioRegimeTributario;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class CenarioRegimeService extends PadraoService<CenarioRegimeTributario> {

    public boolean existeId(CenarioRegimeTributario cenario) {

        if (cenario.getId() != null) {
            Query query = em.createQuery("SELECT o from CenarioRegimeTributario o where o.id = :id and o.atributoPadrao.dominioEvento <> 3 ");

            query.setParameter("id", cenario.getId());

            List<CenarioRegimeTributario> lista = query.getResultList();

            return !lista.isEmpty();
        }
        
        return true;

    }

    @Override
    public String getWhere(String complementoConsulta) {
        String consultaSQL = "";

        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {
            if (Utils.somenteNumeros(complementoConsulta)) {
                consultaSQL = "o.id = '" + complementoConsulta;
            } else {
                consultaSQL = "o.id.regimeTributarioId = '" + complementoConsulta + "' ";
            }
        }
        setOrder("order by o.id.cenarioId");
        return consultaSQL;
    }
    
    @Override
    public CenarioRegimeTributario create(CenarioRegimeTributario obj) throws Exception {
        validar(obj);
        return super.create(obj);
    }

    @Override
    public CenarioRegimeTributario update(CenarioRegimeTributario obj) throws Exception {
        return super.update(obj);
    }

    public void validar(CenarioRegimeTributario cenario) throws Exception {
        if (existeId(cenario)) {
            addErro("Cenario de Regime Tributario cadastrado anteriormente!");
        }
    }

}
