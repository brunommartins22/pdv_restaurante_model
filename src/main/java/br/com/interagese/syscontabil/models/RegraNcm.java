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
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

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
    
    @Column(length = 8, nullable = false)
    private String ncm;
    
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private DominioRegime regimeTributario;
    
    @Column
    private Long cenarioId;
    
    @Embedded
    private TributoFederalPadrao tributoFederalPadrao = new TributoFederalPadrao();
    @Embedded
    private TributoEstadualPadrao tributoEstadualPadrao = new TributoEstadualPadrao();
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    @Transient
    private String nomeRegime;
    
    @Transient
    private String nomeCenario;
    
    public RegraNcm() {
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
     * @return the ncm
     */
    public String getNcm() {
        return ncm;
    }

    /**
     * @param ncm the ncm to set
     */
    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    /**
     * @return the regimeTributario
     */
    public DominioRegime getRegimeTributario() {
        return regimeTributario;
    }

    /**
     * @param regimeTributario the regimeTributario to set
     */
    public void setRegimeTributario(DominioRegime regimeTributario) {
        this.regimeTributario = regimeTributario;
    }

    /**
     * @return the cenarioId
     */
    public Long getCenarioId() {
        return cenarioId;
    }

    /**
     * @param cenarioId the cenarioId to set
     */
    public void setCenarioId(Long cenarioId) {
        this.cenarioId = cenarioId;
    }
    
    public TributoFederalPadrao getTributoFederalPadrao() {
        return tributoFederalPadrao;
    }

    public void setTributoFederalPadrao(TributoFederalPadrao tributoFederalPadrao) {
        this.tributoFederalPadrao = tributoFederalPadrao;
    }

    public TributoEstadualPadrao getTributoEstadualPadrao() {
        return tributoEstadualPadrao;
    }

    public void setTributoEstadualPadrao(TributoEstadualPadrao tributoEstadualPadrao) {
        this.tributoEstadualPadrao = tributoEstadualPadrao;
    }

    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

    public String getNomeRegime() {
        return nomeRegime;
    }

    public void setNomeRegime(String nomeRegime) {
        this.nomeRegime = nomeRegime;
    }

    public String getNomeCenario() {
        return nomeCenario;
    }

    public void setNomeCenario(String nomeCenario) {
        this.nomeCenario = nomeCenario;
    }

    
  
}
