/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import br.com.interagese.erplibrary.EnderecoPadrao;
import br.com.interagese.padrao.rest.models.Cidade;
import br.com.interagese.padrao.rest.models.Estado;
import br.com.interagese.rest.domain.DominioTipoPessoa;
import br.com.interagese.syscontabil.domains.DominioRegime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cliente")
    @SequenceGenerator(name = "gen_cliente", sequenceName = "seq_cliente")
    private Long id;
    @Column(length = 255, nullable = false)
    private String razaoSocial;
    @Column(length = 255)
    private String nomeFantasia;
    @Column(length = 255)
    private String nomeResposavel;
    
    @Enumerated(EnumType.STRING)
    private DominioRegime tipoRegime;
    
    @Column( length = 30)
    @Enumerated(EnumType.STRING)
    private DominioTipoPessoa tipoPessoa;
    
    @Embedded
    private EnderecoPadrao endereco;
    
    @Embedded
    private AtributoPadrao atributoPadrao;
    
    

    public Cliente() {

    }

    //*********************** Eequals && Hashcode ******************************
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.Cliente { id=" + getId() + " ]";
    }

    //*************************** get && setts *********************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getNomeResposavel() {
        return nomeResposavel;
    }

    public void setNomeResposavel(String nomeResposavel) {
        this.nomeResposavel = nomeResposavel;
    }

    public DominioRegime getTipoRegime() {
        return tipoRegime;
    }

    public void setTipoRegime(DominioRegime tipoRegime) {
        this.tipoRegime = tipoRegime;
    }

    public DominioTipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(DominioTipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public EnderecoPadrao getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoPadrao endereco) {
        this.endereco = endereco;
    }

    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }
   

}
