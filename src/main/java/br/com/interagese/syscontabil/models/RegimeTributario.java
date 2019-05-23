/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "regime_tributario")
public class RegimeTributario implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_regime_tributario")
    @SequenceGenerator(name = "gen_regime_tributario",sequenceName = "seq_regime_tributario")
    private Long id;
    @Column(length = 120,nullable = false,unique = true)
    private String nomeRegimeTributario;
    @Column(length = 5,unique = true)
    private String siglaRegimeTributario;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();
    
    //************************ Equal && HashCode *******************************

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.getId());
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
        final RegimeTributario other = (RegimeTributario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RegimeTributario{" + "id=" + getId() + '}';
    }
    
    //****************************** get && setts ******************************

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
     * @return the nomeRegimeTributario
     */
    public String getNomeRegimeTributario() {
        return nomeRegimeTributario;
    }

    /**
     * @param nomeRegimeTributario the nomeRegimeTributario to set
     */
    public void setNomeRegimeTributario(String nomeRegimeTributario) {
        this.nomeRegimeTributario = nomeRegimeTributario;
    }

    /**
     * @return the siglaRegimeTributario
     */
    public String getSiglaRegimeTributario() {
        return siglaRegimeTributario;
    }

    /**
     * @param siglaRegimeTributario the siglaRegimeTributario to set
     */
    public void setSiglaRegimeTributario(String siglaRegimeTributario) {
        this.siglaRegimeTributario = siglaRegimeTributario;
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
    
    
    
    
    
}
