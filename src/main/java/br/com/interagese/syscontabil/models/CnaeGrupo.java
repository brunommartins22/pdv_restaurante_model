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
@Table(name = "cnae_grupo")
public class CnaeGrupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cnae_grupo")
    @SequenceGenerator(name = "gen_cnae_grupo", sequenceName = "seq_cnae_grupo")
    private Long id;
    @Column(length = 12,nullable = false,unique = true)
    private String codigoGrupo;
    @Column(length = 255,nullable = false)
    private String descricaoGrupo;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "cnae_grupo_fk_cnae_classe"))
    private List<CnaeClasse> listCnaeClasses = new ArrayList<>();
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    //*************************** Equals && HashCode ***************************
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final CnaeGrupo other = (CnaeGrupo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.CnaeGrupo{" + "id=" + id + '}';
    }

    //**************************** get && setts ********************************
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
     * @return the codigoGrupo
     */
    public String getCodigoGrupo() {
        return codigoGrupo;
    }

    /**
     * @param codigoGrupo the codigoGrupo to set
     */
    public void setCodigoGrupo(String codigoGrupo) {
        this.codigoGrupo = codigoGrupo;
    }

    /**
     * @return the descricaoGrupo
     */
    public String getDescricaoGrupo() {
        return descricaoGrupo;
    }

    /**
     * @param descricaoGrupo the descricaoGrupo to set
     */
    public void setDescricaoGrupo(String descricaoGrupo) {
        this.descricaoGrupo = descricaoGrupo;
    }

    /**
     * @return the listCnaeClasses
     */
    public List<CnaeClasse> getListCnaeClasses() {
        if (listCnaeClasses == null) {
            listCnaeClasses = new ArrayList<>();
        }
        return listCnaeClasses;
    }

    /**
     * @param listCnaeClasses the listCnaeClasses to set
     */
    public void setListCnaeClasses(List<CnaeClasse> listCnaeClasses) {
        this.listCnaeClasses = listCnaeClasses;
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
