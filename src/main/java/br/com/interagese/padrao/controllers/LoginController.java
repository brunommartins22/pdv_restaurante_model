/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.padrao.rest.models.Usuario;
import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.UsuarioService;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author adam
 */
@RestController
@RequestMapping(path = "/api/security")
public class LoginController extends PadraoController<Usuario> {

    @IsServiceDefault
    @Autowired
    private UsuarioService service;

    @PostMapping(path = "/login")
// @HeaderParam("Authorization"
    public String login(@RequestHeader String authorizationHeader) {
        try {
            String accessToken = service.login(authorizationHeader);
            return accessToken;
        } catch (Exception ex) {
            return null;
        }
    }

}
