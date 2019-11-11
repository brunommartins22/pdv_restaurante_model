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

    @Column(length = 3)
    private String cstIcmsEntradaCliente;
    @Column(length = 12)
    private Double aliquotaIcmsEntradaCliente;
    @Column(length = 3)
    private String cstIcmsSaidaCliente;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaCliente;

    //********************************* get && setts ***************************
    /**
     * @return the cstIcmsEntradaCliente
     */
    public String getCstIcmsEntradaCliente() {
        if (cstIcmsEntradaCliente == null) {
            cstIcmsEntradaCliente = "";
        }
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
        if (aliquotaIcmsEntradaCliente == null) {
            aliquotaIcmsEntradaCliente = 0.0;
        }
        return aliquotaIcmsEntradaCliente;
    }

    /**
     * @param aliquotaIcmsEntradaCliente the aliquotaIcmsEntradaCliente to set
     */
    public void setAliquotaIcmsEntradaCliente(Double aliquotaIcmsEntradaCliente) {
        this.aliquotaIcmsEntradaCliente = aliquotaIcmsEntradaCliente;
    }

    /**
     * @return the cstIcmsSaidaCliente
     */
    public String getCstIcmsSaidaCliente() {
        if (cstIcmsSaidaCliente == null) {
            cstIcmsSaidaCliente = "";
        }
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
        if (aliquotaIcmsSaidaCliente == null) {
            aliquotaIcmsSaidaCliente = 0.0;
        }
        return aliquotaIcmsSaidaCliente;
    }

    /**
     * @param aliquotaIcmsSaidaCliente the aliquotaIcmsSaidaCliente to set
     */
    public void setAliquotaIcmsSaidaCliente(Double aliquotaIcmsSaidaCliente) {
        this.aliquotaIcmsSaidaCliente = aliquotaIcmsSaidaCliente;
    }

}
