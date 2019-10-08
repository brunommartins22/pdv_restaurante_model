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
public enum DominioCstPisCofins implements DominioPadrao {
    _01("01", "Operação Tributável (base de cálculo = valor da operação alíquota normal (cumulativo/não cumulativo))"),
    _02("02", "Operação Tributável (base de cálculo = valor da operação (alíquota diferenciada))"),
    _04("04", "Operação Tributável (tributação monofásica (alíquota zero))"),
    _05("05", "Operação Tributável (Substituição Tributária)"),
    _06("06", "Operação Tributável (alíquota zero)"),
    _07("07", "Operação Isenta da Contribuição"),
    _08("08", "Operação Sem Incidência da Contribuição"),
    _09("09", "Operação com Suspensão da Contribuição"),
    _49("49", "Outras Operações de Saída"),
    _50("50", "Operação com Direito a Crédito - Vinculada Exclusivamente a Receita Tributada no Mercado Interno"),
    _51("51", "Operação com Direito a Crédito - Vinculada Exclusivamente a Receita Não Tributada no Mercado Interno"),
    _52("52", "Operação com Direito a Crédito – Vinculada Exclusivamente a Receita de Exportação"),
    _53("53", "Operação com Direito a Crédito - Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno"),
    _54("54", "Operação com Direito a Crédito - "
            + " a Receitas Tributadas no Mercado Interno e de Exportação"),
    _55("55", "Operação com Direito a Crédito - Vinculada a Receitas NãoTributadas no Mercado Interno e de Exportação"),
    _56("56", "Operação com Direito a Crédito - Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação"),
    _60("60", "Crédito Presumido - Operação de Aquisição Vinculada Exclusivamente a Receita Tributada no Mercado Interno"),
    _61("61", "Crédito Presumido - Operação de Aquisição Vinculada Exclusivamente a Receita Não-Tributada no Mercado Interno"),
    _62("62", "Crédito Presumido - Operação de Aquisição Vinculada Exclusivamente a Receita de Exportação"),
    _63("63", "Crédito Presumido - Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno"),
    _64("64", "Crédito Presumido - Operação de Aquisição Vinculada a Receitas Tributadas no Mercado Interno e de Exportação"),
    _65("65", "Crédito Presumido - Operação de Aquisição Vinculada a Receitas Não-Tributadas no Mercado Interno e de Exportação"),
    _66("66", "Crédito Presumido - Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação"),
    _67("67", "Crédito Presumido - Outras Operações"),
    _70("70", "Operação de Aquisição sem Direito a Crédito"),
    _71("71", "Operação de Aquisição com Isenção"),
    _72("72", "Operação de Aquisição com Suspensão"),
    _73("73", "Operação de Aquisição a Alíquota Zero"),
    _74("74", "Operação de Aquisição; sem Incidência da Contribuição"),
    _75("75", "Operação de Aquisição por Substituição Tributária"),
    _98("98", "Outras Operações de Entrada"),
    _99("99", "Outras Operações"),;

    private String codigo;
    private String descricao;

    private DominioCstPisCofins(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    public String getCodigo() {
        return codigo;
    }

}
