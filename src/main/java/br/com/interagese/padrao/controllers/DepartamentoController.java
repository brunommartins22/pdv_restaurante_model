/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.syscontabil.models.Departamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.interagese.padrao.services.DepartartamentoService;

/**
 *
 * @author Programacao
 */
@RestController
@RequestMapping (path = "api/departamentos")
public class DepartamentoController extends PadraoController<Departamento>{
 
    @Autowired
    @IsServiceDefault
    private DepartartamentoService departamentoService;
}
