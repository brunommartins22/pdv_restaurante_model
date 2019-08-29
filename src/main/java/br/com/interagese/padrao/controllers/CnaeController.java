/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.CnaeService;
import br.com.interagese.syscontabil.models.Cnae;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bruno Martins
 */
@RestController
@RequestMapping(path = "api/cnae")
public class CnaeController extends PadraoController<Cnae> {
    
    //********************** inject service in controller **********************
    @IsServiceDefault
    @Autowired
    private CnaeService service;
    
    //*************************** endpoint *************************************
    
    
}
