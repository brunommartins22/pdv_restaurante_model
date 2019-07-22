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
public class TributoFederalHistorico implements Serializable {

    @Column(length = 3)
    private String cstPisEntradaHistorico;
    @Column(length = 12)
    private Double aliquotaPisEntradaHistorico;
    @Column(length = 3)
    private String cstPisSaidaHistorico;
    @Column(length = 12)
    private Double aliquotaPisSaidaHistorico;
    @Column(length = 3)
    private String cstCofinsEntradaHistorico;
    @Column(length = 12)
    private Double aliquotaCofinsEntradaHistorico;
    @Column(length = 3)
    private String cstCofinsSaidaHistorico;
    @Column(length = 12)
    private Double aliquotaCofinsSaidaHistorico;
    @Column(length = 3)
    private String cstIpiEntradaHistorico;
    @Column(length = 12)
    private Double aliquotaIpiEntradaHistorico;
    @Column(length = 3)
    private String cstIpiSaidaHistorico;
    @Column(length = 12)
    private Double aliquotaIpiSaidaHistorico;
    
    //********************************* get && setts ***************************

    /**
     * @return the cstPisEntradaHistorico
     */
    public String getCstPisEntradaHistorico() {
        return cstPisEntradaHistorico;
    }

    /**
     * @param cstPisEntradaHistorico the cstPisEntradaHistorico to set
     */
    public void setCstPisEntradaHistorico(String cstPisEntradaHistorico) {
        this.cstPisEntradaHistorico = cstPisEntradaHistorico;
    }

    /**
     * @return the aliquotaPisEntradaHistorico
     */
    public Double getAliquotaPisEntradaHistorico() {
        return aliquotaPisEntradaHistorico;
    }

    /**
     * @param aliquotaPisEntradaHistorico the aliquotaPisEntradaHistorico to set
     */
    public void setAliquotaPisEntradaHistorico(Double aliquotaPisEntradaHistorico) {
        this.aliquotaPisEntradaHistorico = aliquotaPisEntradaHistorico;
    }

    /**
     * @return the cstPisSaidaHistorico
     */
    public String getCstPisSaidaHistorico() {
        return cstPisSaidaHistorico;
    }

    /**
     * @param cstPisSaidaHistorico the cstPisSaidaHistorico to set
     */
    public void setCstPisSaidaHistorico(String cstPisSaidaHistorico) {
        this.cstPisSaidaHistorico = cstPisSaidaHistorico;
    }

    /**
     * @return the aliquotaPisSaidaHistorico
     */
    public Double getAliquotaPisSaidaHistorico() {
        return aliquotaPisSaidaHistorico;
    }

    /**
     * @param aliquotaPisSaidaHistorico the aliquotaPisSaidaHistorico to set
     */
    public void setAliquotaPisSaidaHistorico(Double aliquotaPisSaidaHistorico) {
        this.aliquotaPisSaidaHistorico = aliquotaPisSaidaHistorico;
    }

    /**
     * @return the cstCofinsEntradaHistorico
     */
    public String getCstCofinsEntradaHistorico() {
        return cstCofinsEntradaHistorico;
    }

    /**
     * @param cstCofinsEntradaHistorico the cstCofinsEntradaHistorico to set
     */
    public void setCstCofinsEntradaHistorico(String cstCofinsEntradaHistorico) {
        this.cstCofinsEntradaHistorico = cstCofinsEntradaHistorico;
    }

    /**
     * @return the aliquotaCofinsEntradaHistorico
     */
    public Double getAliquotaCofinsEntradaHistorico() {
        return aliquotaCofinsEntradaHistorico;
    }

    /**
     * @param aliquotaCofinsEntradaHistorico the aliquotaCofinsEntradaHistorico to set
     */
    public void setAliquotaCofinsEntradaHistorico(Double aliquotaCofinsEntradaHistorico) {
        this.aliquotaCofinsEntradaHistorico = aliquotaCofinsEntradaHistorico;
    }

    /**
     * @return the cstCofinsSaidaHistorico
     */
    public String getCstCofinsSaidaHistorico() {
        return cstCofinsSaidaHistorico;
    }

    /**
     * @param cstCofinsSaidaHistorico the cstCofinsSaidaHistorico to set
     */
    public void setCstCofinsSaidaHistorico(String cstCofinsSaidaHistorico) {
        this.cstCofinsSaidaHistorico = cstCofinsSaidaHistorico;
    }

    /**
     * @return the aliquotaCofinsSaidaHistorico
     */
    public Double getAliquotaCofinsSaidaHistorico() {
        return aliquotaCofinsSaidaHistorico;
    }

    /**
     * @param aliquotaCofinsSaidaHistorico the aliquotaCofinsSaidaHistorico to set
     */
    public void setAliquotaCofinsSaidaHistorico(Double aliquotaCofinsSaidaHistorico) {
        this.aliquotaCofinsSaidaHistorico = aliquotaCofinsSaidaHistorico;
    }

    /**
     * @return the cstIpiEntradaHistorico
     */
    public String getCstIpiEntradaHistorico() {
        return cstIpiEntradaHistorico;
    }

    /**
     * @param cstIpiEntradaHistorico the cstIpiEntradaHistorico to set
     */
    public void setCstIpiEntradaHistorico(String cstIpiEntradaHistorico) {
        this.cstIpiEntradaHistorico = cstIpiEntradaHistorico;
    }

    /**
     * @return the aliquotaIpiEntradaHistorico
     */
    public Double getAliquotaIpiEntradaHistorico() {
        return aliquotaIpiEntradaHistorico;
    }

    /**
     * @param aliquotaIpiEntradaHistorico the aliquotaIpiEntradaHistorico to set
     */
    public void setAliquotaIpiEntradaHistorico(Double aliquotaIpiEntradaHistorico) {
        this.aliquotaIpiEntradaHistorico = aliquotaIpiEntradaHistorico;
    }

    /**
     * @return the cstIpiSaidaHistorico
     */
    public String getCstIpiSaidaHistorico() {
        return cstIpiSaidaHistorico;
    }

    /**
     * @param cstIpiSaidaHistorico the cstIpiSaidaHistorico to set
     */
    public void setCstIpiSaidaHistorico(String cstIpiSaidaHistorico) {
        this.cstIpiSaidaHistorico = cstIpiSaidaHistorico;
    }

    /**
     * @return the aliquotaIpiSaidaHistorico
     */
    public Double getAliquotaIpiSaidaHistorico() {
        return aliquotaIpiSaidaHistorico;
    }

    /**
     * @param aliquotaIpiSaidaHistorico the aliquotaIpiSaidaHistorico to set
     */
    public void setAliquotaIpiSaidaHistorico(Double aliquotaIpiSaidaHistorico) {
        this.aliquotaIpiSaidaHistorico = aliquotaIpiSaidaHistorico;
    }

   
}
