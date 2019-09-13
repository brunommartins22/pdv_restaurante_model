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
public class TributoEstadualPadrao implements Serializable {

    @Column(length = 3)
    private String cstIcmsEntradaPadrao;
    @Column(length = 12)
    private Double aliquotaIcmsEntradaPadrao;
    @Column(length = 3)
    private String cstIcmsSaidaPadrao;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaPadrao;

    //***************************** get && setts *******************************
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

    

}
