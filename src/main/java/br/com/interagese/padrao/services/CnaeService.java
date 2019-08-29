/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.models.Cnae;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author Joao
 */
@Service
public class CnaeService extends PadraoService<Cnae> {

    public boolean existeCnae(Cnae cnae) {

        String sqlComplementar = "";
        if (cnae.getId() != null) {
            sqlComplementar = " and o.id <> :id ";
        }

        Query query = em.createQuery("SELECT o from Cnae o where o.codigo = :codigo and o.atributoPadrao.dominioEvento <> 3 " + sqlComplementar);
        query.setParameter("codigo", cnae.getCodigo());

        if (cnae.getId() != null) {
            query.setParameter("id", cnae.getId());
        }

        List<Cnae> lista = query.getResultList();

        return !lista.isEmpty();

    }

    @Override
    public String getWhere(String complementoConsulta) {
        String consultaSQL = "";

        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {
            String teste = complementoConsulta.replace("-", "").replace("/", "");
            if (Utils.somenteNumeros(teste)) {
                consultaSQL = "o.codigo like '%" + complementoConsulta + "%' ";
            } else {
                consultaSQL = "o.descricao LIKE '%" + complementoConsulta + "%' ";
            }
        }
        setOrder("order by o.descricao");
        return consultaSQL;
    }

    @Override
    public Cnae create(Cnae obj) throws Exception {
        validar(obj);
        return super.create(obj);
    }

    @Override
    public Cnae update(Cnae obj) throws Exception {
        validar(obj);
        return super.update(obj);
    }

    public void validar(Cnae cnae) throws Exception {
        if (existeCnae(cnae)) {
            addErro("Ja existe um cnae cadastrado com o codigo informado!");
        }
    }


}
