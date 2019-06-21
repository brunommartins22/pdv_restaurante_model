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
@Table(name = "regra_cnae")
public class RegraCnae implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_regra_cnae")
    @SequenceGenerator(name = "gen_regra_cnae", sequenceName = "seq_regra_cnae")
    private Long id;
    private String codigoCnae;
    private String denominacao;
    @Enumerated(EnumType.STRING)
    private DominioRegime dominioRegime;
    @Embedded
    private TributoEstadual tributoEstadual = new TributoEstadual();
    @Embedded
    private TributoFederal tributoFederal = new TributoFederal();
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    //************************** Equals && Hashcode ****************************
    @Override
    public int hashCode() {
        int hash = 7;
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
        final RegraCnae other = (RegraCnae) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    //************************** Get && setts **********************************

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
     * @return the denominacao
     */
    public String getDenominacao() {
        return denominacao;
    }

    /**
     * @param denominacao the denominacao to set
     */
    public void setDenominacao(String denominacao) {
        this.denominacao = denominacao;
    }

    /**
     * @return the codigoCnae
     */
    public String getCodigoCnae() {
        return codigoCnae;
    }

    /**
     * @param codigoCnae the codigoCnae to set
     */
    public void setCodigoCnae(String codigoCnae) {
        this.codigoCnae = codigoCnae;
    }

    /**
     * @return the dominioRegime
     */
    public DominioRegime getDominioRegime() {
        return dominioRegime;
    }

    /**
     * @param dominioRegime the dominioRegime to set
     */
    public void setDominioRegime(DominioRegime dominioRegime) {
        this.dominioRegime = dominioRegime;
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
