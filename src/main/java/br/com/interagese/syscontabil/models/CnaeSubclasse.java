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
@Table(name = "cnae_subclasse")
public class CnaeSubclasse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cnae_subclasse")
    @SequenceGenerator(name = "gen_cnae_subclasse", sequenceName = "seq_cnae_subclasse")
    private Long id;
    @Column(length = 12, nullable = false, unique = true)
    private String codigoSubclasse;
    @Column(length = 255, nullable = false)
    private String descricaoSubclasse;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    //**************************** Eequals && Hashcode *************************
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final CnaeSubclasse other = (CnaeSubclasse) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.CnaeSubclasse{" + "id=" + id + '}';
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
     * @return the codigoSubclasse
     */
    public String getCodigoSubclasse() {
        return codigoSubclasse;
    }

    /**
     * @param codigoSubclasse the codigoSubclasse to set
     */
    public void setCodigoSubclasse(String codigoSubclasse) {
        this.codigoSubclasse = codigoSubclasse;
    }

    /**
     * @return the descricaoSubclasse
     */
    public String getDescricaoSubclasse() {
        return descricaoSubclasse;
    }

    /**
     * @param descricaoSubclasse the descricaoSubclasse to set
     */
    public void setDescricaoSubclasse(String descricaoSubclasse) {
        this.descricaoSubclasse = descricaoSubclasse;
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
