/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.restaurantemodel.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_produto")
    @SequenceGenerator(name = "gen_produto", sequenceName = "seq_produto")
    private Integer id;
    @Column(length = 255, nullable = false)
    private String nmProduto;
    @Column(length = 14)
    private String codBarras;
    @Column(length = 1)
    private String tipoProduto;
    @ManyToOne(cascade = CascadeType.ALL)
    private Fornecedor fornecedor;
    @ManyToOne(cascade = CascadeType.ALL)
    private Unidade unidade;
    @ManyToOne(cascade = CascadeType.ALL)
    private Departamento departamento;
    @ManyToOne(cascade = CascadeType.ALL)
    private Secao secao;
    @Column(length = 8)
    private String ncm;
    @Column(length = 7)
    private String cfop;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoPrecoProduto> listHistoricoPrecoProduto;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComposicaoProduto> listComposicaoProduto;
    @Transient
    private Double precoVarejo;
    @Transient
    private Double precoAtacado;

    //************************* equals && hashcode *****************************
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    //******************************* get && setters ***************************

    public String getTipoProdutoDesc() {
        String resp = "";
        if (tipoProduto != null && !tipoProduto.isEmpty()) {
            switch (tipoProduto) {
                case "1": {
                    resp = "Simples";
                    break;
                }
                case "2": {
                    resp = "Composto";
                    break;
                }
            }
        }
        return resp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nmProduto
     */
    public String getNmProduto() {
        return nmProduto;
    }

    /**
     * @param nmProduto the nmProduto to set
     */
    public void setNmProduto(String nmProduto) {
        this.nmProduto = nmProduto;
    }

    /**
     * @return the fornecedor
     */
    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    /**
     * @param fornecedor the fornecedor to set
     */
    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    /**
     * @return the unidade
     */
    public Unidade getUnidade() {
        return unidade;
    }

    /**
     * @param unidade the unidade to set
     */
    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    /**
     * @return the codBarras
     */
    public String getCodBarras() {
        return codBarras;
    }

    /**
     * @param codBarras the codBarras to set
     */
    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    /**
     * @return the tipoProduto
     */
    public String getTipoProduto() {
        return tipoProduto;
    }

    /**
     * @param tipoProduto the tipoProduto to set
     */
    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    /**
     * @return the listHistoricoPrecoProduto
     */
    public List<HistoricoPrecoProduto> getListHistoricoPrecoProduto() {
        if (listHistoricoPrecoProduto == null) {
            listHistoricoPrecoProduto = new ArrayList<>();
        }
        return listHistoricoPrecoProduto;
    }

    /**
     * @param listHistoricoPrecoProduto the listHistoricoPrecoProduto to set
     */
    public void setListHistoricoPrecoProduto(List<HistoricoPrecoProduto> listHistoricoPrecoProduto) {
        this.listHistoricoPrecoProduto = listHistoricoPrecoProduto;
    }

    public List<ComposicaoProduto> getListComposicaoProduto() {
        if (listComposicaoProduto == null) {
            listComposicaoProduto = new ArrayList<>();
        }
        return listComposicaoProduto;
    }

    public void setListComposicaoProduto(List<ComposicaoProduto> listComposicaoProduto) {
        this.listComposicaoProduto = listComposicaoProduto;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Secao getSecao() {
        return secao;
    }

    public void setSecao(Secao secao) {
        this.secao = secao;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    /**
     * @return the precoVarejo
     */
    public Double getPrecoVarejo() {

        return precoVarejo == null ? 0.0 : precoVarejo;
    }

    /**
     * @param precoVarejo the precoVarejo to set
     */
    public void setPrecoVarejo(Double precoVarejo) {
        this.precoVarejo = precoVarejo;
    }

    /**
     * @return the precoAtacado
     */
    public Double getPrecoAtacado() {
        return precoAtacado == null ? 0.0 : precoAtacado;
    }

    /**
     * @param precoAtacado the precoAtacado to set
     */
    public void setPrecoAtacado(Double precoAtacado) {
        this.precoAtacado = precoAtacado;
    }

}
