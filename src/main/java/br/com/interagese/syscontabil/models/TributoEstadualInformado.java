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
    @Column(length = 12)
    private Double aliquotaIcmsEntradaSTInformado;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntradaInformado;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntradaSTInformado;
    @Column(length = 3)
    private String cstIcmsSaidaInformado;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaInformado;
    @Column(length = 12)
    private Double aliquotaIcmsSaidaSTInformado;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsSaidaInformado;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsSaidaSTInformado;
    
    //********************************* get && setts ***************************

    /**
     * @return the cstIcmsEntradaInformado
     */
    public String getCstIcmsEntradaInformado() {
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
        return aliquotaIcmsEntradaInformado;
    }

    /**
     * @param aliquotaIcmsEntradaInformado the aliquotaIcmsEntradaInformado to set
     */
    public void setAliquotaIcmsEntradaInformado(Double aliquotaIcmsEntradaInformado) {
        this.aliquotaIcmsEntradaInformado = aliquotaIcmsEntradaInformado;
    }

    /**
     * @return the aliquotaIcmsEntradaSTInformado
     */
    public Double getAliquotaIcmsEntradaSTInformado() {
        return aliquotaIcmsEntradaSTInformado;
    }

    /**
     * @param aliquotaIcmsEntradaSTInformado the aliquotaIcmsEntradaSTInformado to set
     */
    public void setAliquotaIcmsEntradaSTInformado(Double aliquotaIcmsEntradaSTInformado) {
        this.aliquotaIcmsEntradaSTInformado = aliquotaIcmsEntradaSTInformado;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntradaInformado
     */
    public Double getReducaoBaseCalculoIcmsEntradaInformado() {
        return reducaoBaseCalculoIcmsEntradaInformado;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntradaInformado the reducaoBaseCalculoIcmsEntradaInformado to set
     */
    public void setReducaoBaseCalculoIcmsEntradaInformado(Double reducaoBaseCalculoIcmsEntradaInformado) {
        this.reducaoBaseCalculoIcmsEntradaInformado = reducaoBaseCalculoIcmsEntradaInformado;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntradaSTInformado
     */
    public Double getReducaoBaseCalculoIcmsEntradaSTInformado() {
        return reducaoBaseCalculoIcmsEntradaSTInformado;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntradaSTInformado the reducaoBaseCalculoIcmsEntradaSTInformado to set
     */
    public void setReducaoBaseCalculoIcmsEntradaSTInformado(Double reducaoBaseCalculoIcmsEntradaSTInformado) {
        this.reducaoBaseCalculoIcmsEntradaSTInformado = reducaoBaseCalculoIcmsEntradaSTInformado;
    }

    /**
     * @return the cstIcmsSaidaInformado
     */
    public String getCstIcmsSaidaInformado() {
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
        return aliquotaIcmsSaidaInformado;
    }

    /**
     * @param aliquotaIcmsSaidaInformado the aliquotaIcmsSaidaInformado to set
     */
    public void setAliquotaIcmsSaidaInformado(Double aliquotaIcmsSaidaInformado) {
        this.aliquotaIcmsSaidaInformado = aliquotaIcmsSaidaInformado;
    }

    /**
     * @return the aliquotaIcmsSaidaSTInformado
     */
    public Double getAliquotaIcmsSaidaSTInformado() {
        return aliquotaIcmsSaidaSTInformado;
    }

    /**
     * @param aliquotaIcmsSaidaSTInformado the aliquotaIcmsSaidaSTInformado to set
     */
    public void setAliquotaIcmsSaidaSTInformado(Double aliquotaIcmsSaidaSTInformado) {
        this.aliquotaIcmsSaidaSTInformado = aliquotaIcmsSaidaSTInformado;
    }

    /**
     * @return the reducaoBaseCalculoIcmsSaidaInformado
     */
    public Double getReducaoBaseCalculoIcmsSaidaInformado() {
        return reducaoBaseCalculoIcmsSaidaInformado;
    }

    /**
     * @param reducaoBaseCalculoIcmsSaidaInformado the reducaoBaseCalculoIcmsSaidaInformado to set
     */
    public void setReducaoBaseCalculoIcmsSaidaInformado(Double reducaoBaseCalculoIcmsSaidaInformado) {
        this.reducaoBaseCalculoIcmsSaidaInformado = reducaoBaseCalculoIcmsSaidaInformado;
    }

    /**
     * @return the reducaoBaseCalculoIcmsSaidaSTInformado
     */
    public Double getReducaoBaseCalculoIcmsSaidaSTInformado() {
        return reducaoBaseCalculoIcmsSaidaSTInformado;
    }

    /**
     * @param reducaoBaseCalculoIcmsSaidaSTInformado the reducaoBaseCalculoIcmsSaidaSTInformado to set
     */
    public void setReducaoBaseCalculoIcmsSaidaSTInformado(Double reducaoBaseCalculoIcmsSaidaSTInformado) {
        this.reducaoBaseCalculoIcmsSaidaSTInformado = reducaoBaseCalculoIcmsSaidaSTInformado;
    }

   
}
