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
public class TributoFederalCliente implements Serializable {

    @Column(length = 2)
    private String cstPisEntradaCliente;
    @Column(length = 12)
    private Double aliquotaPisEntradaCliente;
    @Column(length = 2)
    private String cstPisSaidaCliente;
    @Column(length = 12)
    private Double aliquotaPisSaidaCliente;
    @Column(length = 2)
    private String cstCofinsEntradaCliente;
    @Column(length = 12)
    private Double aliquotaCofinsEntradaCliente;
    @Column(length = 2)
    private String cstCofinsSaidaCliente;
    @Column(length = 12)
    private Double aliquotaCofinsSaidaCliente;
    @Column(length = 12)
    private String cstIpiEntradaCliente;
    @Column(length = 12)
    private Double aliquotaIpiEntradaCliente;
    @Column(length = 2)
    private String cstIpiSaidaCliente;
    @Column(length = 12)
    private Double aliquotaIpiSaidaCliente;
    
    //********************************* get && setts ***************************

    /**
     * @return the cstPisEntradaCliente
     */
    public String getCstPisEntradaCliente() {
        return cstPisEntradaCliente;
    }

    /**
     * @param cstPisEntradaCliente the cstPisEntradaCliente to set
     */
    public void setCstPisEntradaCliente(String cstPisEntradaCliente) {
        this.cstPisEntradaCliente = cstPisEntradaCliente;
    }

    /**
     * @return the aliquotaPisEntradaCliente
     */
    public Double getAliquotaPisEntradaCliente() {
        return aliquotaPisEntradaCliente;
    }

    /**
     * @param aliquotaPisEntradaCliente the aliquotaPisEntradaCliente to set
     */
    public void setAliquotaPisEntradaCliente(Double aliquotaPisEntradaCliente) {
        this.aliquotaPisEntradaCliente = aliquotaPisEntradaCliente;
    }

    /**
     * @return the cstPisSaidaCliente
     */
    public String getCstPisSaidaCliente() {
        return cstPisSaidaCliente;
    }

    /**
     * @param cstPisSaidaCliente the cstPisSaidaCliente to set
     */
    public void setCstPisSaidaCliente(String cstPisSaidaCliente) {
        this.cstPisSaidaCliente = cstPisSaidaCliente;
    }

    /**
     * @return the aliquotaPisSaidaCliente
     */
    public Double getAliquotaPisSaidaCliente() {
        return aliquotaPisSaidaCliente;
    }

    /**
     * @param aliquotaPisSaidaCliente the aliquotaPisSaidaCliente to set
     */
    public void setAliquotaPisSaidaCliente(Double aliquotaPisSaidaCliente) {
        this.aliquotaPisSaidaCliente = aliquotaPisSaidaCliente;
    }

    /**
     * @return the cstCofinsEntradaCliente
     */
    public String getCstCofinsEntradaCliente() {
        return cstCofinsEntradaCliente;
    }

    /**
     * @param cstCofinsEntradaCliente the cstCofinsEntradaCliente to set
     */
    public void setCstCofinsEntradaCliente(String cstCofinsEntradaCliente) {
        this.cstCofinsEntradaCliente = cstCofinsEntradaCliente;
    }

    /**
     * @return the aliquotaCofinsEntradaCliente
     */
    public Double getAliquotaCofinsEntradaCliente() {
        return aliquotaCofinsEntradaCliente;
    }

    /**
     * @param aliquotaCofinsEntradaCliente the aliquotaCofinsEntradaCliente to set
     */
    public void setAliquotaCofinsEntradaCliente(Double aliquotaCofinsEntradaCliente) {
        this.aliquotaCofinsEntradaCliente = aliquotaCofinsEntradaCliente;
    }

    /**
     * @return the cstCofinsSaidaCliente
     */
    public String getCstCofinsSaidaCliente() {
        return cstCofinsSaidaCliente;
    }

    /**
     * @param cstCofinsSaidaCliente the cstCofinsSaidaCliente to set
     */
    public void setCstCofinsSaidaCliente(String cstCofinsSaidaCliente) {
        this.cstCofinsSaidaCliente = cstCofinsSaidaCliente;
    }

    /**
     * @return the aliquotaCofinsSaidaCliente
     */
    public Double getAliquotaCofinsSaidaCliente() {
        return aliquotaCofinsSaidaCliente;
    }

    /**
     * @param aliquotaCofinsSaidaCliente the aliquotaCofinsSaidaCliente to set
     */
    public void setAliquotaCofinsSaidaCliente(Double aliquotaCofinsSaidaCliente) {
        this.aliquotaCofinsSaidaCliente = aliquotaCofinsSaidaCliente;
    }

    /**
     * @return the cstIpiEntradaCliente
     */
    public String getCstIpiEntradaCliente() {
        return cstIpiEntradaCliente;
    }

    /**
     * @param cstIpiEntradaCliente the cstIpiEntradaCliente to set
     */
    public void setCstIpiEntradaCliente(String cstIpiEntradaCliente) {
        this.cstIpiEntradaCliente = cstIpiEntradaCliente;
    }

    /**
     * @return the aliquotaIpiEntradaCliente
     */
    public Double getAliquotaIpiEntradaCliente() {
        return aliquotaIpiEntradaCliente;
    }

    /**
     * @param aliquotaIpiEntradaCliente the aliquotaIpiEntradaCliente to set
     */
    public void setAliquotaIpiEntradaCliente(Double aliquotaIpiEntradaCliente) {
        this.aliquotaIpiEntradaCliente = aliquotaIpiEntradaCliente;
    }

    /**
     * @return the cstIpiSaidaCliente
     */
    public String getCstIpiSaidaCliente() {
        return cstIpiSaidaCliente;
    }

    /**
     * @param cstIpiSaidaCliente the cstIpiSaidaCliente to set
     */
    public void setCstIpiSaidaCliente(String cstIpiSaidaCliente) {
        this.cstIpiSaidaCliente = cstIpiSaidaCliente;
    }

    /**
     * @return the aliquotaIpiSaidaCliente
     */
    public Double getAliquotaIpiSaidaCliente() {
        return aliquotaIpiSaidaCliente;
    }

    /**
     * @param aliquotaIpiSaidaCliente the aliquotaIpiSaidaCliente to set
     */
    public void setAliquotaIpiSaidaCliente(Double aliquotaIpiSaidaCliente) {
        this.aliquotaIpiSaidaCliente = aliquotaIpiSaidaCliente;
    }

   
}
