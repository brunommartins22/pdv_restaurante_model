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
@Table(name = "cnae_divisao")
public class CnaeDivisao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cnae_divisao")
    @SequenceGenerator(name = "gen_cnae_divisao", sequenceName = "seq_cnae_divisao")
    private Long id;
    @Column(length = 12, unique = true)
    private String codigoDivisao;
    @Column(length = 255)
    private String descricaoDivisao;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "cnae_divisao_fk_cnae_grupo"))
    private List<CnaeGrupo> listCnaeGrupos;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    //****************************** get && setts ******************************
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
     * @return the codigoDivisao
     */
    public String getCodigoDivisao() {
        return codigoDivisao;
    }

    /**
     * @param codigoDivisao the codigoDivisao to set
     */
    public void setCodigoDivisao(String codigoDivisao) {
        this.codigoDivisao = codigoDivisao;
    }

    /**
     * @return the descricaoDivisao
     */
    public String getDescricaoDivisao() {
        return descricaoDivisao;
    }

    /**
     * @param descricaoDivisao the descricaoDivisao to set
     */
    public void setDescricaoDivisao(String descricaoDivisao) {
        this.descricaoDivisao = descricaoDivisao;
    }

    /**
     * @return the listCnaeGrupos
     */
    public List<CnaeGrupo> getListCnaeGrupos() {
        if (listCnaeGrupos == null) {
            listCnaeGrupos = new ArrayList<>();
        }
        return listCnaeGrupos;
    }

    /**
     * @param listCnaeGrupos the listCnaeGrupos to set
     */
    public void setListCnaeGrupos(List<CnaeGrupo> listCnaeGrupos) {
        this.listCnaeGrupos = listCnaeGrupos;
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
