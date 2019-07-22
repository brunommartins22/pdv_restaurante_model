/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "cenario_ncm")
public class CenarioNcm implements Serializable {

    @EmbeddedId
    private CenarioNcmPK id;
    @Embedded
    private TributoFederalPadrao tributoFederal = new TributoFederalPadrao();
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();
    
    public CenarioNcm(){
    }
    
    public CenarioNcm(CenarioNcmPK id){
        this.id = id;
    }
    
    //**************************** Equals && HashCode **************************
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.getId());
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
        final CenarioNcm other = (CenarioNcm) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.CenarioNcm{" + "id=" + getId() + '}';
    }

    //***************************** get && setts *******************************
    /**
     * @return the id
     */
    public CenarioNcmPK getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(CenarioNcmPK id) {
        this.id = id;
    }

    /**
     * @return the tributoFederal
     */
    public TributoFederalPadrao getTributoFederal() {
        return tributoFederal;
    }

    /**
     * @param tributoFederal the tributoFederal to set
     */
    public void setTributoFederal(TributoFederalPadrao tributoFederal) {
        this.tributoFederal = tributoFederal;
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
