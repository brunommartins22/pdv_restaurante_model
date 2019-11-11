/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.restaurantemodel.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "historico_preco_produto")
public class HistoricoPrecoProduto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "gen_historico_preco_produto")
    @SequenceGenerator(name = "gen_historico_preco_produto", sequenceName = "seq_historico_preco_produto")
    private Integer id;
    @Temporal(TemporalType.DATE)
    private Date dataRegistro;
    private Double precoCompra = 0.0;
    private Double precoVendaAtacado = 0.0;
    private Double percentualVendaAtacado = 0.0;
    private Double precoVendaVarejo = 0.0;
    private Double percentualVendaVarejo = 0.0;

    //*************************** equals && hashcode ***************************
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final HistoricoPrecoProduto other = (HistoricoPrecoProduto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    //****************************** get && setts ******************************
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(Double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public Double getPrecoVendaAtacado() {
        return precoVendaAtacado;
    }

    public void setPrecoVendaAtacado(Double precoVendaAtacado) {
        this.precoVendaAtacado = precoVendaAtacado;
    }

    public Double getPercentualVendaAtacado() {
        return percentualVendaAtacado;
    }

    public void setPercentualVendaAtacado(Double percentualVendaAtacado) {
        this.percentualVendaAtacado = percentualVendaAtacado;
    }

    public Double getPrecoVendaVarejo() {
        return precoVendaVarejo;
    }

    public void setPrecoVendaVarejo(Double precoVendaVarejo) {
        this.precoVendaVarejo = precoVendaVarejo;
    }

    public Double getPercentualVendaVarejo() {
        return percentualVendaVarejo;
    }

    public void setPercentualVendaVarejo(Double percentualVendaVarejo) {
        this.percentualVendaVarejo = percentualVendaVarejo;
    }

}
