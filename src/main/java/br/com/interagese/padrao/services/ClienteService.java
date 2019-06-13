/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.models.Cliente;
import br.com.interagese.syscontabil.models.RegraProduto;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class ClienteService extends PadraoService<Cliente> {
    
    
            
    public  boolean  verificaClienteExiste(Long IdCliente, String cpfCnpj) {       
            String sql = "select o from cliente o where o.id <> " + IdCliente + " and o.cpfCnpj = '"+ cpfCnpj+"'";
            TypedQuery<Cliente> query = em.createQuery(sql, Cliente.class);
           return query.getResultList().isEmpty() ? false : true;      
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
    
    
    
    

}
