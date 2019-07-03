/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.syscontabil.models.CenarioNcm;
import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.CenarioNcmService;
import br.com.interagese.syscontabil.models.CenarioNcmPK;

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
@RequestMapping(path = "/api/cenario-ncm")
public class CenarioNcmController extends PadraoController<CenarioNcm> {

    @IsServiceDefault
    @Autowired
    private CenarioNcmService service;   
    
    @PostMapping(path = "/findById")
    public String findById(@RequestBody CenarioNcmPK id) {
        return serializar(service.findById(id));
    }
    
}
