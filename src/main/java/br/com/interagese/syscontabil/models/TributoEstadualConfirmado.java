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
public class TributoEstadualConfirmado implements Serializable {

    @Column(length = 3)
    private String cstIcmsEntradaConfirmado;
    @Column(length = 12)
    private Double aliquotaIcmsEntradaConfirmado;
    @Column(length = 12)
    private Double aliquotaIcmsEntradaSTConfirmado;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntradaConfirmado;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntradaSTConfirmado;
    @Column(length = 3)
    private String cstIcmsSaidaConfirmado;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaConfirmado;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaSTConfirmado;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsSaidaConfirmado;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsSaidaSTConfirmado;
    
    //********************************* get && setts ***************************

    /**
     * @return the cstIcmsEntradaConfirmado
     */
    public String getCstIcmsEntradaConfirmado() {
        return cstIcmsEntradaConfirmado;
    }

    /**
     * @param cstIcmsEntradaConfirmado the cstIcmsEntradaConfirmado to set
     */
    public void setCstIcmsEntradaConfirmado(String cstIcmsEntradaConfirmado) {
        this.cstIcmsEntradaConfirmado = cstIcmsEntradaConfirmado;
    }

    /**
     * @return the aliquotaIcmsEntradaConfirmado
     */
    public Double getAliquotaIcmsEntradaConfirmado() {
        return aliquotaIcmsEntradaConfirmado;
    }

    /**
     * @param aliquotaIcmsEntradaConfirmado the aliquotaIcmsEntradaConfirmado to set
     */
    public void setAliquotaIcmsEntradaConfirmado(Double aliquotaIcmsEntradaConfirmado) {
        this.aliquotaIcmsEntradaConfirmado = aliquotaIcmsEntradaConfirmado;
    }

    /**
     * @return the aliquotaIcmsEntradaSTConfirmado
     */
    public Double getAliquotaIcmsEntradaSTConfirmado() {
        return aliquotaIcmsEntradaSTConfirmado;
    }

    /**
     * @param aliquotaIcmsEntradaSTConfirmado the aliquotaIcmsEntradaSTConfirmado to set
     */
    public void setAliquotaIcmsEntradaSTConfirmado(Double aliquotaIcmsEntradaSTConfirmado) {
        this.aliquotaIcmsEntradaSTConfirmado = aliquotaIcmsEntradaSTConfirmado;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntradaConfirmado
     */
    public Double getReducaoBaseCalculoIcmsEntradaConfirmado() {
        return reducaoBaseCalculoIcmsEntradaConfirmado;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntradaConfirmado the reducaoBaseCalculoIcmsEntradaConfirmado to set
     */
    public void setReducaoBaseCalculoIcmsEntradaConfirmado(Double reducaoBaseCalculoIcmsEntradaConfirmado) {
        this.reducaoBaseCalculoIcmsEntradaConfirmado = reducaoBaseCalculoIcmsEntradaConfirmado;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntradaSTConfirmado
     */
    public Double getReducaoBaseCalculoIcmsEntradaSTConfirmado() {
        return reducaoBaseCalculoIcmsEntradaSTConfirmado;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntradaSTConfirmado the reducaoBaseCalculoIcmsEntradaSTConfirmado to set
     */
    public void setReducaoBaseCalculoIcmsEntradaSTConfirmado(Double reducaoBaseCalculoIcmsEntradaSTConfirmado) {
        this.reducaoBaseCalculoIcmsEntradaSTConfirmado = reducaoBaseCalculoIcmsEntradaSTConfirmado;
    }

    /**
     * @return the cstIcmsSaidaConfirmado
     */
    public String getCstIcmsSaidaConfirmado() {
        return cstIcmsSaidaConfirmado;
    }

    /**
     * @param cstIcmsSaidaConfirmado the cstIcmsSaidaConfirmado to set
     */
    public void setCstIcmsSaidaConfirmado(String cstIcmsSaidaConfirmado) {
        this.cstIcmsSaidaConfirmado = cstIcmsSaidaConfirmado;
    }

    /**
     * @return the aliquotaIcmsSaidaConfirmado
     */
    public Double getAliquotaIcmsSaidaConfirmado() {
        return aliquotaIcmsSaidaConfirmado;
    }

    /**
     * @param aliquotaIcmsSaidaConfirmado the aliquotaIcmsSaidaConfirmado to set
     */
    public void setAliquotaIcmsSaidaConfirmado(Double aliquotaIcmsSaidaConfirmado) {
        this.aliquotaIcmsSaidaConfirmado = aliquotaIcmsSaidaConfirmado;
    }

    /**
     * @return the aliquotaIcmsSaidaSTConfirmado
     */
    public Double getAliquotaIcmsSaidaSTConfirmado() {
        return aliquotaIcmsSaidaSTConfirmado;
    }

    /**
     * @param aliquotaIcmsSaidaSTConfirmado the aliquotaIcmsSaidaSTConfirmado to set
     */
    public void setAliquotaIcmsSaidaSTConfirmado(Double aliquotaIcmsSaidaSTConfirmado) {
        this.aliquotaIcmsSaidaSTConfirmado = aliquotaIcmsSaidaSTConfirmado;
    }

    /**
     * @return the reducaoBaseCalculoIcmsSaidaConfirmado
     */
    public Double getReducaoBaseCalculoIcmsSaidaConfirmado() {
        return reducaoBaseCalculoIcmsSaidaConfirmado;
    }

    /**
     * @param reducaoBaseCalculoIcmsSaidaConfirmado the reducaoBaseCalculoIcmsSaidaConfirmado to set
     */
    public void setReducaoBaseCalculoIcmsSaidaConfirmado(Double reducaoBaseCalculoIcmsSaidaConfirmado) {
        this.reducaoBaseCalculoIcmsSaidaConfirmado = reducaoBaseCalculoIcmsSaidaConfirmado;
    }

    /**
     * @return the reducaoBaseCalculoIcmsSaidaSTConfirmado
     */
    public Double getReducaoBaseCalculoIcmsSaidaSTConfirmado() {
        return reducaoBaseCalculoIcmsSaidaSTConfirmado;
    }

    /**
     * @param reducaoBaseCalculoIcmsSaidaSTConfirmado the reducaoBaseCalculoIcmsSaidaSTConfirmado to set
     */
    public void setReducaoBaseCalculoIcmsSaidaSTConfirmado(Double reducaoBaseCalculoIcmsSaidaSTConfirmado) {
        this.reducaoBaseCalculoIcmsSaidaSTConfirmado = reducaoBaseCalculoIcmsSaidaSTConfirmado;
    }

   
}
