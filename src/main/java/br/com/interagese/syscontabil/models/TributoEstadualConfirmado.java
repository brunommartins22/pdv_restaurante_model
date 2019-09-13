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
    @Column(length = 3)
    private String cstIcmsSaidaConfirmado;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaConfirmado;

    //********************************* get && setts ***************************
    /**
     * @return the cstIcmsEntradaConfirmado
     */
    public String getCstIcmsEntradaConfirmado() {
        if (cstIcmsEntradaConfirmado == null) {
            cstIcmsEntradaConfirmado = "";
        }
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
        if (aliquotaIcmsEntradaConfirmado == null) {
            aliquotaIcmsEntradaConfirmado = 0.0;
        }
        return aliquotaIcmsEntradaConfirmado;
    }

    /**
     * @param aliquotaIcmsEntradaConfirmado the aliquotaIcmsEntradaConfirmado to
     * set
     */
    public void setAliquotaIcmsEntradaConfirmado(Double aliquotaIcmsEntradaConfirmado) {
        this.aliquotaIcmsEntradaConfirmado = aliquotaIcmsEntradaConfirmado;
    }

    /**
     * @return the cstIcmsSaidaConfirmado
     */
    public String getCstIcmsSaidaConfirmado() {
        if (cstIcmsSaidaConfirmado == null) {
            cstIcmsSaidaConfirmado = "";
        }
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
        if (aliquotaIcmsSaidaConfirmado == null) {
            aliquotaIcmsSaidaConfirmado = 0.0;
        }
        return aliquotaIcmsSaidaConfirmado;
    }

    /**
     * @param aliquotaIcmsSaidaConfirmado the aliquotaIcmsSaidaConfirmado to set
     */
    public void setAliquotaIcmsSaidaConfirmado(Double aliquotaIcmsSaidaConfirmado) {
        this.aliquotaIcmsSaidaConfirmado = aliquotaIcmsSaidaConfirmado;
    }

}
