/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.dto;

import br.com.interagese.erplibrary.AtributoPadrao;
import br.com.interagese.syscontabil.domains.DominioRegras;
import br.com.interagese.syscontabil.models.Cenario;
import br.com.interagese.syscontabil.models.Cliente;
import br.com.interagese.syscontabil.models.TributoEstadualCliente;
import br.com.interagese.syscontabil.models.TributoEstadualPadrao;
import br.com.interagese.syscontabil.models.TributoFederalCliente;
import br.com.interagese.syscontabil.models.TributoFederalPadrao;

/**
 *
 * @author Joao
 */
public class ProdutoCenarioDto {

    private Long id;
    private Cliente cliente;
    private String nomeProduto;
    private String codigoProduto;
    private Long ean;
    private String ncmCliente;
    private String ncmPadrao;
    private String cestCliente;
    private String cestPadrao;
    private boolean isProdutoGeral; 
    private Cenario cenario;
    private DominioRegras dominioRegras;
    private Long regraId;
    private TributoFederalCliente tributoFederalCliente = new TributoFederalCliente();
    private TributoEstadualCliente tributoEstadualCliente = new TributoEstadualCliente();
    private TributoFederalPadrao tributoFederalPadrao = new TributoFederalPadrao();
    private TributoEstadualPadrao tributoEstadualPadrao = new TributoEstadualPadrao();
    private AtributoPadrao atributoPadrao = new AtributoPadrao();
    private boolean divergente;
    
    //****************************** get && setts ******************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public Long getEan() {
        return ean;
    }

    public void setEan(Long ean) {
        this.ean = ean;
    }

    public String getNcmCliente() {
        return ncmCliente;
    }

    public void setNcmCliente(String ncmCliente) {
        this.ncmCliente = ncmCliente;
    }

    public String getNcmPadrao() {
        return ncmPadrao;
    }

    public void setNcmPadrao(String ncmPadrao) {
        this.ncmPadrao = ncmPadrao;
    }

    public String getCestCliente() {
        return cestCliente;
    }

    public void setCestCliente(String cestCliente) {
        this.cestCliente = cestCliente;
    }

    public String getCestPadrao() {
        return cestPadrao;
    }

    public void setCestPadrao(String cestPadrao) {
        this.cestPadrao = cestPadrao;
    }

    public boolean isIsProdutoGeral() {
        return isProdutoGeral;
    }

    public void setIsProdutoGeral(boolean isProdutoGeral) {
        this.isProdutoGeral = isProdutoGeral;
    }

    public Cenario getCenario() {
        return cenario;
    }

    public void setCenario(Cenario cenario) {
        this.cenario = cenario;
    }

    public DominioRegras getDominioRegras() {
        return dominioRegras;
    }

    public void setDominioRegras(DominioRegras dominioRegras) {
        this.dominioRegras = dominioRegras;
    }

    public Long getRegraId() {
        return regraId;
    }

    public void setRegraId(Long regraId) {
        this.regraId = regraId;
    }

    public TributoFederalCliente getTributoFederalCliente() {
        return tributoFederalCliente;
    }

    public void setTributoFederalCliente(TributoFederalCliente tributoFederalCliente) {
        this.tributoFederalCliente = tributoFederalCliente;
    }

    public TributoEstadualCliente getTributoEstadualCliente() {
        return tributoEstadualCliente;
    }

    public void setTributoEstadualCliente(TributoEstadualCliente tributoEstadualCliente) {
        this.tributoEstadualCliente = tributoEstadualCliente;
    }

    public TributoFederalPadrao getTributoFederalPadrao() {
        return tributoFederalPadrao;
    }

    public void setTributoFederalPadrao(TributoFederalPadrao tributoFederalPadrao) {
        this.tributoFederalPadrao = tributoFederalPadrao;
    }

    public TributoEstadualPadrao getTributoEstadualPadrao() {
        return tributoEstadualPadrao;
    }

    public void setTributoEstadualPadrao(TributoEstadualPadrao tributoEstadualPadrao) {
        this.tributoEstadualPadrao = tributoEstadualPadrao;
    }

    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

    public boolean isDivergente() {
        return divergente;
    }

    public void setDivergente(boolean divergente) {
        this.divergente = divergente;
    }
    
}
