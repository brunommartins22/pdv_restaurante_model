/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.ProdutoClienteService;
import br.com.interagese.syscontabil.models.ProdutoCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bruno Martins
 */
@RestController
@RequestMapping("api/produtoclientes")
public class ProdutoClienteController extends PadraoController<ProdutoCliente> {

    @IsServiceDefault
    @Autowired
    private ProdutoClienteService produtoClienteService;

}
