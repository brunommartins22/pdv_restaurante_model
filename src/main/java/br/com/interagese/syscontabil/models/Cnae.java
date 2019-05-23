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
@Table(name = "cnae")
public class Cnae implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cnae")
    @SequenceGenerator(name = "gen_cnae", sequenceName = "seq_cnae")
    private Long id;
    @Column(length = 12, nullable = false, unique = true)
    private String secao;
    @Column(length = 255, nullable = false)
    private String nome;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "cnae_fk_cnae_divisao"))
    private List<CnaeDivisao> listCnaeDivisoes;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    //************************* Equals && Hashcode *****************************
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.getId());
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
        final Cnae other = (Cnae) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.Cnae{" + "id=" + getId() + '}';
    }

    //************************* Get && Setts ***********************************
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
     * @return the secao
     */
    public String getSecao() {
        return secao;
    }

    /**
     * @param secao the secao to set
     */
    public void setSecao(String secao) {
        this.secao = secao;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the listCnaeDivisoes
     */
    public List<CnaeDivisao> getListCnaeDivisoes() {
        if (listCnaeDivisoes == null) {
            listCnaeDivisoes = new ArrayList<>();
        }
        return listCnaeDivisoes;
    }

    /**
     * @param listCnaeDivisoes the listCnaeDivisoes to set
     */
    public void setListCnaeDivisoes(List<CnaeDivisao> listCnaeDivisoes) {
        this.listCnaeDivisoes = listCnaeDivisoes;
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
