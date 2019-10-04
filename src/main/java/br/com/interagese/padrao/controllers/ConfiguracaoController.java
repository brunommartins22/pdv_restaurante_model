/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.syscontabil.models.Configuracao;
import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.ConfiguracaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author adam
 */
@RestController
@RequestMapping(path = "/api/configuracao")
    public class ConfiguracaoController extends PadraoController<Configuracao> {

    @IsServiceDefault
    @Autowired
    private ConfiguracaoService service;
    
}
