/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import br.com.interagese.padrao.rest.models.Estado;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "cenario")
public class Cenario implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "gen_cenario")
    @SequenceGenerator(name = "gen_cenario",sequenceName = "seq_cenario")
    private Long id;
    @Column(length = 255, nullable = false, unique = true)
    private String nomeCenario;
    @ManyToOne
    private Estado estado;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();
    
    
    //************************* Equals && Hashcode *****************************
    
    @Override
    public int hashCode(){
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.getId());
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
        final Cenario other = (Cenario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.Cenario{" + "id=" + getId() + '}';
    }
    
    //************************* get && setts ***********************************
    public Long getId() {
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }

    /**
     * @return the nomeCenario
     */
    public String getNomeCenario() {
        return nomeCenario;
    }

    /**
     * @param nomeCenario the nomeCenario to set
     */
    public void setNomeCenario(String nomeCenario) {
        this.nomeCenario = nomeCenario;
    }

    /**
     * @return the atributoPadrao
     */
    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    /**
     * @param atributoPadrao the atributoPadrao to set
     */
    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
}
