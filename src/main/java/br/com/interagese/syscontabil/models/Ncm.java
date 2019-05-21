/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "ncm")
public class Ncm implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_ncm")
    @SequenceGenerator(name = "gen_ncm",sequenceName = "seq_ncm")
    private Long id;
    @Column(length = 12,nullable = false,unique = true)
    private Long codigoNcm;
    @Column(length = 12,nullable = false,unique = true)
    private String nomeNcm;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CenarioEstadual> listCenariosEstaduais;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CenarioFederal> listCenariosFederais;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();
    
    
    //************************** Equals && HashCode ****************************

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.getId());
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.Ncm{" + "id=" + getId() + '}';
    }
        
    
    
    //*************************** get && setts *********************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the codigoNcm
     */
    public Long getCodigoNcm() {
        return codigoNcm;
    }

    /**
     * @param codigoNcm the codigoNcm to set
     */
    public void setCodigoNcm(Long codigoNcm) {
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
     * @return the listCenariosEstaduais
     */
    public List<CenarioEstadual> getListCenariosEstaduais() {
        return listCenariosEstaduais;
    }

    /**
     * @param listCenariosEstaduais the listCenariosEstaduais to set
     */
    public void setListCenariosEstaduais(List<CenarioEstadual> listCenariosEstaduais) {
        this.listCenariosEstaduais = listCenariosEstaduais;
    }

    /**
     * @return the listCenariosFederais
     */
    public List<CenarioFederal> getListCenariosFederais() {
        return listCenariosFederais;
    }

    /**
     * @param listCenariosFederais the listCenariosFederais to set
     */
    public void setListCenariosFederais(List<CenarioFederal> listCenariosFederais) {
        this.listCenariosFederais = listCenariosFederais;
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
