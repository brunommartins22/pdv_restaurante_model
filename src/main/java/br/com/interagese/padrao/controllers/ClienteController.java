/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.ClienteService;
import br.com.interagese.syscontabil.models.Cliente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bruno Martins
 */
@RestController
@RequestMapping(path = "api/clientes")
public class ClienteController extends PadraoController<Cliente> {
    
    //********************** inject service in controller **********************
    @IsServiceDefault
    @Autowired
    private ClienteService service;
    
    //*************************** endpoint *************************************
    
    @GetMapping(path = "getListClientByName/{nome}")
    public String getListClientByName(@PathVariable String nome) {
        List<Cliente> result = service.getListClientByName(nome);

        return serializar(result);
    }
    
    
    
}
