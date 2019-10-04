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
public enum DominioRegime implements DominioPadrao {
    LUCROREAL("LUCRO REAL"), LUCROPRESUMIDO("LUCRO PRESUMIDO"), SIMPLESNACIONAL("SIMPLES NACIONAL");

    private String descricao;

    private DominioRegime(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

}
