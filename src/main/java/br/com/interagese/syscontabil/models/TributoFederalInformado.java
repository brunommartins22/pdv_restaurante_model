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
public class TributoFederalInformado implements Serializable {

    @Column(length = 3)
    private String cstPisEntradaInformado;
    @Column(length = 12)
    private Double aliquotaPisEntradaInformado;
    @Column(length = 3)
    private String cstPisSaidaInformado;
    @Column(length = 12)
    private Double aliquotaPisSaidaInformado;
    @Column(length = 3)
    private String cstCofinsEntradaInformado;
    @Column(length = 12)
    private Double aliquotaCofinsEntradaInformado;
    @Column(length = 3)
    private String cstCofinsSaidaInformado;
    @Column(length = 12)
    private Double aliquotaCofinsSaidaInformado;
    @Column(length = 3)
    private String cstIpiEntradaInformado;
    @Column(length = 12)
    private Double aliquotaIpiEntradaInformado;
    @Column(length = 3)
    private String cstIpiSaidaInformado;
    @Column(length = 12)
    private Double aliquotaIpiSaidaInformado;
    
    //********************************* get && setts ***************************

    /**
     * @return the cstPisEntradaInformado
     */
    public String getCstPisEntradaInformado() {
        return cstPisEntradaInformado;
    }

    /**
     * @param cstPisEntradaInformado the cstPisEntradaInformado to set
     */
    public void setCstPisEntradaInformado(String cstPisEntradaInformado) {
        this.cstPisEntradaInformado = cstPisEntradaInformado;
    }

    /**
     * @return the aliquotaPisEntradaInformado
     */
    public Double getAliquotaPisEntradaInformado() {
        return aliquotaPisEntradaInformado;
    }

    /**
     * @param aliquotaPisEntradaInformado the aliquotaPisEntradaInformado to set
     */
    public void setAliquotaPisEntradaInformado(Double aliquotaPisEntradaInformado) {
        this.aliquotaPisEntradaInformado = aliquotaPisEntradaInformado;
    }

    /**
     * @return the cstPisSaidaInformado
     */
    public String getCstPisSaidaInformado() {
        return cstPisSaidaInformado;
    }

    /**
     * @param cstPisSaidaInformado the cstPisSaidaInformado to set
     */
    public void setCstPisSaidaInformado(String cstPisSaidaInformado) {
        this.cstPisSaidaInformado = cstPisSaidaInformado;
    }

    /**
     * @return the aliquotaPisSaidaInformado
     */
    public Double getAliquotaPisSaidaInformado() {
        return aliquotaPisSaidaInformado;
    }

    /**
     * @param aliquotaPisSaidaInformado the aliquotaPisSaidaInformado to set
     */
    public void setAliquotaPisSaidaInformado(Double aliquotaPisSaidaInformado) {
        this.aliquotaPisSaidaInformado = aliquotaPisSaidaInformado;
    }

    /**
     * @return the cstCofinsEntradaInformado
     */
    public String getCstCofinsEntradaInformado() {
        return cstCofinsEntradaInformado;
    }

    /**
     * @param cstCofinsEntradaInformado the cstCofinsEntradaInformado to set
     */
    public void setCstCofinsEntradaInformado(String cstCofinsEntradaInformado) {
        this.cstCofinsEntradaInformado = cstCofinsEntradaInformado;
    }

    /**
     * @return the aliquotaCofinsEntradaInformado
     */
    public Double getAliquotaCofinsEntradaInformado() {
        return aliquotaCofinsEntradaInformado;
    }

    /**
     * @param aliquotaCofinsEntradaInformado the aliquotaCofinsEntradaInformado to set
     */
    public void setAliquotaCofinsEntradaInformado(Double aliquotaCofinsEntradaInformado) {
        this.aliquotaCofinsEntradaInformado = aliquotaCofinsEntradaInformado;
    }

    /**
     * @return the cstCofinsSaidaInformado
     */
    public String getCstCofinsSaidaInformado() {
        return cstCofinsSaidaInformado;
    }

    /**
     * @param cstCofinsSaidaInformado the cstCofinsSaidaInformado to set
     */
    public void setCstCofinsSaidaInformado(String cstCofinsSaidaInformado) {
        this.cstCofinsSaidaInformado = cstCofinsSaidaInformado;
    }

    /**
     * @return the aliquotaCofinsSaidaInformado
     */
    public Double getAliquotaCofinsSaidaInformado() {
        return aliquotaCofinsSaidaInformado;
    }

    /**
     * @param aliquotaCofinsSaidaInformado the aliquotaCofinsSaidaInformado to set
     */
    public void setAliquotaCofinsSaidaInformado(Double aliquotaCofinsSaidaInformado) {
        this.aliquotaCofinsSaidaInformado = aliquotaCofinsSaidaInformado;
    }

    /**
     * @return the cstIpiEntradaInformado
     */
    public String getCstIpiEntradaInformado() {
        return cstIpiEntradaInformado;
    }

    /**
     * @param cstIpiEntradaInformado the cstIpiEntradaInformado to set
     */
    public void setCstIpiEntradaInformado(String cstIpiEntradaInformado) {
        this.cstIpiEntradaInformado = cstIpiEntradaInformado;
    }

    /**
     * @return the aliquotaIpiEntradaInformado
     */
    public Double getAliquotaIpiEntradaInformado() {
        return aliquotaIpiEntradaInformado;
    }

    /**
     * @param aliquotaIpiEntradaInformado the aliquotaIpiEntradaInformado to set
     */
    public void setAliquotaIpiEntradaInformado(Double aliquotaIpiEntradaInformado) {
        this.aliquotaIpiEntradaInformado = aliquotaIpiEntradaInformado;
    }

    /**
     * @return the cstIpiSaidaInformado
     */
    public String getCstIpiSaidaInformado() {
        return cstIpiSaidaInformado;
    }

    /**
     * @param cstIpiSaidaInformado the cstIpiSaidaInformado to set
     */
    public void setCstIpiSaidaInformado(String cstIpiSaidaInformado) {
        this.cstIpiSaidaInformado = cstIpiSaidaInformado;
    }

    /**
     * @return the aliquotaIpiSaidaInformado
     */
    public Double getAliquotaIpiSaidaInformado() {
        return aliquotaIpiSaidaInformado;
    }

    /**
     * @param aliquotaIpiSaidaInformado the aliquotaIpiSaidaInformado to set
     */
    public void setAliquotaIpiSaidaInformado(Double aliquotaIpiSaidaInformado) {
        this.aliquotaIpiSaidaInformado = aliquotaIpiSaidaInformado;
    }

   
}
