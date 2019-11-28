/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.restaurantemodel.models;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "ambiente")
public class Ambiente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_ambiente")
    @SequenceGenerator(name = "gen_ambiente", sequenceName = "seq_ambiente")
    private Long id;
    @Column(length = 120, nullable = false)
    private String nomeAmbiente;
    @Column(nullable = false)
    private Integer quantidadeMesas;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Mesa> listMesas;
    private boolean ativo;

    //*************************** Equals && Hashcode ***************************
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.getId());
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
        final Ambiente other = (Ambiente) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    //**************************** get && setts ********************************
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
     * @return the nomeAmbiente
     */
    public String getNomeAmbiente() {
        return nomeAmbiente;
    }

    /**
     * @param nomeAmbiente the nomeAmbiente to set
     */
    public void setNomeAmbiente(String nomeAmbiente) {
        this.nomeAmbiente = nomeAmbiente;
    }

    /**
     * @return the quantidadeMesas
     */
    public Integer getQuantidadeMesas() {
        return quantidadeMesas;
    }

    /**
     * @param quantidadeMesas the quantidadeMesas to set
     */
    public void setQuantidadeMesas(Integer quantidadeMesas) {
        this.quantidadeMesas = quantidadeMesas;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Mesa> getListMesas() {
        if (listMesas == null) {
            listMesas = new ArrayList<>();
        }
        return listMesas;
    }

    public void setListMesas(List<Mesa> listMesas) {
        this.listMesas = listMesas;
    }

}
