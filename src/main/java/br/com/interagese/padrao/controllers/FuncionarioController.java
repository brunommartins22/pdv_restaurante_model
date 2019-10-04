/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.syscontabil.models.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.interagese.padrao.services.FuncionarioService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 *
 * @author Programacao
 */
@RestController
@RequestMapping(path = "api/funcionarios")
public class FuncionarioController extends PadraoController<Funcionario> {

    @Autowired
    @IsServiceDefault
    private FuncionarioService service;

    @GetMapping(path = "/teste/{idade}")
    public String teste(@PathVariable String idade) {
        return "alguma coisa" + idade;
    }

    @GetMapping(path = "/buscarPorDepartamento/{idDepartamento}")
    public List<Funcionario> buscarPorDepartamento(@PathVariable Integer idDepartamento) {
        return service.buscarPorDepartamento(idDepartamento);
           
    }
    
    @GetMapping(path = "/buscaPorFuncao/{idFuncao}")
    public List<Funcionario> buscaPorFuncao (@PathVariable Integer idFuncao){
        return service.buscaPorFuncao(idFuncao);
    }
    
    @GetMapping(path = "/buscaPorNome/{nomeFuncionario}")
    public List<Funcionario> buscaPorNome (@PathVariable String nomeFuncionario){
        return service.buscaPorNome(nomeFuncionario);
        
    }
    
    @GetMapping (path = "/buscaPorNomeFuncao/{nomeFuncao}")
    public List<Funcionario> buscaPorNomeFuncao (@PathVariable String nomeFuncao){
        return service.buscaPorNomeFuncao(nomeFuncao);
    }
}
