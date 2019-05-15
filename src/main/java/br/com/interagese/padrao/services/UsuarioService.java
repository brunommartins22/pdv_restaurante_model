/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.models.Usuario;
import br.com.interagese.padrao.rest.util.JwtToken;
import br.com.interagese.padrao.rest.util.PadraoService;
import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class UsuarioService extends PadraoService<Usuario> {

    public String login(String authorizationHeader) throws Exception {
        String[] credentials = getCredentials(authorizationHeader);

        String login = credentials[0];
        String senha=credentials[1];

        

        UUID sessionId = UUID.randomUUID();
        
        return JwtToken.buildAccessToken(login, null, null, sessionId);
    }

    private String[] getCredentials(String authorizationHeader) {

        try {

            if (authorizationHeader != null) {
                String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
                String credentials = new String(Base64.getDecoder().decode(base64Credentials));
                return credentials.split(":", 2);
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
