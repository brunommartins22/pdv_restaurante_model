/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

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

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "produtos_service")
public class ProdutosService implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_produtos_service")
    @SequenceGenerator(name = "gen_produtos_service", sequenceName = "seq_produtos_service")
    private Long id;
    @Column(nullable = false)
    @ManyToOne(cascade = CascadeType.MERGE)
    private Cliente cliente;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Cenario cenario;
    @Column(length = 120, nullable = false)
    private String codigoProduto;
    @Column(length = 14)
    private Long ean;
    @Column(length = 255)
    private String nomeProduto;
    @Column(length = 8)
    private String ncm;
    @Column(length = 7)
    private String cest;
    @Embedded
    private TributoEstadualCliente tributoEstadualCliente;
    @Embedded
    private TributoFederalCliente tributoFederalCliente;

    //************************ Estrutura Arquivo *******************************
    /*
     cliente|cenario|codigoroduto|ean|nomeproduto|ncm|cest|cstIcmsSaida|alqIcmsSaida|cstPisSaida|alqPisSaida|cstCofinsSaida|alqCofinsSaida|cstIpiSaida|alqIpiSaida 
     */
    //************************ Equals && Hashcode ******************************
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final ProdutosService other = (ProdutosService) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    //************************* get && setts ***********************************
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
     * @return the ncm
     */
    public String getNcm() {
        return ncm;
    }

    /**
     * @param ncm the ncm to set
     */
    public void setNcm(String ncm) {
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

}
