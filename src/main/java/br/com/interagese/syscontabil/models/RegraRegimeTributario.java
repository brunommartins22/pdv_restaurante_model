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
    private DominioRegime regimeTributarioId;
    @Embedded
    private TributoFederal tributoFederal = new TributoFederal();
    @Embedded
    private TributoEstadual tributoEstadual = new TributoEstadual();
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    //************************* Equals && HashCode *****************************
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.getRegimeTributarioId());
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
        if (!Objects.equals(this.regimeTributarioId, other.regimeTributarioId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.RegraRegimeTributario{" + "id=" + getRegimeTributarioId() + '}';
    }

    //****************************** get && setts ******************************
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

    /**
     * @return the tributoFederal
     */
    public TributoFederal getTributoFederal() {
        return tributoFederal;
    }

    /**
     * @param tributoFederal the tributoFederal to set
     */
    public void setTributoFederal(TributoFederal tributoFederal) {
        this.tributoFederal = tributoFederal;
    }

    /**
     * @return the regimeTributarioId
     */
    public DominioRegime getRegimeTributarioId() {
        return regimeTributarioId;
    }

    /**
     * @param regimeTributarioId the regimeTributarioId to set
     */
    public void setRegimeTributarioId(DominioRegime regimeTributarioId) {
        this.regimeTributarioId = regimeTributarioId;
    }

    /**
     * @return the tributoEstadual
     */
    public TributoEstadual getTributoEstadual() {
        return tributoEstadual;
    }

    /**
     * @param tributoEstadual the tributoEstadual to set
     */
    public void setTributoEstadual(TributoEstadual tributoEstadual) {
        this.tributoEstadual = tributoEstadual;
    }

}
