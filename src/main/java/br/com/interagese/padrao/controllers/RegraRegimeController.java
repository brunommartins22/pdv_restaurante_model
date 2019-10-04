/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.syscontabil.models.RegraRegimeTributario;
import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.RegraRegimeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author adam
 */
@RestController
@RequestMapping(path = "/api/regra-regime")
    public class RegraRegimeController extends PadraoController<RegraRegimeTributario> {

    @IsServiceDefault
    @Autowired
    private RegraRegimeService service;   
    
    @GetMapping(path = "/{id:.+}")
    public String findById(@PathVariable(name = "id") String id) {
        return serializar(service.findById(id));
    }
    
}
