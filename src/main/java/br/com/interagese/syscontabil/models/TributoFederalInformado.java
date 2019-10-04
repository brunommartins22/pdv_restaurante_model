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
        if (cstPisEntradaInformado == null) {
            cstPisEntradaInformado = "";
        }
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
        if (aliquotaPisEntradaInformado == null) {
            aliquotaPisEntradaInformado = 0.0;
        }
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
        if (cstPisSaidaInformado == null) {
            cstPisSaidaInformado = "";
        }
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
        if (aliquotaPisSaidaInformado == null) {
            aliquotaPisSaidaInformado = 0.0;
        }
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
        if (cstCofinsEntradaInformado == null) {
            cstCofinsEntradaInformado = "";
        }
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
        if (aliquotaCofinsEntradaInformado == null) {
            aliquotaCofinsEntradaInformado = 0.0;
        }
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
        if (cstCofinsSaidaInformado == null) {
            cstCofinsSaidaInformado = "";
        }
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
        if (aliquotaCofinsSaidaInformado == null) {
            aliquotaCofinsSaidaInformado = 0.0;
        }
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
        if (cstIpiEntradaInformado == null) {
            cstIpiEntradaInformado = "";
        }
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
        if (aliquotaIpiEntradaInformado == null) {
            aliquotaIpiEntradaInformado = 0.0;
        }
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
        if (cstIpiSaidaInformado == null) {
            cstIpiSaidaInformado = "";
        }
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
        if (aliquotaIpiSaidaInformado == null) {
            aliquotaIpiSaidaInformado = 0.0;
        }
        return aliquotaIpiSaidaInformado;
    }

    /**
     * @param aliquotaIpiSaidaInformado the aliquotaIpiSaidaInformado to set
     */
    public void setAliquotaIpiSaidaInformado(Double aliquotaIpiSaidaInformado) {
        this.aliquotaIpiSaidaInformado = aliquotaIpiSaidaInformado;
    }


   
}
