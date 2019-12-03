/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.restaurantemodel.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "fornecedor")
public class Fornecedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_fornecedor")
    @SequenceGenerator(name = "gen_fornecedor", initialValue=1, allocationSize=1, sequenceName = "seq_fornecedor")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Pessoa pessoa;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

}
