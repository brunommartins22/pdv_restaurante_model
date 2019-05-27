/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.ClienteProdutoService;
import br.com.interagese.syscontabil.models.ClienteProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bruno Martins
 */
@RestController
@RequestMapping(path = "api/clienteprodutos")
public class ClienteProdutoController extends PadraoController<ClienteProduto>{
    
    @IsServiceDefault
    @Autowired
    private ClienteProdutoService clienteProdutoService;
    
    //****************************** methods ***********************************
    
     
    
    
    
    
}
