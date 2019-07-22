/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.domains.DominioRegime;
import br.com.interagese.syscontabil.models.CenarioNcm;
import java.util.List;
import javax.persistence.Query;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class CenarioNcmService extends PadraoService<CenarioNcm> {

    public boolean existeId(CenarioNcm cenario) {

        if (cenario.getId() != null) {
            Query query = em.createQuery("SELECT o from CenarioNcm o where o.id = :id and o.atributoPadrao.dominioEvento <> 3 ");

            query.setParameter("id", cenario.getId());

            List<CenarioNcm> lista = query.getResultList();

            return !lista.isEmpty();
        }
        
        return true;

    }

    @Override
    public String getWhere(Map filtros) {
        String consultaSQL = "";

        if (filtros.containsKey("codigoNcm")) {
            consultaSQL = " o.id.codigoNcm = '" + filtros.get("codigoNcm") + "' ";
        }
        
        if (filtros.containsKey("regimeTributarioId")) {
            consultaSQL += (consultaSQL.length() > 0 ? " and ": "") +" o.id.regimeTributarioId = '" + DominioRegime.valueOf(filtros.get("regimeTributarioId").toString()) + "' ";
        }
        
        if (filtros.containsKey("cenarioId")) {
            consultaSQL += (consultaSQL.length() > 0 ? " and ": "") +" o.id.cenarioId = '" + filtros.get("cenarioId") + "' ";
        }
        
        setOrder("order by o.id.cenarioId");
        return consultaSQL;
    }
    
    @Override
    public CenarioNcm create(CenarioNcm obj) throws Exception {
        validar(obj);
        return super.create(obj);
    }

    @Override
    public CenarioNcm update(CenarioNcm obj) throws Exception {
        return super.update(obj);
    }

    public void validar(CenarioNcm cenario) throws Exception {
        if (existeId(cenario)) {
            addErro("Cenario de Regime Tributario cadastrado anteriormente!");
        }
    }

}
