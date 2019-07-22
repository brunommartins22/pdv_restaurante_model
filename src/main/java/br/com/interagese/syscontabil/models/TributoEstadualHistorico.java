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
public class TributoEstadualHistorico implements Serializable {

    @Column(length = 3)
    private String cstIcmsEntradaHistorico;
    @Column(length = 12)
    private Double aliquotaIcmsEntradaHistorico;
    @Column(length = 12)
    private Double aliquotaIcmsEntradaSTHistorico;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntradaHistorico;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntradaSTHistorico;
    @Column(length = 3)
    private String cstIcmsSaidaHistorico;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaHistorico;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaSTHistorico;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsSaidaHistorico;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsSaidaSTHistorico;
    
    //********************************* get && setts ***************************

    /**
     * @return the cstIcmsEntradaHistorico
     */
    public String getCstIcmsEntradaHistorico() {
        return cstIcmsEntradaHistorico;
    }

    /**
     * @param cstIcmsEntradaHistorico the cstIcmsEntradaHistorico to set
     */
    public void setCstIcmsEntradaHistorico(String cstIcmsEntradaHistorico) {
        this.cstIcmsEntradaHistorico = cstIcmsEntradaHistorico;
    }

    /**
     * @return the aliquotaIcmsEntradaHistorico
     */
    public Double getAliquotaIcmsEntradaHistorico() {
        return aliquotaIcmsEntradaHistorico;
    }

    /**
     * @param aliquotaIcmsEntradaHistorico the aliquotaIcmsEntradaHistorico to set
     */
    public void setAliquotaIcmsEntradaHistorico(Double aliquotaIcmsEntradaHistorico) {
        this.aliquotaIcmsEntradaHistorico = aliquotaIcmsEntradaHistorico;
    }

    /**
     * @return the aliquotaIcmsEntradaSTHistorico
     */
    public Double getAliquotaIcmsEntradaSTHistorico() {
        return aliquotaIcmsEntradaSTHistorico;
    }

    /**
     * @param aliquotaIcmsEntradaSTHistorico the aliquotaIcmsEntradaSTHistorico to set
     */
    public void setAliquotaIcmsEntradaSTHistorico(Double aliquotaIcmsEntradaSTHistorico) {
        this.aliquotaIcmsEntradaSTHistorico = aliquotaIcmsEntradaSTHistorico;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntradaHistorico
     */
    public Double getReducaoBaseCalculoIcmsEntradaHistorico() {
        return reducaoBaseCalculoIcmsEntradaHistorico;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntradaHistorico the reducaoBaseCalculoIcmsEntradaHistorico to set
     */
    public void setReducaoBaseCalculoIcmsEntradaHistorico(Double reducaoBaseCalculoIcmsEntradaHistorico) {
        this.reducaoBaseCalculoIcmsEntradaHistorico = reducaoBaseCalculoIcmsEntradaHistorico;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntradaSTHistorico
     */
    public Double getReducaoBaseCalculoIcmsEntradaSTHistorico() {
        return reducaoBaseCalculoIcmsEntradaSTHistorico;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntradaSTHistorico the reducaoBaseCalculoIcmsEntradaSTHistorico to set
     */
    public void setReducaoBaseCalculoIcmsEntradaSTHistorico(Double reducaoBaseCalculoIcmsEntradaSTHistorico) {
        this.reducaoBaseCalculoIcmsEntradaSTHistorico = reducaoBaseCalculoIcmsEntradaSTHistorico;
    }

    /**
     * @return the cstIcmsSaidaHistorico
     */
    public String getCstIcmsSaidaHistorico() {
        return cstIcmsSaidaHistorico;
    }

    /**
     * @param cstIcmsSaidaHistorico the cstIcmsSaidaHistorico to set
     */
    public void setCstIcmsSaidaHistorico(String cstIcmsSaidaHistorico) {
        this.cstIcmsSaidaHistorico = cstIcmsSaidaHistorico;
    }

    /**
     * @return the aliquotaIcmsSaidaHistorico
     */
    public Double getAliquotaIcmsSaidaHistorico() {
        return aliquotaIcmsSaidaHistorico;
    }

    /**
     * @param aliquotaIcmsSaidaHistorico the aliquotaIcmsSaidaHistorico to set
     */
    public void setAliquotaIcmsSaidaHistorico(Double aliquotaIcmsSaidaHistorico) {
        this.aliquotaIcmsSaidaHistorico = aliquotaIcmsSaidaHistorico;
    }

    /**
     * @return the aliquotaIcmsSaidaSTHistorico
     */
    public Double getAliquotaIcmsSaidaSTHistorico() {
        return aliquotaIcmsSaidaSTHistorico;
    }

    /**
     * @param aliquotaIcmsSaidaSTHistorico the aliquotaIcmsSaidaSTHistorico to set
     */
    public void setAliquotaIcmsSaidaSTHistorico(Double aliquotaIcmsSaidaSTHistorico) {
        this.aliquotaIcmsSaidaSTHistorico = aliquotaIcmsSaidaSTHistorico;
    }

    /**
     * @return the reducaoBaseCalculoIcmsSaidaHistorico
     */
    public Double getReducaoBaseCalculoIcmsSaidaHistorico() {
        return reducaoBaseCalculoIcmsSaidaHistorico;
    }

    /**
     * @param reducaoBaseCalculoIcmsSaidaHistorico the reducaoBaseCalculoIcmsSaidaHistorico to set
     */
    public void setReducaoBaseCalculoIcmsSaidaHistorico(Double reducaoBaseCalculoIcmsSaidaHistorico) {
        this.reducaoBaseCalculoIcmsSaidaHistorico = reducaoBaseCalculoIcmsSaidaHistorico;
    }

    /**
     * @return the reducaoBaseCalculoIcmsSaidaSTHistorico
     */
    public Double getReducaoBaseCalculoIcmsSaidaSTHistorico() {
        return reducaoBaseCalculoIcmsSaidaSTHistorico;
    }

    /**
     * @param reducaoBaseCalculoIcmsSaidaSTHistorico the reducaoBaseCalculoIcmsSaidaSTHistorico to set
     */
    public void setReducaoBaseCalculoIcmsSaidaSTHistorico(Double reducaoBaseCalculoIcmsSaidaSTHistorico) {
        this.reducaoBaseCalculoIcmsSaidaSTHistorico = reducaoBaseCalculoIcmsSaidaSTHistorico;
    }

   
}
