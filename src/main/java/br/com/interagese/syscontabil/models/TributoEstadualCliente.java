/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Bruno Martins
 */
@Embeddable
public class TributoEstadualCliente implements Serializable {

    @Column(length = 12)
    private String cstIcmsEntradaCliente;
    @Column(length = 12)
    private Double aliquotaIcmsEntradaCliente;
    @Column(length = 12)
    private Double aliquotaIcmsEntradaSTCliente;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntradaCliente;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntradaSTCliente;
    @Column(length = 12)
    private String cstIcmsSaidaCliente;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaCliente;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaSTCliente;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsSaidaCliente;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsSaidaSTCliente;
    
    //********************************* get && setts ***************************

    /**
     * @return the cstIcmsEntradaCliente
     */
    public String getCstIcmsEntradaCliente() {
        return cstIcmsEntradaCliente;
    }

    /**
     * @param cstIcmsEntradaCliente the cstIcmsEntradaCliente to set
     */
    public void setCstIcmsEntradaCliente(String cstIcmsEntradaCliente) {
        this.cstIcmsEntradaCliente = cstIcmsEntradaCliente;
    }

    /**
     * @return the aliquotaIcmsEntradaCliente
     */
    public Double getAliquotaIcmsEntradaCliente() {
        return aliquotaIcmsEntradaCliente;
    }

    /**
     * @param aliquotaIcmsEntradaCliente the aliquotaIcmsEntradaCliente to set
     */
    public void setAliquotaIcmsEntradaCliente(Double aliquotaIcmsEntradaCliente) {
        this.aliquotaIcmsEntradaCliente = aliquotaIcmsEntradaCliente;
    }

    /**
     * @return the aliquotaIcmsEntradaSTCliente
     */
    public Double getAliquotaIcmsEntradaSTCliente() {
        return aliquotaIcmsEntradaSTCliente;
    }

    /**
     * @param aliquotaIcmsEntradaSTCliente the aliquotaIcmsEntradaSTCliente to set
     */
    public void setAliquotaIcmsEntradaSTCliente(Double aliquotaIcmsEntradaSTCliente) {
        this.aliquotaIcmsEntradaSTCliente = aliquotaIcmsEntradaSTCliente;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntradaCliente
     */
    public Double getReducaoBaseCalculoIcmsEntradaCliente() {
        return reducaoBaseCalculoIcmsEntradaCliente;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntradaCliente the reducaoBaseCalculoIcmsEntradaCliente to set
     */
    public void setReducaoBaseCalculoIcmsEntradaCliente(Double reducaoBaseCalculoIcmsEntradaCliente) {
        this.reducaoBaseCalculoIcmsEntradaCliente = reducaoBaseCalculoIcmsEntradaCliente;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntradaSTCliente
     */
    public Double getReducaoBaseCalculoIcmsEntradaSTCliente() {
        return reducaoBaseCalculoIcmsEntradaSTCliente;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntradaSTCliente the reducaoBaseCalculoIcmsEntradaSTCliente to set
     */
    public void setReducaoBaseCalculoIcmsEntradaSTCliente(Double reducaoBaseCalculoIcmsEntradaSTCliente) {
        this.reducaoBaseCalculoIcmsEntradaSTCliente = reducaoBaseCalculoIcmsEntradaSTCliente;
    }

    /**
     * @return the cstIcmsSaidaCliente
     */
    public String getCstIcmsSaidaCliente() {
        return cstIcmsSaidaCliente;
    }

    /**
     * @param cstIcmsSaidaCliente the cstIcmsSaidaCliente to set
     */
    public void setCstIcmsSaidaCliente(String cstIcmsSaidaCliente) {
        this.cstIcmsSaidaCliente = cstIcmsSaidaCliente;
    }

    /**
     * @return the aliquotaIcmsSaidaCliente
     */
    public Double getAliquotaIcmsSaidaCliente() {
        return aliquotaIcmsSaidaCliente;
    }

    /**
     * @param aliquotaIcmsSaidaCliente the aliquotaIcmsSaidaCliente to set
     */
    public void setAliquotaIcmsSaidaCliente(Double aliquotaIcmsSaidaCliente) {
        this.aliquotaIcmsSaidaCliente = aliquotaIcmsSaidaCliente;
    }

    /**
     * @return the aliquotaIcmsSaidaSTCliente
     */
    public Double getAliquotaIcmsSaidaSTCliente() {
        return aliquotaIcmsSaidaSTCliente;
    }

    /**
     * @param aliquotaIcmsSaidaSTCliente the aliquotaIcmsSaidaSTCliente to set
     */
    public void setAliquotaIcmsSaidaSTCliente(Double aliquotaIcmsSaidaSTCliente) {
        this.aliquotaIcmsSaidaSTCliente = aliquotaIcmsSaidaSTCliente;
    }

    /**
     * @return the reducaoBaseCalculoIcmsSaidaCliente
     */
    public Double getReducaoBaseCalculoIcmsSaidaCliente() {
        return reducaoBaseCalculoIcmsSaidaCliente;
    }

    /**
     * @param reducaoBaseCalculoIcmsSaidaCliente the reducaoBaseCalculoIcmsSaidaCliente to set
     */
    public void setReducaoBaseCalculoIcmsSaidaCliente(Double reducaoBaseCalculoIcmsSaidaCliente) {
        this.reducaoBaseCalculoIcmsSaidaCliente = reducaoBaseCalculoIcmsSaidaCliente;
    }

    /**
     * @return the reducaoBaseCalculoIcmsSaidaSTCliente
     */
    public Double getReducaoBaseCalculoIcmsSaidaSTCliente() {
        return reducaoBaseCalculoIcmsSaidaSTCliente;
    }

    /**
     * @param reducaoBaseCalculoIcmsSaidaSTCliente the reducaoBaseCalculoIcmsSaidaSTCliente to set
     */
    public void setReducaoBaseCalculoIcmsSaidaSTCliente(Double reducaoBaseCalculoIcmsSaidaSTCliente) {
        this.reducaoBaseCalculoIcmsSaidaSTCliente = reducaoBaseCalculoIcmsSaidaSTCliente;
    }

   
}
