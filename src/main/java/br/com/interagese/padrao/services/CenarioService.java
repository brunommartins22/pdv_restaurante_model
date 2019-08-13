/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.models.Cenario;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class CenarioService extends PadraoService<Cenario> {

    public boolean existeNome(Cenario cenario) {

        String sqlComplementar = "";
        if (cenario.getId() != null) {
            sqlComplementar = " and o.id <> :id ";
        }

        Query query = em.createQuery("SELECT o from Cenario o where o.estado = :estado and o.nomeCenario = :nome and o.atributoPadrao.dominioEvento <> 3 " + sqlComplementar);
        query.setParameter("nome", cenario.getNomeCenario())
                .setParameter("estado", cenario.getEstado());

        if (cenario.getId() != null) {
            query.setParameter("id", cenario.getId());
        }

        List<Cenario> lista = query.getResultList();

        return !lista.isEmpty();

    }

    @Override
    public String getWhere(String complementoConsulta) {
        String consultaSQL = "";

        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {
            if (Utils.somenteNumeros(complementoConsulta)) {
                consultaSQL = "o.id = '" + complementoConsulta;
            } else {
                consultaSQL = "o.nomeCenario  LIKE '%" + complementoConsulta + "%' ";
            }
        }
        setOrder("order by o.nomeCenario");
        return consultaSQL;
    }

    @Override
    public Cenario create(Cenario obj) throws Exception {
        validar(obj);
        return super.create(obj);
    }

    @Override
    public Cenario update(Cenario obj) throws Exception {
        validar(obj);
        return super.update(obj);
    }

    public void validar(Cenario cenario) throws Exception {
        if (existeNome(cenario)) {
            addErro("Nome do Cenário cadastrado anteriormente para este estado!");
        }
    }

}
