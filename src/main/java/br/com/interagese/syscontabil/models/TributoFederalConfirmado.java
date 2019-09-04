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
public class TributoFederalConfirmado implements Serializable {

    @Column(length = 3)
    private String cstPisEntradaConfirmado;
    @Column(length = 12)
    private Double aliquotaPisEntradaConfirmado;
    @Column(length = 3)
    private String cstPisSaidaConfirmado;
    @Column(length = 12)
    private Double aliquotaPisSaidaConfirmado;
    @Column(length = 3)
    private String cstCofinsEntradaConfirmado;
    @Column(length = 12)
    private Double aliquotaCofinsEntradaConfirmado;
    @Column(length = 3)
    private String cstCofinsSaidaConfirmado;
    @Column(length = 12)
    private Double aliquotaCofinsSaidaConfirmado;
    @Column(length = 3)
    private String cstIpiEntradaConfirmado;
    @Column(length = 12)
    private Double aliquotaIpiEntradaConfirmado;
    @Column(length = 3)
    private String cstIpiSaidaConfirmado;
    @Column(length = 12)
    private Double aliquotaIpiSaidaConfirmado;
    
    //********************************* get && setts ***************************

    /**
     * @return the cstPisEntradaConfirmado
     */
    public String getCstPisEntradaConfirmado() {
        return cstPisEntradaConfirmado;
    }

    /**
     * @param cstPisEntradaConfirmado the cstPisEntradaConfirmado to set
     */
    public void setCstPisEntradaConfirmado(String cstPisEntradaConfirmado) {
        this.cstPisEntradaConfirmado = cstPisEntradaConfirmado;
    }

    /**
     * @return the aliquotaPisEntradaConfirmado
     */
    public Double getAliquotaPisEntradaConfirmado() {
        return aliquotaPisEntradaConfirmado;
    }

    /**
     * @param aliquotaPisEntradaConfirmado the aliquotaPisEntradaConfirmado to set
     */
    public void setAliquotaPisEntradaConfirmado(Double aliquotaPisEntradaConfirmado) {
        this.aliquotaPisEntradaConfirmado = aliquotaPisEntradaConfirmado;
    }

    /**
     * @return the cstPisSaidaConfirmado
     */
    public String getCstPisSaidaConfirmado() {
        return cstPisSaidaConfirmado;
    }

    /**
     * @param cstPisSaidaConfirmado the cstPisSaidaConfirmado to set
     */
    public void setCstPisSaidaConfirmado(String cstPisSaidaConfirmado) {
        this.cstPisSaidaConfirmado = cstPisSaidaConfirmado;
    }

    /**
     * @return the aliquotaPisSaidaConfirmado
     */
    public Double getAliquotaPisSaidaConfirmado() {
        return aliquotaPisSaidaConfirmado;
    }

    /**
     * @param aliquotaPisSaidaConfirmado the aliquotaPisSaidaConfirmado to set
     */
    public void setAliquotaPisSaidaConfirmado(Double aliquotaPisSaidaConfirmado) {
        this.aliquotaPisSaidaConfirmado = aliquotaPisSaidaConfirmado;
    }

    /**
     * @return the cstCofinsEntradaConfirmado
     */
    public String getCstCofinsEntradaConfirmado() {
        return cstCofinsEntradaConfirmado;
    }

    /**
     * @param cstCofinsEntradaConfirmado the cstCofinsEntradaConfirmado to set
     */
    public void setCstCofinsEntradaConfirmado(String cstCofinsEntradaConfirmado) {
        this.cstCofinsEntradaConfirmado = cstCofinsEntradaConfirmado;
    }

    /**
     * @return the aliquotaCofinsEntradaConfirmado
     */
    public Double getAliquotaCofinsEntradaConfirmado() {
        return aliquotaCofinsEntradaConfirmado;
    }

    /**
     * @param aliquotaCofinsEntradaConfirmado the aliquotaCofinsEntradaConfirmado to set
     */
    public void setAliquotaCofinsEntradaConfirmado(Double aliquotaCofinsEntradaConfirmado) {
        this.aliquotaCofinsEntradaConfirmado = aliquotaCofinsEntradaConfirmado;
    }

    /**
     * @return the cstCofinsSaidaConfirmado
     */
    public String getCstCofinsSaidaConfirmado() {
        return cstCofinsSaidaConfirmado;
    }

    /**
     * @param cstCofinsSaidaConfirmado the cstCofinsSaidaConfirmado to set
     */
    public void setCstCofinsSaidaConfirmado(String cstCofinsSaidaConfirmado) {
        this.cstCofinsSaidaConfirmado = cstCofinsSaidaConfirmado;
    }

    /**
     * @return the aliquotaCofinsSaidaConfirmado
     */
    public Double getAliquotaCofinsSaidaConfirmado() {
        return aliquotaCofinsSaidaConfirmado;
    }

    /**
     * @param aliquotaCofinsSaidaConfirmado the aliquotaCofinsSaidaConfirmado to set
     */
    public void setAliquotaCofinsSaidaConfirmado(Double aliquotaCofinsSaidaConfirmado) {
        this.aliquotaCofinsSaidaConfirmado = aliquotaCofinsSaidaConfirmado;
    }

    /**
     * @return the cstIpiEntradaConfirmado
     */
    public String getCstIpiEntradaConfirmado() {
        return cstIpiEntradaConfirmado;
    }

    /**
     * @param cstIpiEntradaConfirmado the cstIpiEntradaConfirmado to set
     */
    public void setCstIpiEntradaConfirmado(String cstIpiEntradaConfirmado) {
        this.cstIpiEntradaConfirmado = cstIpiEntradaConfirmado;
    }

    /**
     * @return the aliquotaIpiEntradaConfirmado
     */
    public Double getAliquotaIpiEntradaConfirmado() {
        return aliquotaIpiEntradaConfirmado;
    }

    /**
     * @param aliquotaIpiEntradaConfirmado the aliquotaIpiEntradaConfirmado to set
     */
    public void setAliquotaIpiEntradaConfirmado(Double aliquotaIpiEntradaConfirmado) {
        this.aliquotaIpiEntradaConfirmado = aliquotaIpiEntradaConfirmado;
    }

    /**
     * @return the cstIpiSaidaConfirmado
     */
    public String getCstIpiSaidaConfirmado() {
        return cstIpiSaidaConfirmado;
    }

    /**
     * @param cstIpiSaidaConfirmado the cstIpiSaidaConfirmado to set
     */
    public void setCstIpiSaidaConfirmado(String cstIpiSaidaConfirmado) {
        this.cstIpiSaidaConfirmado = cstIpiSaidaConfirmado;
    }

    /**
     * @return the aliquotaIpiSaidaConfirmado
     */
    public Double getAliquotaIpiSaidaConfirmado() {
        return aliquotaIpiSaidaConfirmado;
    }

    /**
     * @param aliquotaIpiSaidaConfirmado the aliquotaIpiSaidaConfirmado to set
     */
    public void setAliquotaIpiSaidaConfirmado(Double aliquotaIpiSaidaConfirmado) {
        this.aliquotaIpiSaidaConfirmado = aliquotaIpiSaidaConfirmado;
    }

   
}
