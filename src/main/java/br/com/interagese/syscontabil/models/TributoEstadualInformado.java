/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

//update syscontabil.arquivos_processar set status_arquivo='PENDENTE';
//delete from syscontabil.produto_cenario;
//delete from syscontabil.produto_cliente;
/**
 *
 * @author Bruno Martins
 */
@Embeddable
public class TributoEstadualInformado implements Serializable {

    @Column(length = 3)
    private String cstIcmsEntradaInformado;
    @Column(length = 12)
    private Double aliquotaIcmsEntradaInformado;
    @Column(length = 3)
    private String cstIcmsSaidaInformado;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaInformado;

    //********************************* get && setts ***************************
    /**
     * @return the cstIcmsEntradaInformado
     */
    public String getCstIcmsEntradaInformado() {
        if (cstIcmsEntradaInformado == null) {
            cstIcmsEntradaInformado = "";
        }
        return cstIcmsEntradaInformado;
    }

    /**
     * @param cstIcmsEntradaInformado the cstIcmsEntradaInformado to set
     */
    public void setCstIcmsEntradaInformado(String cstIcmsEntradaInformado) {
        this.cstIcmsEntradaInformado = cstIcmsEntradaInformado;
    }

    /**
     * @return the aliquotaIcmsEntradaInformado
     */
    public Double getAliquotaIcmsEntradaInformado() {
        if (aliquotaIcmsEntradaInformado == null) {
            aliquotaIcmsEntradaInformado = 0.0;
        }
        return aliquotaIcmsEntradaInformado;
    }

    /**
     * @param aliquotaIcmsEntradaInformado the aliquotaIcmsEntradaInformado to
     * set
     */
    public void setAliquotaIcmsEntradaInformado(Double aliquotaIcmsEntradaInformado) {
        this.aliquotaIcmsEntradaInformado = aliquotaIcmsEntradaInformado;
    }

    /**
     * @return the cstIcmsSaidaInformado
     */
    public String getCstIcmsSaidaInformado() {
        if (cstIcmsSaidaInformado == null) {
            cstIcmsSaidaInformado = "";
        }
        return cstIcmsSaidaInformado;
    }

    /**
     * @param cstIcmsSaidaInformado the cstIcmsSaidaInformado to set
     */
    public void setCstIcmsSaidaInformado(String cstIcmsSaidaInformado) {
        this.cstIcmsSaidaInformado = cstIcmsSaidaInformado;
    }

    /**
     * @return the aliquotaIcmsSaidaInformado
     */
    public Double getAliquotaIcmsSaidaInformado() {
        if (aliquotaIcmsSaidaInformado == null) {
            aliquotaIcmsSaidaInformado = 0.0;
        }
        return aliquotaIcmsSaidaInformado;
    }

    /**
     * @param aliquotaIcmsSaidaInformado the aliquotaIcmsSaidaInformado to set
     */
    public void setAliquotaIcmsSaidaInformado(Double aliquotaIcmsSaidaInformado) {
        this.aliquotaIcmsSaidaInformado = aliquotaIcmsSaidaInformado;
    }

}
