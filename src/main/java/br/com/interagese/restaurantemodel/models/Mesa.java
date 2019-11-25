/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.restaurantemodel.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
    @OneToMany(cascade = CascadeType.ALL)
    private List<Ambiente> listaAmbientes;
    private boolean ativo;

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


    public List<Ambiente> getListaAmbientes() {
        return listaAmbientes;
    }

    public void setListaAmbientes(List<Ambiente> listaAmbientes) {
        this.listaAmbientes = listaAmbientes;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
