/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.ProdutoCenarioService;
import br.com.interagese.padrao.services.ProdutoClienteService;
import br.com.interagese.syscontabil.models.ProdutoCenario;
import br.com.interagese.syscontabil.models.ProdutoCliente;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private ProdutoCenarioService produtoCenarioService;

    //**************************************************************************
    @PostMapping(path = "loadClientProductAll")
    public String loadClientProductAll(@RequestBody Map resp) {

        try {
            return serializar(produtoClienteService.loadProductClient(resp));
        } catch (Exception ex) {
            return returnException(ex);
        }
    }

    @PostMapping(path = "loadProdutoCenarioCount")
    public String loadProdutoCenarioCount(@RequestBody Map resp) {
        try {

            return serializar(produtoCenarioService.loadProdutoCenarioCount(resp));
        } catch (Exception ex) {
            return returnException(ex);
        }
    }

    @PostMapping(path = "loadClientSelected")
    public String loadClientSelected(@RequestBody Map resp) {
        try {
            return serializar(produtoClienteService.loadProductClientRule(resp));
        } catch (Exception ex) {
            return returnException(ex);
        }
    }

    @PostMapping(path = "confirmClientRule")
    public String confirmClientRule(@RequestBody String json) {
        try {
            ProdutoCenario produtoCenario = (ProdutoCenario) deserializar(json, ProdutoCenario.class);
            produtoClienteService.confirmClientRule(produtoCenario, false);
            return serializar("");
        } catch (Exception ex) {
            return returnException(ex);
        }
    }

    @PostMapping(path = "confirmClientRuleOnBatchProduct")
    public String confirmClientRuleOnBatchProduct(@RequestBody List<ProdutoCenario> result) {
        try {
            produtoClienteService.confirmClientRuleOnBatchProduct(result);
            return serializar("");
        } catch (Exception ex) {
            return returnException(ex);
        }
    }

    @PostMapping(path = "editValueNcmOrCestbyInformed/{value}")
    public String editValueNcmOrCestbyInformed(@RequestBody List<ProdutoCenario> result, @PathVariable boolean value) {
        try {
            produtoClienteService.editValueNcmOrCestbyInformed(result, value);
           return serializar("");
        } catch (Exception ex) {
            return returnException(ex);
        }
    }
    
    
    @PostMapping(path = "inactivateProducts/{value}")
    public String inactivateProducts(@RequestBody List<ProdutoCenario> result,@PathVariable String value){
        try{
            produtoClienteService.inactivateProducts(result, value);
            return serializar("");
        }catch(Exception ex){
            return returnException(ex);
        }
    }

}
