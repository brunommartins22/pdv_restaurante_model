/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.ClienteService;
import br.com.interagese.padrao.services.ProdutoClienteService;
import br.com.interagese.syscontabil.dto.ClienteProdutoDto;
import br.com.interagese.syscontabil.dto.ProdutoClienteDto;
import br.com.interagese.syscontabil.models.ProdutoCliente;
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
@RequestMapping("api/produtoclientes")
public class ProdutoClienteController extends PadraoController<ProdutoCliente> {

    @IsServiceDefault
    @Autowired
    private ProdutoClienteService produtoClienteService;
    @Autowired
    private ClienteService clienteService;

    //**************************************************************************
    @GetMapping(path = "loadClientProductAll")
    public String loadClientProductAll() {

        try {
            return serializar(produtoClienteService.loadProductClient());
        } catch (Exception ex) {
            return returnException(ex);
        }
    }

    @GetMapping(path = "loadClientSelected/{clienteId}")
    public String loadClientSelected(@PathVariable Long clienteId) {
        try {

            ProdutoClienteDto temp = produtoClienteService.loadProductClientRule(clienteService.findById(clienteId));

            return serializar(temp);

        } catch (Exception ex) {
            return returnException(ex);
        }
    }

}
