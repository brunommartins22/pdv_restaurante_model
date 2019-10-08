/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.dto;

import br.com.interagese.syscontabil.models.Cenario;
import br.com.interagese.syscontabil.models.Cliente;
import br.com.interagese.syscontabil.models.ProdutoCenario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bruno Martins
 */
public class ProdutoClienteDto {

    private Cliente cliente;
    private List<Cenario> cenarios;
    private List<ProdutoCenario> produtos;

    //******************************* get && setts *****************************
    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the cenarios
     */
    public List<Cenario> getCenarios() {
        if (cenarios == null) {
            cenarios = new ArrayList<>();
        }
        return cenarios;
    }

    /**
     * @param cenarios the cenarios to set
     */
    public void setCenarios(List<Cenario> cenarios) {
        this.cenarios = cenarios;
    }

    /**
     * @return the produtos
     */
    public List<ProdutoCenario> getProdutos() {
        if (produtos == null) {
            produtos = new ArrayList<>();
        }
        return produtos;
    }

    /**
     * @param produtos the produtos to set
     */
    public void setProdutos(List<ProdutoCenario> produtos) {
        this.produtos = produtos;
    }

}
