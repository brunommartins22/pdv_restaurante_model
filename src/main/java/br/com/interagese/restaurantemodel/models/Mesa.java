/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.restaurantemodel.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "mesa")
public class Mesa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_mesa")
    @SequenceGenerator(name = "gen_mesa", sequenceName = "seq_mesa")
    private Long id;
    @Column(nullable = false)
    private Integer numeroMesa;
    @Column(nullable = false)
    private Integer quantidadePessoas;
    /**
     * 0-Livre,1-Em uso
     */
    private boolean status;
    private boolean ativo;
    @Transient
    private String numeroMesaDesc;
    @Transient
    private String quantidadePessoasDesc;

    //*********************** Equals && Hashcode *******************************
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mesa other = (Mesa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    //************************** get && setts **********************************
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the numeroMesa
     */
    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    /**
     * @param numeroMesa the numeroMesa to set
     */
    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    /**
     * @return the quantidadePessoas
     */
    public Integer getQuantidadePessoas() {
        return quantidadePessoas;
    }

    /**
     * @param quantidadePessoas the quantidadePessoas to set
     */
    public void setQuantidadePessoas(Integer quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * @return the numeroMesaDesc
     */
    public String getNumeroMesaDesc() {
        return numeroMesaDesc;
    }

    /**
     * @param numeroMesaDesc the numeroMesaDesc to set
     */
    public void setNumeroMesaDesc(String numeroMesaDesc) {
        this.numeroMesaDesc = numeroMesaDesc;
    }

    /**
     * @return the quantidadePessoasDesc
     */
    public String getQuantidadePessoasDesc() {
        return quantidadePessoasDesc;
    }

    /**
     * @param quantidadePessoasDesc the quantidadePessoasDesc to set
     */
    public void setQuantidadePessoasDesc(String quantidadePessoasDesc) {
        this.quantidadePessoasDesc = quantidadePessoasDesc;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

}
