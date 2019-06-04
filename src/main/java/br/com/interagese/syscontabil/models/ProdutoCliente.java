/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import br.com.interagese.syscontabil.domains.DominioValidacaoProduto;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
    @Column(nullable = false)
    private Long clienteId;
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
    @Enumerated(EnumType.STRING)
    private DominioValidacaoProduto status;
    @Embedded
    private TributoFederalCliente tributoFederalCliente;
    @Embedded
    private TributoFederal tributoFederal;
    @Embedded
    private TributoEstadualCliente tributoEstadualCliente;
    @Embedded
    private TributoEstadual tributoEstadual;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

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
     * @return the clienteId
     */
    public Long getClienteId() {
        return clienteId;
    }

    /**
     * @param clienteId the clienteId to set
     */
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
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
     * @return the tributoFederal
     */
    public TributoFederal getTributoFederal() {
        return tributoFederal;
    }

    /**
     * @param tributoFederal the tributoFederal to set
     */
    public void setTributoFederal(TributoFederal tributoFederal) {
        this.tributoFederal = tributoFederal;
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
     * @return the tributoEstadual
     */
    public TributoEstadual getTributoEstadual() {
        return tributoEstadual;
    }

    /**
     * @param tributoEstadual the tributoEstadual to set
     */
    public void setTributoEstadual(TributoEstadual tributoEstadual) {
        this.tributoEstadual = tributoEstadual;
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
     * @return the dominioValidacaoProduto
     */
    public DominioValidacaoProduto getstatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setDominioValidacaoProduto(DominioValidacaoProduto status) {
        this.status = status;
    }
   
}
