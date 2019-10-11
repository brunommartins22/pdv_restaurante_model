/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.dto;

/**
 *
 * @author Bruno Martins
 */
public class CountProdutos {
    
    private Long countProdutosValidados;
    private Long countProdutosPendentes;
    private Long countProdutosDivengentes;
    private Long countProdutosInexistentes;
    private Long countProdutosInativos;
    
    //**************************** get && setts ********************************

    /**
     * @return the countProdutosValidados
     */
    public Long getCountProdutosValidados() {
        return countProdutosValidados;
    }

    /**
     * @param countProdutosValidados the countProdutosValidados to set
     */
    public void setCountProdutosValidados(Long countProdutosValidados) {
        this.countProdutosValidados = countProdutosValidados;
    }

    /**
     * @return the countProdutosPendentes
     */
    public Long getCountProdutosPendentes() {
        return countProdutosPendentes;
    }

    /**
     * @param countProdutosPendentes the countProdutosPendentes to set
     */
    public void setCountProdutosPendentes(Long countProdutosPendentes) {
        this.countProdutosPendentes = countProdutosPendentes;
    }

    /**
     * @return the countProdutosDivengentes
     */
    public Long getCountProdutosDivengentes() {
        return countProdutosDivengentes;
    }

    /**
     * @param countProdutosDivengentes the countProdutosDivengentes to set
     */
    public void setCountProdutosDivengentes(Long countProdutosDivengentes) {
        this.countProdutosDivengentes = countProdutosDivengentes;
    }

    /**
     * @return the countProdutosInexistentes
     */
    public Long getCountProdutosInexistentes() {
        return countProdutosInexistentes;
    }

    /**
     * @param countProdutosInexistentes the countProdutosInexistentes to set
     */
    public void setCountProdutosInexistentes(Long countProdutosInexistentes) {
        this.countProdutosInexistentes = countProdutosInexistentes;
    }

    /**
     * @return the countProdutosInativos
     */
    public Long getCountProdutosInativos() {
        return countProdutosInativos;
    }

    /**
     * @param countProdutosInativos the countProdutosInativos to set
     */
    public void setCountProdutosInativos(Long countProdutosInativos) {
        this.countProdutosInativos = countProdutosInativos;
    }
    
    
}
