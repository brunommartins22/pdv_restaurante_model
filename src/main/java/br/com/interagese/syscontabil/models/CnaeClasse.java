/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "cnae_classe")
public class CnaeClasse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cnae_classe")
    @SequenceGenerator(name = "gen_cnae_classe", sequenceName = "seq_cnae_classe")
    private Long id;
    @Column(length = 12, nullable = false, unique = true)
    private String codigoClasse;
    @Column(length = 255, nullable = false)
    private String descricaoClasse;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "cnae_classe_fk_cnae_subclasse"))
    private List<CnaeSubclasse> listCnaeSubclasse;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    //************************** Equals && Hashcode ****************************
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.getId());
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
        final CnaeClasse other = (CnaeClasse) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.model.CnaeClasse{" + "id=" + getId() + '}';
    }

    //*************************** get && setts *********************************
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
     * @return the codigoClasse
     */
    public String getCodigoClasse() {
        return codigoClasse;
    }

    /**
     * @param codigoClasse the codigoClasse to set
     */
    public void setCodigoClasse(String codigoClasse) {
        this.codigoClasse = codigoClasse;
    }

    /**
     * @return the descricaoClasse
     */
    public String getDescricaoClasse() {
        return descricaoClasse;
    }

    /**
     * @param descricaoClasse the descricaoClasse to set
     */
    public void setDescricaoClasse(String descricaoClasse) {
        this.descricaoClasse = descricaoClasse;
    }

    /**
     * @return the listCnaeSubclasse
     */
    public List<CnaeSubclasse> getListCnaeSubclasse() {
        if (listCnaeSubclasse == null) {
            listCnaeSubclasse = new ArrayList<>();
        }
        return listCnaeSubclasse;
    }

    /**
     * @param listCnaeSubclasse the listCnaeSubclasse to set
     */
    public void setListCnaeSubclasse(List<CnaeSubclasse> listCnaeSubclasse) {
        this.listCnaeSubclasse = listCnaeSubclasse;
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
