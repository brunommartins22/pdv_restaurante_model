/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.dto;

import javax.persistence.Column;

/**
 *
 * @author Bruno Martins
 */
public class TributoEstadualDto {

    @Column(length = 12)
    private String cstIcmsEntradaPadrao;
    @Column(length = 12)
    private Double aliquotaIcmsEntradaPadrao;
    @Column(length = 12)
    private Double aliquotaIcmsEntradaSTPadrao;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntradaPadrao;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntradaSTPadrao;
    @Column(length = 12)
    private String cstIcmsSaidaPadrao;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaPadrao;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaSTPadrao;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsSaidaPadrao;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsSaidaSTPadrao;

    /**
     * @return the cstIcmsEntradaPadrao
     */
    public String getCstIcmsEntradaPadrao() {
        if (cstIcmsEntradaPadrao == null) {
            cstIcmsEntradaPadrao = "";
        }
        return cstIcmsEntradaPadrao;
    }

    /**
     * @param cstIcmsEntradaPadrao the cstIcmsEntradaPadrao to set
     */
    public void setCstIcmsEntradaPadrao(String cstIcmsEntradaPadrao) {
        this.cstIcmsEntradaPadrao = cstIcmsEntradaPadrao;
    }

    /**
     * @return the aliquotaIcmsEntradaPadrao
     */
    public Double getAliquotaIcmsEntradaPadrao() {
        if (aliquotaIcmsEntradaPadrao == null) {
            aliquotaIcmsEntradaPadrao = 0.0;
        }
        return aliquotaIcmsEntradaPadrao;
    }

    /**
     * @param aliquotaIcmsEntradaPadrao the aliquotaIcmsEntradaPadrao to set
     */
    public void setAliquotaIcmsEntradaPadrao(Double aliquotaIcmsEntradaPadrao) {
        this.aliquotaIcmsEntradaPadrao = aliquotaIcmsEntradaPadrao;
    }

    /**
     * @return the aliquotaIcmsEntradaSTPadrao
     */
    public Double getAliquotaIcmsEntradaSTPadrao() {
        if (aliquotaIcmsEntradaSTPadrao == null) {
            aliquotaIcmsEntradaSTPadrao = 0.0;
        }
        return aliquotaIcmsEntradaSTPadrao;
    }

    /**
     * @param aliquotaIcmsEntradaSTPadrao the aliquotaIcmsEntradaSTPadrao to set
     */
    public void setAliquotaIcmsEntradaSTPadrao(Double aliquotaIcmsEntradaSTPadrao) {
        this.aliquotaIcmsEntradaSTPadrao = aliquotaIcmsEntradaSTPadrao;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntradaPadrao
     */
    public Double getReducaoBaseCalculoIcmsEntradaPadrao() {
        if (reducaoBaseCalculoIcmsEntradaPadrao == null) {
            reducaoBaseCalculoIcmsEntradaPadrao = 0.0;
        }
        return reducaoBaseCalculoIcmsEntradaPadrao;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntradaPadrao the
     * reducaoBaseCalculoIcmsEntradaPadrao to set
     */
    public void setReducaoBaseCalculoIcmsEntradaPadrao(Double reducaoBaseCalculoIcmsEntradaPadrao) {
        this.reducaoBaseCalculoIcmsEntradaPadrao = reducaoBaseCalculoIcmsEntradaPadrao;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntradaSTPadrao
     */
    public Double getReducaoBaseCalculoIcmsEntradaSTPadrao() {
        if (reducaoBaseCalculoIcmsEntradaSTPadrao == null) {
            reducaoBaseCalculoIcmsEntradaSTPadrao = 0.0;
        }
        return reducaoBaseCalculoIcmsEntradaSTPadrao;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntradaSTPadrao the
     * reducaoBaseCalculoIcmsEntradaSTPadrao to set
     */
    public void setReducaoBaseCalculoIcmsEntradaSTPadrao(Double reducaoBaseCalculoIcmsEntradaSTPadrao) {
        this.reducaoBaseCalculoIcmsEntradaSTPadrao = reducaoBaseCalculoIcmsEntradaSTPadrao;
    }

    /**
     * @return the cstIcmsSaidaPadrao
     */
    public String getCstIcmsSaidaPadrao() {
        if (cstIcmsSaidaPadrao == null) {
            cstIcmsSaidaPadrao = "";
        }
        return cstIcmsSaidaPadrao;
    }

    /**
     * @param cstIcmsSaidaPadrao the cstIcmsSaidaPadrao to set
     */
    public void setCstIcmsSaidaPadrao(String cstIcmsSaidaPadrao) {
        this.cstIcmsSaidaPadrao = cstIcmsSaidaPadrao;
    }

    /**
     * @return the aliquotaIcmsSaidaPadrao
     */
    public Double getAliquotaIcmsSaidaPadrao() {
        if (aliquotaIcmsSaidaPadrao == null) {
            aliquotaIcmsSaidaPadrao = 0.0;
        }
        return aliquotaIcmsSaidaPadrao;
    }

    /**
     * @param aliquotaIcmsSaidaPadrao the aliquotaIcmsSaidaPadrao to set
     */
    public void setAliquotaIcmsSaidaPadrao(Double aliquotaIcmsSaidaPadrao) {
        this.aliquotaIcmsSaidaPadrao = aliquotaIcmsSaidaPadrao;
    }

    /**
     * @return the aliquotaIcmsSaidaSTPadrao
     */
    public Double getAliquotaIcmsSaidaSTPadrao() {
        if (aliquotaIcmsSaidaSTPadrao == null) {
            aliquotaIcmsSaidaSTPadrao = 0.0;
        }
        return aliquotaIcmsSaidaSTPadrao;
    }

    /**
     * @param aliquotaIcmsSaidaSTPadrao the aliquotaIcmsSaidaSTPadrao to set
     */
    public void setAliquotaIcmsSaidaSTPadrao(Double aliquotaIcmsSaidaSTPadrao) {
        this.aliquotaIcmsSaidaSTPadrao = aliquotaIcmsSaidaSTPadrao;
    }

    /**
     * @return the reducaoBaseCalculoIcmsSaidaPadrao
     */
    public Double getReducaoBaseCalculoIcmsSaidaPadrao() {
        if (reducaoBaseCalculoIcmsSaidaPadrao == null) {
            reducaoBaseCalculoIcmsSaidaPadrao = 0.0;
        }
        return reducaoBaseCalculoIcmsSaidaPadrao;
    }

    /**
     * @param reducaoBaseCalculoIcmsSaidaPadrao the
     * reducaoBaseCalculoIcmsSaidaPadrao to set
     */
    public void setReducaoBaseCalculoIcmsSaidaPadrao(Double reducaoBaseCalculoIcmsSaidaPadrao) {
        this.reducaoBaseCalculoIcmsSaidaPadrao = reducaoBaseCalculoIcmsSaidaPadrao;
    }

    /**
     * @return the reducaoBaseCalculoIcmsSaidaSTPadrao
     */
    public Double getReducaoBaseCalculoIcmsSaidaSTPadrao() {
        if (reducaoBaseCalculoIcmsSaidaSTPadrao == null) {
            reducaoBaseCalculoIcmsSaidaSTPadrao = 0.0;
        }
        return reducaoBaseCalculoIcmsSaidaSTPadrao;
    }

    /**
     * @param reducaoBaseCalculoIcmsSaidaSTPadrao the
     * reducaoBaseCalculoIcmsSaidaSTPadrao to set
     */
    public void setReducaoBaseCalculoIcmsSaidaSTPadrao(Double reducaoBaseCalculoIcmsSaidaSTPadrao) {
        this.reducaoBaseCalculoIcmsSaidaSTPadrao = reducaoBaseCalculoIcmsSaidaSTPadrao;
    }
}
