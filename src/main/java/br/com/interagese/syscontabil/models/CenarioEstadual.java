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
@Table(name = "cenario_estadual")
public class CenarioEstadual implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cenario_estadual")
    @SequenceGenerator(name = "gen_cenario_estadual", sequenceName = "seq_cenario_estadual")
    private Long id;
    @Column(length = 255, nullable = false, unique = true)
    private String nomeCenario;
    @Column(length = 12,nullable = false)
    private String crt;
    @Column(length = 12)
    private String cfop;
   @Embedded
   private TributoEstadualPadrao tributoEstadual = new TributoEstadualPadrao();
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    //******************************* Equals && hashcode ***********************
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
        final CenarioEstadual other = (CenarioEstadual) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.CenarioEstadual{" + "id=" + getId() + '}';
    }

    //******************************** get && setts ****************************
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nomeCenario
     */
    public String getNomeCenario() {
        return nomeCenario;
    }

    /**
     * @param nomeCenario the nomeCenario to set
     */
    public void setNomeCenario(String nomeCenario) {
        this.nomeCenario = nomeCenario;
    }

    /**
     * @return the crt
     */
    public String getCrt() {
        return crt;
    }

    /**
     * @param crt the crt to set
     */
    public void setCrt(String crt) {
        this.crt = crt;
    }

    /**
     * @return the cfop
     */
    public String getCfop() {
        return cfop;
    }

    /**
     * @param cfop the cfop to set
     */
    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    /**
     * @return the tributoEstadual
     */
    public TributoEstadualPadrao getTributoEstadual() {
        return tributoEstadual;
    }

    /**
     * @param tributoEstadual the tributoEstadual to set
     */
    public void setTributoEstadual(TributoEstadualPadrao tributoEstadual) {
        this.tributoEstadual = tributoEstadual;
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
