/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.models.Cliente;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class ClienteService extends PadraoService<Cliente> {

    public List<Cliente> getListClientByName(String nome) {

        TypedQuery<Cliente> result = (TypedQuery<Cliente>) em.createNativeQuery("SELECT * FROM cliente o where o.nm_Cliente like '%" + nome + "%'", Cliente.class);

        return result.getResultList();

    }

}
