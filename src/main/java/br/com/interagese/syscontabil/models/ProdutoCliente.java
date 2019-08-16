/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "produto_cliente")
public class ProdutoCliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_produto_cliente")
    @SequenceGenerator(name = "gen_produto_cliente", sequenceName = "seq_produto_cliente")
    private Long id;
    @ManyToOne
    private Cliente cliente;
    @Column(length = 255)
    private String nomeProduto;
    @Column(length = 120)
    private String codigoProduto;
    @Column(length = 13, unique = true)
    private Long ean;
    @Column(length = 8)
    private String ncmCliente;
    @Column(length = 8)
    private String ncmPadrao;
    @Column(length = 7)
    private String cestCliente;
    @Column(length = 7)
    private String cestPadrao;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();
    @Transient
    private boolean isProdutoGeral;

    //************************* Equals && Hashcode *****************************
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.getId());
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
        final ProdutoCliente other = (ProdutoCliente) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.PordutoCliente{" + "id=" + getId() + '}';
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
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
     * @return the ncmCliente
     */
    public String getNcmCliente() {
        return ncmCliente;
    }

    /**
     * @param ncmCliente the ncmCliente to set
     */
    public void setNcmCliente(String ncmCliente) {
        this.ncmCliente = ncmCliente;
    }

    /**
     * @return the ncmPadrao
     */
    public String getNcmPadrao() {
        return ncmPadrao;
    }

    /**
     * @param ncmPadrao the ncmPadrao to set
     */
    public void setNcmPadrao(String ncmPadrao) {
        this.ncmPadrao = ncmPadrao;
    }

    /**
     * @return the cestCliente
     */
    public String getCestCliente() {
        return cestCliente;
    }

    /**
     * @param cestCliente the cestCliente to set
     */
    public void setCestCliente(String cestCliente) {
        this.cestCliente = cestCliente;
    }

    /**
     * @return the cestPadrao
     */
    public String getCestPadrao() {
        return cestPadrao;
    }

    /**
     * @param cestPadrao the cestPadrao to set
     */
    public void setCestPadrao(String cestPadrao) {
        this.cestPadrao = cestPadrao;
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

    /**
     * @return the isProdutoGeral
     */
    public boolean isIsProdutoGeral() {
        return isProdutoGeral;
    }

    /**
     * @param isProdutoGeral the isProdutoGeral to set
     */
    public void setIsProdutoGeral(boolean isProdutoGeral) {
        this.isProdutoGeral = isProdutoGeral;
    }

}
