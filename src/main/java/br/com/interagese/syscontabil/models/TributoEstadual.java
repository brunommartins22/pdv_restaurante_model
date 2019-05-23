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
public class TributoEstadual implements Serializable {

    @Column(length = 12)
    private String cstIcmsEntrada;
    @Column(length = 12)
    private Double aliquotaIcmsEntrada;
    @Column(length = 12)
    private Double aliquotaIcmsEntradaST;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntrada;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntradaST;
    @Column(length = 12)
    private String cstIcmsSaida;
    @Column(length = 12)
    private Double aliquotaIcmsSaida;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaST;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsSaida;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsSaidaST;

    /**
     * @return the cstIcmsEntrada
     */
    public String getCstIcmsEntrada() {
        return cstIcmsEntrada;
    }

    /**
     * @param cstIcmsEntrada the cstIcmsEntrada to set
     */
    public void setCstIcmsEntrada(String cstIcmsEntrada) {
        this.cstIcmsEntrada = cstIcmsEntrada;
    }

    /**
     * @return the aliquotaIcmsEntrada
     */
    public Double getAliquotaIcmsEntrada() {
        return aliquotaIcmsEntrada;
    }

    /**
     * @param aliquotaIcmsEntrada the aliquotaIcmsEntrada to set
     */
    public void setAliquotaIcmsEntrada(Double aliquotaIcmsEntrada) {
        this.aliquotaIcmsEntrada = aliquotaIcmsEntrada;
    }

    /**
     * @return the aliquotaIcmsEntradaST
     */
    public Double getAliquotaIcmsEntradaST() {
        return aliquotaIcmsEntradaST;
    }

    /**
     * @param aliquotaIcmsEntradaST the aliquotaIcmsEntradaST to set
     */
    public void setAliquotaIcmsEntradaST(Double aliquotaIcmsEntradaST) {
        this.aliquotaIcmsEntradaST = aliquotaIcmsEntradaST;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntrada
     */
    public Double getReducaoBaseCalculoIcmsEntrada() {
        return reducaoBaseCalculoIcmsEntrada;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntrada the reducaoBaseCalculoIcmsEntrada to
     * set
     */
    public void setReducaoBaseCalculoIcmsEntrada(Double reducaoBaseCalculoIcmsEntrada) {
        this.reducaoBaseCalculoIcmsEntrada = reducaoBaseCalculoIcmsEntrada;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntradaST
     */
    public Double getReducaoBaseCalculoIcmsEntradaST() {
        return reducaoBaseCalculoIcmsEntradaST;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntradaST the
     * reducaoBaseCalculoIcmsEntradaST to set
     */
    public void setReducaoBaseCalculoIcmsEntradaST(Double reducaoBaseCalculoIcmsEntradaST) {
        this.reducaoBaseCalculoIcmsEntradaST = reducaoBaseCalculoIcmsEntradaST;
    }

    /**
     * @return the cstIcmsSaida
     */
    public String getCstIcmsSaida() {
        return cstIcmsSaida;
    }

    /**
     * @param cstIcmsSaida the cstIcmsSaida to set
     */
    public void setCstIcmsSaida(String cstIcmsSaida) {
        this.cstIcmsSaida = cstIcmsSaida;
    }

    /**
     * @return the aliquotaIcmsSaida
     */
    public Double getAliquotaIcmsSaida() {
        return aliquotaIcmsSaida;
    }

    /**
     * @param aliquotaIcmsSaida the aliquotaIcmsSaida to set
     */
    public void setAliquotaIcmsSaida(Double aliquotaIcmsSaida) {
        this.aliquotaIcmsSaida = aliquotaIcmsSaida;
    }

    /**
     * @return the aliquotaIcmsSaidaST
     */
    public Double getAliquotaIcmsSaidaST() {
        return aliquotaIcmsSaidaST;
    }

    /**
     * @param aliquotaIcmsSaidaST the aliquotaIcmsSaidaST to set
     */
    public void setAliquotaIcmsSaidaST(Double aliquotaIcmsSaidaST) {
        this.aliquotaIcmsSaidaST = aliquotaIcmsSaidaST;
    }

    /**
     * @return the reducaoBaseCalculoIcmsSaida
     */
    public Double getReducaoBaseCalculoIcmsSaida() {
        return reducaoBaseCalculoIcmsSaida;
    }

    /**
     * @param reducaoBaseCalculoIcmsSaida the reducaoBaseCalculoIcmsSaida to set
     */
    public void setReducaoBaseCalculoIcmsSaida(Double reducaoBaseCalculoIcmsSaida) {
        this.reducaoBaseCalculoIcmsSaida = reducaoBaseCalculoIcmsSaida;
    }

    /**
     * @return the reducaoBaseCalculoIcmsSaidaST
     */
    public Double getReducaoBaseCalculoIcmsSaidaST() {
        return reducaoBaseCalculoIcmsSaidaST;
    }

    /**
     * @param reducaoBaseCalculoIcmsSaidaST the reducaoBaseCalculoIcmsSaidaST to
     * set
     */
    public void setReducaoBaseCalculoIcmsSaidaST(Double reducaoBaseCalculoIcmsSaidaST) {
        this.reducaoBaseCalculoIcmsSaidaST = reducaoBaseCalculoIcmsSaidaST;
    }
}
