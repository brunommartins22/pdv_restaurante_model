/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author adam
 */
@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_produto")
    @SequenceGenerator(name = "gen_produto", sequenceName = "seq_produto")
    private Long id;
    @Column(length = 255,nullable = false,unique = true)
    private String nomeProduto;
    @Column(length = 50)
    private String codigoProduto;
    @Column(length = 20)
    private Long ean;
    @Column(length = 50)
    private String genero;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "produto_fk_ncm"))
    private Ncm ncm;
    @Column(length = 20,nullable = false)
    private String cest;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    //************************** Equals && hashcode ****************************
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.Produto[ id=" + getId() + " ]";
    }

    //******************************* get && setts *****************************
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nomeProduto
     */
    public String getNomeProduto() {
        return nomeProduto;
    }

    /**
     * @param nomeProduto the nomeProduto to set
     */
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    /**
     * @return the codigoProduto
     */
    public String getCodigoProduto() {
        return codigoProduto;
    }

    /**
     * @param codigoProduto the codigoProduto to set
     */
    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    /**
     * @return the ean
     */
    public Long getEan() {
        return ean;
    }

    /**
     * @param ean the ean to set
     */
    public void setEan(Long ean) {
        this.ean = ean;
    }

    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * @return the ncm
     */
    public Ncm getNcm() {
        return ncm;
    }

    /**
     * @param ncm the ncm to set
     */
    public void setNcm(Ncm ncm) {
        this.ncm = ncm;
    }

    /**
     * @return the cest
     */
    public String getCest() {
        return cest;
    }

    /**
     * @param cest the cest to set
     */
    public void setCest(String cest) {
        this.cest = cest;
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