/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.padrao.rest.models.Usuario;
import br.com.interagese.padrao.rest.services.UsuarioService;
import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bruno Martins
 */
@RestController
@RequestMapping(path = "api/usuario")
public class UsuarioController extends PadraoController<Usuario> {

    //******************** inject service in controller ************************
    @IsServiceDefault
    @Autowired
    private UsuarioService service;
    
    

}
