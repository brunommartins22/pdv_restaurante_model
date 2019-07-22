/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.dto;

import javax.persistence.Column;

/**
 *
 * @author Bruno Martins
 */
public class TributoFederalDto  {

    @Column(length = 2)
    private String cstPisEntradaPadrao;
    @Column(length = 12)
    private Double aliquotaPisEntradaPadrao;
    @Column(length = 2)
    private String cstPisSaidaPadrao;
    @Column(length = 12)
    private Double aliquotaPisSaidaPadrao;
    @Column(length = 2)
    private String cstCofinsEntradaPadrao;
    @Column(length = 12)
    private Double aliquotaCofinsEntradaPadrao;
    @Column(length = 2)
    private String cstCofinsSaidaPadrao;
    @Column(length = 12)
    private Double aliquotaCofinsSaidaPadrao;
    @Column(length = 12)
    private String cstIpiEntradaPadrao;
    @Column(length = 12)
    private Double aliquotaIpiEntradaPadrao;
    @Column(length = 2)
    private String cstIpiSaidaPadrao;
    @Column(length = 12)
    private Double aliquotaIpiSaidaPadrao;

    /**
     * @return the cstPisEntradaPadrao
     */
    public String getCstPisEntradaPadrao() {
        if (cstPisEntradaPadrao == null) {
            cstPisEntradaPadrao = "";
        }
        return cstPisEntradaPadrao;
    }

    /**
     * @param cstPisEntradaPadrao the cstPisEntradaPadrao to set
     */
    public void setCstPisEntradaPadrao(String cstPisEntradaPadrao) {
        this.cstPisEntradaPadrao = cstPisEntradaPadrao;
    }

    /**
     * @return the aliquotaPisEntradaPadrao
     */
    public Double getAliquotaPisEntradaPadrao() {
        if (aliquotaPisEntradaPadrao == null) {
            aliquotaPisEntradaPadrao = 0.0;
        }
        return aliquotaPisEntradaPadrao;
    }

    /**
     * @param aliquotaPisEntradaPadrao the aliquotaPisEntradaPadrao to set
     */
    public void setAliquotaPisEntradaPadrao(Double aliquotaPisEntradaPadrao) {
        this.aliquotaPisEntradaPadrao = aliquotaPisEntradaPadrao;
    }

    /**
     * @return the cstPisSaidaPadrao
     */
    public String getCstPisSaidaPadrao() {
        if (cstPisSaidaPadrao == null) {
            cstPisSaidaPadrao = "";
        }
        return cstPisSaidaPadrao;
    }

    /**
     * @param cstPisSaidaPadrao the cstPisSaidaPadrao to set
     */
    public void setCstPisSaidaPadrao(String cstPisSaidaPadrao) {
        this.cstPisSaidaPadrao = cstPisSaidaPadrao;
    }

    /**
     * @return the aliquotaPisSaidaPadrao
     */
    public Double getAliquotaPisSaidaPadrao() {
        if (aliquotaPisSaidaPadrao == null) {
            aliquotaPisSaidaPadrao = 0.0;
        }
        return aliquotaPisSaidaPadrao;
    }

    /**
     * @param aliquotaPisSaidaPadrao the aliquotaPisSaidaPadrao to set
     */
    public void setAliquotaPisSaidaPadrao(Double aliquotaPisSaidaPadrao) {
        this.aliquotaPisSaidaPadrao = aliquotaPisSaidaPadrao;
    }

    /**
     * @return the cstCofinsEntradaPadrao
     */
    public String getCstCofinsEntradaPadrao() {
        if (cstCofinsEntradaPadrao == null) {
            cstCofinsEntradaPadrao = "";
        }
        return cstCofinsEntradaPadrao;
    }

    /**
     * @param cstCofinsEntradaPadrao the cstCofinsEntradaPadrao to set
     */
    public void setCstCofinsEntradaPadrao(String cstCofinsEntradaPadrao) {
        this.cstCofinsEntradaPadrao = cstCofinsEntradaPadrao;
    }

    /**
     * @return the aliquotaCofinsEntradaPadrao
     */
    public Double getAliquotaCofinsEntradaPadrao() {
        if (aliquotaCofinsEntradaPadrao == null) {
            aliquotaCofinsEntradaPadrao = 0.0;
        }
        return aliquotaCofinsEntradaPadrao;
    }

    /**
     * @param aliquotaCofinsEntradaPadrao the aliquotaCofinsEntradaPadrao to set
     */
    public void setAliquotaCofinsEntradaPadrao(Double aliquotaCofinsEntradaPadrao) {
        this.aliquotaCofinsEntradaPadrao = aliquotaCofinsEntradaPadrao;
    }

    /**
     * @return the cstCofinsSaidaPadrao
     */
    public String getCstCofinsSaidaPadrao() {
        if (cstCofinsSaidaPadrao == null) {
            cstCofinsSaidaPadrao = "";
        }
        return cstCofinsSaidaPadrao;
    }

    /**
     * @param cstCofinsSaidaPadrao the cstCofinsSaidaPadrao to set
     */
    public void setCstCofinsSaidaPadrao(String cstCofinsSaidaPadrao) {
        this.cstCofinsSaidaPadrao = cstCofinsSaidaPadrao;
    }

    /**
     * @return the aliquotaCofinsSaidaPadrao
     */
    public Double getAliquotaCofinsSaidaPadrao() {
        if (aliquotaCofinsSaidaPadrao == null) {
            aliquotaCofinsSaidaPadrao = 0.0;
        }
        return aliquotaCofinsSaidaPadrao;
    }

    /**
     * @param aliquotaCofinsSaidaPadrao the aliquotaCofinsSaidaPadrao to set
     */
    public void setAliquotaCofinsSaidaPadrao(Double aliquotaCofinsSaidaPadrao) {
        this.aliquotaCofinsSaidaPadrao = aliquotaCofinsSaidaPadrao;
    }

    /**
     * @return the cstIpiEntradaPadrao
     */
    public String getCstIpiEntradaPadrao() {
        if (cstIpiEntradaPadrao == null) {
            cstIpiEntradaPadrao = "";
        }
        return cstIpiEntradaPadrao;
    }

    /**
     * @param cstIpiEntradaPadrao the cstIpiEntradaPadrao to set
     */
    public void setCstIpiEntradaPadrao(String cstIpiEntradaPadrao) {
        this.cstIpiEntradaPadrao = cstIpiEntradaPadrao;
    }

    /**
     * @return the aliquotaIpiEntradaPadrao
     */
    public Double getAliquotaIpiEntradaPadrao() {
        if (aliquotaIpiEntradaPadrao == null) {
            aliquotaIpiEntradaPadrao = 0.0;
        }
        return aliquotaIpiEntradaPadrao;
    }

    /**
     * @param aliquotaIpiEntradaPadrao the aliquotaIpiEntradaPadrao to set
     */
    public void setAliquotaIpiEntradaPadrao(Double aliquotaIpiEntradaPadrao) {
        this.aliquotaIpiEntradaPadrao = aliquotaIpiEntradaPadrao;
    }

    /**
     * @return the cstIpiSaidaPadrao
     */
    public String getCstIpiSaidaPadrao() {
        if (cstIpiSaidaPadrao == null) {
            cstIpiSaidaPadrao = "";
        }
        return cstIpiSaidaPadrao;
    }

    /**
     * @param cstIpiSaidaPadrao the cstIpiSaidaPadrao to set
     */
    public void setCstIpiSaidaPadrao(String cstIpiSaidaPadrao) {
        this.cstIpiSaidaPadrao = cstIpiSaidaPadrao;
    }

    /**
     * @return the aliquotaIpiSaidaPadrao
     */
    public Double getAliquotaIpiSaidaPadrao() {
        if (aliquotaIpiSaidaPadrao == null) {
            aliquotaIpiSaidaPadrao = 0.0;
        }
        return aliquotaIpiSaidaPadrao;
    }

    /**
     * @param aliquotaIpiSaidaPadrao the aliquotaIpiSaidaPadrao to set
     */
    public void setAliquotaIpiSaidaPadrao(Double aliquotaIpiSaidaPadrao) {
        this.aliquotaIpiSaidaPadrao = aliquotaIpiSaidaPadrao;
    }

}
