/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.syscontabil.models.ArquivosProcessar;
import br.com.interagese.syscontabil.models.Cliente;
import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.ArquivosProcessarService;
import br.com.interagese.erplibrary.AtributoPadrao;
import br.com.interagese.padrao.services.ConfiguracaoService;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author adam
 */
@RestController
@RequestMapping(path = "/api/arquivos-processar")
public class ArquivosProcessarController extends PadraoController<ArquivosProcessar> {

    @IsServiceDefault
    @Autowired
    private ArquivosProcessarService service;   
    
    @Autowired
    private ConfiguracaoService configuracaoService;
    
    @GetMapping(path = "/{id:.+}")
    public String findById(@PathVariable(name = "id") String id) {
        return serializar(service.findById(id));
    }
    
    @RequestMapping(path = "/upload")
    public String upload(@RequestParam("clienteJson") String clienteJson, 
            @RequestParam("file") MultipartFile file, 
            @RequestParam("atributoPadraoJson") String atributoPadraoJson) throws IOException, Exception {
            // @RequestBody UploadArquivoDto dto

        Cliente cliente = (Cliente) Utils.deserializar(clienteJson, Cliente.class);
        AtributoPadrao rg = (AtributoPadrao) Utils.deserializar(atributoPadraoJson, AtributoPadrao.class);
        
        //try {
            
       // } catch (IOException e) {
            //return ResponseEntity.badRequest().body("Não foi possível ler o json");
        //}

        // System.out.println(cliente);
        // System.out.println(file.getOriginalFilename());
        
        String caminhoUpload = configuracaoService.findById(1L).getCaminhoUpload();
        
        file.transferTo(new File(caminhoUpload + "/" + file.getOriginalFilename()));
        
        ArquivosProcessar arquivo = new ArquivosProcessar();
        
        arquivo.setCliente(cliente);
        arquivo.setAtributoPadrao(rg);
        arquivo.setNome(file.getOriginalFilename());
        arquivo.setCaminho(caminhoUpload + "/" + file.getOriginalFilename());
        
        File f = new File(caminhoUpload + "/" + file.getOriginalFilename());
        file.transferTo(f);
        
        LineNumberReader linhaLeitura = new LineNumberReader(new FileReader(f));
        linhaLeitura.skip(f.length());
        Integer qtdLinha = linhaLeitura.getLineNumber();
        
        arquivo.setNumeroRegistros(qtdLinha.longValue());
        arquivo.setNumeroRegistrosProcessados(0L);
        arquivo.setUltimoRegistroProcessado(0L);
        
        try {
            return serializar(service.create(arquivo));
        } catch (Exception ex) {
            //FIXME - tem que gerar log de erro, mas não erro de validação
            //Logger.getLogger(PadraoController.class.getName()).log(Level.SEVERE, null, ex);
            return returnException(ex);
        }
    }
    
}
