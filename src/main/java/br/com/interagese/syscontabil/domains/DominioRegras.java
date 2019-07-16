/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.domains;

import br.com.interagese.padrao.rest.util.DominioPadrao;

/**
 *
 * @author Bruno Martins
 */
public enum DominioRegras implements DominioPadrao {
    PRODUTO("PRODUTO"), NCM("NCM"), REGIME("REGIME");

    private String descricao;

    private DominioRegras(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

}
