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
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Joao
 */
@Entity
public class CenarioRegimeTributario implements Serializable {

    @Id
    private CenarioRegimeTributarioPK id ;
    @Embedded
    private TributoFederal tributoFederal = new TributoFederal();
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    //************************* Equals && HashCode *****************************
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.getId());
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
        final CenarioRegimeTributario other = (CenarioRegimeTributario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.CenarioRegimeTributario{" + "id=" + getId() + '}';
    }

    public CenarioRegimeTributarioPK getId() {
        return id;
    }

    public void setId(CenarioRegimeTributarioPK id) {
        this.id = id;
    }

    public TributoFederal getTributoFederal() {
        return tributoFederal;
    }

    public void setTributoFederal(TributoFederal tributoFederal) {
        this.tributoFederal = tributoFederal;
    }

    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

    

}
