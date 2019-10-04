/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.CenarioService;
import br.com.interagese.syscontabil.models.Cenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bruno Martins
 */
@RestController
@RequestMapping(path = "api/cenario")
public class CenarioController extends PadraoController<Cenario> {

    //********************** inject service in controller **********************
    @IsServiceDefault
    @Autowired
    private CenarioService service;

    //*************************** endpoint *************************************
    @GetMapping("/loadCenarioByUf/{idEstado}")
    public String loadCenarioByUf(@PathVariable Long idEstado) {
        try {
            return serializar(service.loadCenarioByUf(idEstado));
        } catch (Exception ex) {
            return returnException(ex);
        }
    }

}
