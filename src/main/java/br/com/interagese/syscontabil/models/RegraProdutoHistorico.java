/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import br.com.interagese.syscontabil.domains.DominioRegime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "regra_produto_historico")
public class RegraProdutoHistorico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_regra_produto_historico")
    @SequenceGenerator(name = "gen_regra_produto_historico", sequenceName = "seq_regra_produto_historico", allocationSize = 1)
    private Long id;
    @ManyToOne
    private RegraProduto regraProduto;
    @ManyToOne
    private Cliente cliente;
    @Column(length = 12)
    private String codigoProduto;
    @Column(length = 20)
    private Long eanProduto;
    @ManyToOne
    private Cenario cenario;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private DominioRegime regimeTributario;
    @Embedded
    private TributoFederalPadrao tributoFederalPadrao = new TributoFederalPadrao();
    @Embedded
    private TributoEstadualPadrao tributoEstadualPadrao = new TributoEstadualPadrao();
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    @Column
    private String embasamentoJuridico;
    
    @Transient
    private String nomeProduto;
    @Transient
    private String nomeCliente;
    @Transient
    private String nomeCenario;
    @Transient
    private String nomeRegime;
    
    //**************************** Equals && HashCode **************************
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
        final RegraProdutoHistorico other = (RegraProdutoHistorico) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.RegraProduto{" + "id=" + getId() + '}';
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
     * @return the eanProduto
     */
    public Long getEanProduto() {
        return eanProduto;
    }

    /**
     * @param eanProduto the eanProduto to set
     */
    public void setEanProduto(Long eanProduto) {
        this.eanProduto = eanProduto;
    }

    /**
     * @return the cenario
     */
    public Cenario getCenario() {
        return cenario;
    }

    /**
     * @param cenario the cenario to set
     */
    public void setCenario(Cenario cenario) {
        this.cenario = cenario;
    }

    /**
     * @return the tributoFederalPadrao
     */
    public TributoFederalPadrao getTributoFederalPadrao() {
        return tributoFederalPadrao;
    }

    /**
     * @param tributoFederalPadrao the tributoFederalPadrao to set
     */
    public void setTributoFederalPadrao(TributoFederalPadrao tributoFederalPadrao) {
        this.tributoFederalPadrao = tributoFederalPadrao;
    }

    /**
     * @return the tributoEstadualPadrao
     */
    public TributoEstadualPadrao getTributoEstadualPadrao() {
        return tributoEstadualPadrao;
    }

    /**
     * @param tributoEstadualPadrao the tributoEstadualPadrao to set
     */
    public void setTributoEstadualPadrao(TributoEstadualPadrao tributoEstadualPadrao) {
        this.tributoEstadualPadrao = tributoEstadualPadrao;
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

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeCenario() {
        return nomeCenario;
    }

    public void setNomeCenario(String nomeCenario) {
        this.nomeCenario = nomeCenario;
    }

    public String getEmbasamentoJuridico() {
        return embasamentoJuridico;
    }

    public void setEmbasamentoJuridico(String embasamentoJuridico) {
        this.embasamentoJuridico = embasamentoJuridico;
    }

    public RegraProduto getRegraProduto() {
        return regraProduto;
    }

    public void setRegraProduto(RegraProduto regraProduto) {
        this.regraProduto = regraProduto;
    }

    public DominioRegime getRegimeTributario() {
        return regimeTributario;
    }

    public void setRegimeTributario(DominioRegime regimeTributario) {
        this.regimeTributario = regimeTributario;
    }

    public String getNomeRegime() {
        return nomeRegime;
    }

    public void setNomeRegime(String nomeRegime) {
        this.nomeRegime = nomeRegime;
    }

}
