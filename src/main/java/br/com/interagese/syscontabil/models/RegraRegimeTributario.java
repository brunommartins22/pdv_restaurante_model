/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import br.com.interagese.syscontabil.domains.DominioRegime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Joao
 */
@Entity
@Table(name = "regra_regime_tributario")
public class RegraRegimeTributario implements Serializable {

    @Id
    @Enumerated(EnumType.STRING)
    private DominioRegime id;
    @Embedded
    private TributoFederalPadrao tributoFederalPadrao = new TributoFederalPadrao();
    @Embedded
    private TributoEstadualPadrao tributoEstadualPadrao = new TributoEstadualPadrao();
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
        final RegraRegimeTributario other = (RegraRegimeTributario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.RegraRegimeTributario{" + "id=" + getId() + '}';
    }

    //****************************** get && setts ******************************

    /**
     * @return the id
     */
    public DominioRegime getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(DominioRegime id) {
        this.id = id;
    }

    /**
     * @return the tributoFederalPadrao
     */
    public TributoFederalPadrao getTributoFederalPadrao() {
        return tributoFederalPadrao;
    }

    /**
     * @param tributoFederalPadrao the tributoFederalPadrao to set
     */
    public void setTributoFederalPadrao(TributoFederalPadrao tributoFederalPadrao) {
        this.tributoFederalPadrao = tributoFederalPadrao;
    }

    /**
     * @return the tributoEstadualPadrao
     */
    public TributoEstadualPadrao getTributoEstadualPadrao() {
        return tributoEstadualPadrao;
    }

    /**
     * @param tributoEstadualPadrao the tributoEstadualPadrao to set
     */
    public void setTributoEstadualPadrao(TributoEstadualPadrao tributoEstadualPadrao) {
        this.tributoEstadualPadrao = tributoEstadualPadrao;
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
