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
public class TributoFederal implements Serializable {

    @Column(length = 2)
    private String cstPisEntrada;
    @Column(length = 12)
    private Double aliquotaPisEntrada;
    @Column(length = 2)
    private String cstPisSaida;
    @Column(length = 12)
    private Double aliquotaPisSaida;
    @Column(length = 2)
    private String cstCofinsEntrada;
    @Column(length = 12)
    private Double aliquotaCofinsEntrada;
    @Column(length = 2)
    private String cstCofinsSaida;
    @Column(length = 12)
    private Double aliquotaCofinsSaida;
    @Column(length = 12)
    private String cstIpiEntrada;
    @Column(length = 12)
    private Double aliquotaIpiEntrada;
    @Column(length = 2)
    private String cstIpiSaida;
    @Column(length = 12)
    private Double aliquotaIpiSaida;

    /**
     * @return the cstPisEntrada
     */
    public String getCstPisEntrada() {
        return cstPisEntrada;
    }

    /**
     * @param cstPisEntrada the cstPisEntrada to set
     */
    public void setCstPisEntrada(String cstPisEntrada) {
        this.cstPisEntrada = cstPisEntrada;
    }

    /**
     * @return the aliquotaPisEntrada
     */
    public Double getAliquotaPisEntrada() {
        return aliquotaPisEntrada;
    }

    /**
     * @param aliquotaPisEntrada the aliquotaPisEntrada to set
     */
    public void setAliquotaPisEntrada(Double aliquotaPisEntrada) {
        this.aliquotaPisEntrada = aliquotaPisEntrada;
    }

    /**
     * @return the cstPisSaida
     */
    public String getCstPisSaida() {
        return cstPisSaida;
    }

    /**
     * @param cstPisSaida the cstPisSaida to set
     */
    public void setCstPisSaida(String cstPisSaida) {
        this.cstPisSaida = cstPisSaida;
    }

    /**
     * @return the aliquotaPisSaida
     */
    public Double getAliquotaPisSaida() {
        return aliquotaPisSaida;
    }

    /**
     * @param aliquotaPisSaida the aliquotaPisSaida to set
     */
    public void setAliquotaPisSaida(Double aliquotaPisSaida) {
        this.aliquotaPisSaida = aliquotaPisSaida;
    }

    /**
     * @return the cstCofinsEntrada
     */
    public String getCstCofinsEntrada() {
        return cstCofinsEntrada;
    }

    /**
     * @param cstCofinsEntrada the cstCofinsEntrada to set
     */
    public void setCstCofinsEntrada(String cstCofinsEntrada) {
        this.cstCofinsEntrada = cstCofinsEntrada;
    }

    /**
     * @return the aliquotaCofinsEntrada
     */
    public Double getAliquotaCofinsEntrada() {
        return aliquotaCofinsEntrada;
    }

    /**
     * @param aliquotaCofinsEntrada the aliquotaCofinsEntrada to set
     */
    public void setAliquotaCofinsEntrada(Double aliquotaCofinsEntrada) {
        this.aliquotaCofinsEntrada = aliquotaCofinsEntrada;
    }

    /**
     * @return the cstCofinsSaida
     */
    public String getCstCofinsSaida() {
        return cstCofinsSaida;
    }

    /**
     * @param cstCofinsSaida the cstCofinsSaida to set
     */
    public void setCstCofinsSaida(String cstCofinsSaida) {
        this.cstCofinsSaida = cstCofinsSaida;
    }

    /**
     * @return the aliquotaCofinsSaida
     */
    public Double getAliquotaCofinsSaida() {
        return aliquotaCofinsSaida;
    }

    /**
     * @param aliquotaCofinsSaida the aliquotaCofinsSaida to set
     */
    public void setAliquotaCofinsSaida(Double aliquotaCofinsSaida) {
        this.aliquotaCofinsSaida = aliquotaCofinsSaida;
    }

    /**
     * @return the cstIpiEntrada
     */
    public String getCstIpiEntrada() {
        return cstIpiEntrada;
    }

    /**
     * @param cstIpiEntrada the cstIpiEntrada to set
     */
    public void setCstIpiEntrada(String cstIpiEntrada) {
        this.cstIpiEntrada = cstIpiEntrada;
    }

    /**
     * @return the aliquotaIpiEntrada
     */
    public Double getAliquotaIpiEntrada() {
        return aliquotaIpiEntrada;
    }

    /**
     * @param aliquotaIpiEntrada the aliquotaIpiEntrada to set
     */
    public void setAliquotaIpiEntrada(Double aliquotaIpiEntrada) {
        this.aliquotaIpiEntrada = aliquotaIpiEntrada;
    }

    /**
     * @return the cstIpiSaida
     */
    public String getCstIpiSaida() {
        return cstIpiSaida;
    }

    /**
     * @param cstIpiSaida the cstIpiSaida to set
     */
    public void setCstIpiSaida(String cstIpiSaida) {
        this.cstIpiSaida = cstIpiSaida;
    }

    /**
     * @return the aliquotaIpiSaida
     */
    public Double getAliquotaIpiSaida() {
        return aliquotaIpiSaida;
    }

    /**
     * @param aliquotaIpiSaida the aliquotaIpiSaida to set
     */
    public void setAliquotaIpiSaida(Double aliquotaIpiSaida) {
        this.aliquotaIpiSaida = aliquotaIpiSaida;
    }

}
