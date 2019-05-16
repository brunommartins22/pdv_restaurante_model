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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *
 * @author adam
 */
//@RestController
//@RequestMapping(path = "/api/security")
public class LoginController extends PadraoController<Usuario> {

    @IsServiceDefault
    @Autowired
    private UsuarioService service;

    @PostMapping(path = "/login")
// @HeaderParam("Authorization"
    public String login(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String accessToken = service.login(authorizationHeader);
            return accessToken;
        } catch (Exception ex) {
            return null;
        }
    }

}
