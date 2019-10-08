/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.dto;

import br.com.interagese.erplibrary.AtributoPadrao;
import br.com.interagese.syscontabil.models.Cliente;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Bruno Martins
 */
public class UploadArquivoDto {

    private Cliente cliente;
    private MultipartFile arquivo;
    private AtributoPadrao atributoPadrao;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public MultipartFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(MultipartFile arquivo) {
        this.arquivo = arquivo;
    }

    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

    

}
