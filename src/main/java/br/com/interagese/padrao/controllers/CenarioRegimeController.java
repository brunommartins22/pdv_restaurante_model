/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.syscontabil.models.CenarioRegimeTributario;
import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.CenarioRegimeService;
import br.com.interagese.syscontabil.models.CenarioRegimeTributarioPK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author adam
 */
@RestController
@RequestMapping(path = "/api/cenario-regime")
public class CenarioRegimeController extends PadraoController<CenarioRegimeTributario> {

    @IsServiceDefault
    @Autowired
    private CenarioRegimeService service;   
    
    @PostMapping(path = "/findById")
    public String findById(@RequestBody CenarioRegimeTributarioPK id) {
        return serializar(service.findById(id));
    }
    
}
