/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import br.com.interagese.syscontabil.domains.DominioRegras;
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

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "produto_cenario")
public class ProdutoCenario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_produto_cenario")
    @SequenceGenerator(name = "gen_produto_cenario", sequenceName = "seq_produto_cenario")
    private Long id;
    @ManyToOne
    private ProdutoCliente produtoCliente;
    @ManyToOne
    private Cenario cenario;
    @Enumerated(EnumType.STRING)
    private DominioRegras dominioRegras;
    @Column(length = 20)
    private Long regraId;
    @Embedded
    private TributoFederalCliente tributoFederalCliente = new TributoFederalCliente();
    @Embedded
    private TributoEstadualCliente tributoEstadualCliente = new TributoEstadualCliente();
    @Embedded
    private TributoFederalPadrao tributoFederalPadrao = new TributoFederalPadrao();
    @Embedded
    private TributoEstadualPadrao tributoEstadualPadrao = new TributoEstadualPadrao();
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();
    @Column(nullable = false)
    private boolean divergente;
    @Column(nullable = false)
    private boolean confirmado;

    public ProdutoCenario() {
        this.confirmado = false;
    }

    //************************ equals && hashcode ******************************
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.getId());
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
        final ProdutoCenario other = (ProdutoCenario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.ProdutoCenario{" + "id=" + getId() + '}';
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
     * @return the produtoCliente
     */
    public ProdutoCliente getProdutoCliente() {
        return produtoCliente;
    }

    /**
     * @param produtoCliente the produtoCliente to set
     */
    public void setProdutoCliente(ProdutoCliente produtoCliente) {
        this.produtoCliente = produtoCliente;
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
     * @return the dominioRegras
     */
    public DominioRegras getDominioRegras() {
        return dominioRegras;
    }

    /**
     * @param dominioRegras the dominioRegras to set
     */
    public void setDominioRegras(DominioRegras dominioRegras) {
        this.dominioRegras = dominioRegras;
    }

    /**
     * @return the regraId
     */
    public Long getRegraId() {
        return regraId;
    }

    /**
     * @param regraId the regraId to set
     */
    public void setRegraId(Long regraId) {
        this.regraId = regraId;
    }

    /**
     * @return the tributoFederalCliente
     */
    public TributoFederalCliente getTributoFederalCliente() {
        return tributoFederalCliente;
    }

    /**
     * @param tributoFederalCliente the tributoFederalCliente to set
     */
    public void setTributoFederalCliente(TributoFederalCliente tributoFederalCliente) {
        this.tributoFederalCliente = tributoFederalCliente;
    }

    /**
     * @return the tributoEstadualCliente
     */
    public TributoEstadualCliente getTributoEstadualCliente() {
        return tributoEstadualCliente;
    }

    /**
     * @param tributoEstadualCliente the tributoEstadualCliente to set
     */
    public void setTributoEstadualCliente(TributoEstadualCliente tributoEstadualCliente) {
        this.tributoEstadualCliente = tributoEstadualCliente;
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

    /**
     * @return the divergente
     */
    public boolean isDivergente() {
        return divergente;
    }

    /**
     * @param divergente the divergente to set
     */
    public void setDivergente(boolean divergente) {
        this.divergente = divergente;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

}
