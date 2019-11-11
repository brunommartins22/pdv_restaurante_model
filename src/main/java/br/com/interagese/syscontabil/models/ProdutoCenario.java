/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import br.com.interagese.syscontabil.domains.DominioRegras;
import br.com.interagese.syscontabil.dto.CountProdutos;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
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
@Table(name = "produto_cenario")
public class ProdutoCenario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_produto_cenario")
    @SequenceGenerator(name = "gen_produto_cenario", sequenceName = "seq_produto_cenario")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private ProdutoCliente produtoCliente;
    @ManyToOne
    private Cenario cenario;
    @Enumerated(EnumType.STRING)
    private DominioRegras dominioRegras;
    @Transient
    @Enumerated(EnumType.STRING)
    private DominioRegras dominioRegrasInformadoBotaoDireito;
    @Transient
    @Enumerated(EnumType.STRING)
    private DominioRegras dominioRegrasInformado;
    @Enumerated(EnumType.STRING)
    private DominioRegras dominioRegrasConfirmado;
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
    private TributoFederalInformado tributoFederalInformado = new TributoFederalInformado();
    @Embedded
    private TributoEstadualInformado tributoEstadualInformado = new TributoEstadualInformado();
    @Embedded
    private TributoFederalConfirmado tributoFederalConfirmado = new TributoFederalConfirmado();
    @Embedded
    private TributoEstadualConfirmado tributoEstadualConfirmado = new TributoEstadualConfirmado();
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();
    @Column(nullable = false)
    private boolean divergente;
    @Column(nullable = false)
    private boolean confirmado;
    @Transient
    private String status;
    @Transient
    private boolean isEdited;
    @Transient
    private boolean isCheck;
    @Transient
    private CountProdutos countProdutos;

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
        if (tributoFederalCliente == null) {
            tributoFederalCliente = new TributoFederalCliente();
        }
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
        if (tributoEstadualCliente == null) {
            tributoEstadualCliente = new TributoEstadualCliente();
        }
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
        if (tributoFederalPadrao == null) {
            tributoFederalPadrao = new TributoFederalPadrao();
        }
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
        if (tributoEstadualPadrao == null) {
            tributoEstadualPadrao = new TributoEstadualPadrao();
        }
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

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    /**
     * @return the tributoFederalInformado
     */
    public TributoFederalInformado getTributoFederalInformado() {
        if (tributoFederalInformado == null) {
            tributoFederalInformado = new TributoFederalInformado();
        }
        return tributoFederalInformado;
    }

    /**
     * @param tributoFederalInformado the tributoFederalInformado to set
     */
    public void setTributoFederalInformado(TributoFederalInformado tributoFederalInformado) {
        this.tributoFederalInformado = tributoFederalInformado;
    }

    /**
     * @return the tributoEstadualInformado
     */
    public TributoEstadualInformado getTributoEstadualInformado() {
        if (tributoEstadualInformado == null) {
            tributoEstadualInformado = new TributoEstadualInformado();
        }
        return tributoEstadualInformado;
    }

    /**
     * @param tributoEstadualInformado the tributoEstadualInformado to set
     */
    public void setTributoEstadualInformado(TributoEstadualInformado tributoEstadualInformado) {
        this.tributoEstadualInformado = tributoEstadualInformado;
    }

    /**
     * @return the tributoFederalConfirmado
     */
    public TributoFederalConfirmado getTributoFederalConfirmado() {
        return tributoFederalConfirmado;
    }

    /**
     * @param tributoFederalConfirmado the tributoFederalConfirmado to set
     */
    public void setTributoFederalConfirmado(TributoFederalConfirmado tributoFederalConfirmado) {
        this.tributoFederalConfirmado = tributoFederalConfirmado;
    }

    /**
     * @return the tributoEstadualConfirmado
     */
    public TributoEstadualConfirmado getTributoEstadualConfirmado() {
        return tributoEstadualConfirmado;
    }

    /**
     * @param tributoEstadualConfirmado the tributoEstadualConfirmado to set
     */
    public void setTributoEstadualConfirmado(TributoEstadualConfirmado tributoEstadualConfirmado) {
        this.tributoEstadualConfirmado = tributoEstadualConfirmado;
    }

    /**
     * @return the dominioRegrasInformado
     */
    public DominioRegras getDominioRegrasInformado() {
        return dominioRegrasInformado;
    }

    /**
     * @param dominioRegrasInformado the dominioRegrasInformado to set
     */
    public void setDominioRegrasInformado(DominioRegras dominioRegrasInformado) {
        this.dominioRegrasInformado = dominioRegrasInformado;
    }

    /**
     * @return the dominioRegrasConfirmado
     */
    public DominioRegras getDominioRegrasConfirmado() {
        return dominioRegrasConfirmado;
    }

    /**
     * @param dominioRegrasConfirmado the dominioRegrasConfirmado to set
     */
    public void setDominioRegrasConfirmado(DominioRegras dominioRegrasConfirmado) {
        this.dominioRegrasConfirmado = dominioRegrasConfirmado;
    }

    /**
     * @return the dominioRegrasInformadoBotaoDireito
     */
    public DominioRegras getDominioRegrasInformadoBotaoDireito() {
        return dominioRegrasInformadoBotaoDireito;
    }

    /**
     * @param dominioRegrasInformadoBotaoDireito the
     * dominioRegrasInformadoBotaoDireito to set
     */
    public void setDominioRegrasInformadoBotaoDireito(DominioRegras dominioRegrasInformadoBotaoDireito) {
        this.dominioRegrasInformadoBotaoDireito = dominioRegrasInformadoBotaoDireito;
    }

    /**
     * @return the isEdited
     */
    public boolean isIsEdited() {
        return isEdited;
    }

    /**
     * @param isEdited the isEdited to set
     */
    public void setIsEdited(boolean isEdited) {
        this.isEdited = isEdited;
    }

    /**
     * @return the isCheck
     */
    public boolean isIsCheck() {
        return isCheck;
    }

    /**
     * @param isCheck the isCheck to set
     */
    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    /**
     * @return the countProdutos
     */
    public CountProdutos getCountProdutos() {
        return countProdutos;
    }

    /**
     * @param countProdutos the countProdutos to set
     */
    public void setCountProdutos(CountProdutos countProdutos) {
        this.countProdutos = countProdutos;
    }

}
