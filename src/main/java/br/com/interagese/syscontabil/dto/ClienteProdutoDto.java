/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.dto;

import br.com.interagese.syscontabil.models.ProdutoCliente;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bruno Martins
 */
public class ClienteProdutoDto {

    private BigInteger clienteId;
    private String regime;
    private String cpfCnpj;
    private String nomeCliente;
    private BigInteger qtdRegistro;
    private List<ProdutoCliente> resultProdutoCliente;

    //****************************** get && setts ******************************
    /**
     * @return the clienteId
     */
    public BigInteger getClienteId() {
        return clienteId;
    }

    /**
     * @param clienteId the clienteId to set
     */
    public void setClienteId(BigInteger clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * @return the cpfCnpj
     */
    public String getCpfCnpj() {
        return cpfCnpj;
    }

    /**
     * @param cpfCnpj the cpfCnpj to set
     */
    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    /**
     * @return the nomeCliente
     */
    public String getNomeCliente() {
        return nomeCliente;
    }

    /**
     * @param nomeCliente the nomeCliente to set
     */
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    
    /**
     * @return the qtdRegistro
     */
    public BigInteger getQtdRegistro() {
        return qtdRegistro;
    }

    /**
     * @param qtdRegistro the qtdRegistro to set
     */
    public void setQtdRegistro(BigInteger qtdRegistro) {
        this.qtdRegistro = qtdRegistro;
    }

    /**
     * @return the resultProdutoCliente
     */
    public List<ProdutoCliente> getResultProdutoCliente() {
        if (resultProdutoCliente == null) {
            resultProdutoCliente = new ArrayList<>();
        }
        return resultProdutoCliente;
    }

    /**
     * @param resultProdutoCliente the resultProdutoCliente to set
     */
    public void setResultProdutoCliente(List<ProdutoCliente> resultProdutoCliente) {
        this.resultProdutoCliente = resultProdutoCliente;
    }

    /**
     * @return the regime
     */
    public String getRegime() {
        return regime;
    }

    /**
     * @param regime the regime to set
     */
    public void setRegime(String regime) {
        this.regime = regime;
    }

}
