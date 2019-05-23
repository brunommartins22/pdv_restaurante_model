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
@Table(name = "regra_ncm")
public class RegraNcm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_regra_ncm")
    @SequenceGenerator(name = "gen_regra_ncm", sequenceName = "seq_regra_ncm")
    private Long id;
    private String cdigoNcm;
    @Embedded
    private TributoFederal tributoFederal = new TributoFederal();
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

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
        final RegraNcm other = (RegraNcm) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.RegraNcm{" + "id=" + getId() + '}';
    }

    //***************************** get && setts *******************************
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
     * @return the cdigoNcm
     */
    public String getCdigoNcm() {
        return cdigoNcm;
    }

    /**
     * @param cdigoNcm the cdigoNcm to set
     */
    public void setCdigoNcm(String cdigoNcm) {
        this.cdigoNcm = cdigoNcm;
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
