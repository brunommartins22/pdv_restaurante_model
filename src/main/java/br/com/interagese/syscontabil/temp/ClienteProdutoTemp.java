/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.temp;

import java.math.BigInteger;

/**
 *
 * @author Bruno Martins
 */
public class ClienteProdutoTemp {

    private BigInteger clienteId;
    private String cpfCnpj;
    private String nomeCliente;
    private BigInteger qtdRegistrosPendentes;
    private BigInteger qtdRegistrosAtualizados;
    private BigInteger qtdRegistro;

    //****************************** get ¨&& setts *****************************
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
     * @return the qtdRegistrosPendentes
     */
    public BigInteger getQtdRegistrosPendentes() {
        return qtdRegistrosPendentes;
    }

    /**
     * @param qtdRegistrosPendentes the qtdRegistrosPendentes to set
     */
    public void setQtdRegistrosPendentes(BigInteger qtdRegistrosPendentes) {
        this.qtdRegistrosPendentes = qtdRegistrosPendentes;
    }

    /**
     * @return the qtdRegistrosAtualizados
     */
    public BigInteger getQtdRegistrosAtualizados() {
        return qtdRegistrosAtualizados;
    }

    /**
     * @param qtdRegistrosAtualizados the qtdRegistrosAtualizados to set
     */
    public void setQtdRegistrosAtualizados(BigInteger qtdRegistrosAtualizados) {
        this.qtdRegistrosAtualizados = qtdRegistrosAtualizados;
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

}
