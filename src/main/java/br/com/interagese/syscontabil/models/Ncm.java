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
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "ncm")
public class Ncm implements Serializable {

    @Id
    private String codigoNcm;
    @Column(length = 12, nullable = false, unique = true)
    private String nomeNcm;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    //************************** Equals && HashCode ****************************
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.getCodigoNcm());
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
        final Ncm other = (Ncm) obj;
        if (!Objects.equals(this.codigoNcm, other.codigoNcm)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.Ncm{" + "id=" + getCodigoNcm() + '}';
    }

    //*************************** get && setts *********************************

    /**
     * @return the codigoNcm
     */
    public String getCodigoNcm() {
        return codigoNcm;
    }

    /**
     * @param codigoNcm the codigoNcm to set
     */
    public void setCodigoNcm(String codigoNcm) {
        this.codigoNcm = codigoNcm;
    }

    /**
     * @return the nomeNcm
     */
    public String getNomeNcm() {
        return nomeNcm;
    }

    /**
     * @param nomeNcm the nomeNcm to set
     */
    public void setNomeNcm(String nomeNcm) {
        this.nomeNcm = nomeNcm;
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
