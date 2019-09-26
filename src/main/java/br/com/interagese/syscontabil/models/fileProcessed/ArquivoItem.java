/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models.fileProcessed;

/**
 *
 * @author Bruno Martins
 */
public class ArquivoItem {

    private Integer index;
    private Long cenarioId;
    private String codigoProduto;
    private Long ean;
    private String nmProduto;
    private String ncm;
    private String cest;
    private String cstIcmsSaida;
    private Double aliquotaIcmsSaida;
    private String cstPisSaida;
    private Double aliquotaPisSaida;
    private String cstCofinsSaida;
    private Double aliquotaCofinsSaida;
    private String cstIpiSaida;
    private Double aliquotaIpiSaida;

    //**************************************************************************
    /**
     * @return the index
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(Integer index) {
        this.index = index;
    }

    /**
     * @return the cenarioId
     */
    public Long getCenarioId() {
        return cenarioId;
    }

    /**
     * @param cenarioId the cenarioId to set
     */
    public void setCenarioId(Long cenarioId) {
        this.cenarioId = cenarioId;
    }

    /**
     * @return the codigoProduto
     */
    public String getCodigoProduto() {
        return codigoProduto;
    }

    /**
     * @param codigoProduto the codigoProduto to set
     */
    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    /**
     * @return the ean
     */
    public Long getEan() {
        return ean;
    }

    /**
     * @param ean the ean to set
     */
    public void setEan(Long ean) {
        this.ean = ean;
    }

    /**
     * @return the nmProduto
     */
    public String getNmProduto() {
        return nmProduto;
    }

    /**
     * @param nmProduto the nmProduto to set
     */
    public void setNmProduto(String nmProduto) {
        this.nmProduto = nmProduto;
    }

    /**
     * @return the ncm
     */
    public String getNcm() {
        return ncm;
    }

    /**
     * @param ncm the ncm to set
     */
    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    /**
     * @return the cest
     */
    public String getCest() {
        return cest;
    }

    /**
     * @param cest the cest to set
     */
    public void setCest(String cest) {
        this.cest = cest;
    }

    /**
     * @return the cstIcmsSaida
     */
    public String getCstIcmsSaida() {
        return cstIcmsSaida;
    }

    /**
     * @param cstIcmsSaida the cstIcmsSaida to set
     */
    public void setCstIcmsSaida(String cstIcmsSaida) {
        this.cstIcmsSaida = cstIcmsSaida;
    }

    /**
     * @return the aliquotaIcmsSaida
     */
    public Double getAliquotaIcmsSaida() {
        return aliquotaIcmsSaida;
    }

    /**
     * @param aliquotaIcmsSaida the aliquotaIcmsSaida to set
     */
    public void setAliquotaIcmsSaida(Double aliquotaIcmsSaida) {
        this.aliquotaIcmsSaida = aliquotaIcmsSaida;
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
