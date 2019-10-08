/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Joao
 */
@Entity
@Table(name = "configuracao")
public class Configuracao implements Serializable {

    @Id
    private Long id;

    @Column(nullable = false, length = 200)
    private String caminhoUpload;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaminhoUpload() {
        return caminhoUpload;
    }

    public void setCaminhoUpload(String caminhoUpload) {
        this.caminhoUpload = caminhoUpload;
    }
   
}
