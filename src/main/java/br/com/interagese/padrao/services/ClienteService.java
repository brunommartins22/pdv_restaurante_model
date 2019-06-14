/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.models.Cliente;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class ClienteService extends PadraoService<Cliente> {

    
    
     public boolean existeCpfCnpj(Cliente cliente) {

        String sqlComplementar = "";
        if (cliente.getId() != null) {
            sqlComplementar = " and o.id <> :id ";
        }

        Query query = em.createQuery("SELECT o from Cliente o where o.cpfCnpj = :cpfCnpj " + sqlComplementar + " ");
        query.setParameter("cpfCnpj", cliente.getCpfCnpj());

        if (cliente.getId() != null) {
            query.setParameter("id", cliente.getId());
        }

        List<Cliente> lista = query.getResultList();

        return !lista.isEmpty();

    }
     
   

    @Override
    public String getWhere(String complementoConsulta) {
        String consultaSQL = "";

        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {
            if (Utils.somenteNumeros(complementoConsulta)) {
                consultaSQL = "o.id = '" + complementoConsulta;
            } else {
                consultaSQL = "o.nmCliente LIKE '%" + complementoConsulta + "%' ";
            }
        }

        return consultaSQL;
    }

   @Override
    public Cliente create(Cliente obj) throws Exception {
        if (existeCpfCnpj(obj)) {
            addErro("CPF/CNPJ cadastrado anteriormente");
        }

        return super.create(obj);
    }

    

    @Override
    public Cliente update(Cliente obj) throws Exception {
        if (existeCpfCnpj(obj)) {
            addErro("CPF/CNPJ cadastrado anteriormente");
        }
        return super.update(obj);
    }

}
