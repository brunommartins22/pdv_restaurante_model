/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.restaurantemodel.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import br.com.interagese.erplibrary.Utils;
import br.com.interagese.rest.domain.DominioTipoPessoa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_pessoa")
    @SequenceGenerator(name = "gen_pessoa", sequenceName = "seq_pessoa")
    private Long id;
    @Column(length = 255, nullable = false)
    private String nomePessoa;
    @Column(length = 255)
    private String nomeFantasia;
    @Column(length = 17)
    private String cpfCnpj;
    @Column(length = 12)
    private String ieRg;
    @Column(length = 255)
    private String email;
    @Fetch(FetchMode.SELECT)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Endereco> listEndereco;
    @Fetch(FetchMode.SELECT)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Telefone> listTelefone;
    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private DominioTipoPessoa tipoPessoa;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    public String getCpfCnpjDesc() {
        String result = "";
        if (cpfCnpj.length() == 11) {
            result = Utils.formataStringCPF(cpfCnpj);
        } else {
            result = Utils.formataStringCNPJ(cpfCnpj);
        }
        return result;
    }

    //************************** Equals && Hashcode ****************************
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Pessoa other = (Pessoa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    //*************************** get && setts *********************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
     * @return the listEndereco
     */
    public List<Endereco> getListEndereco() {
        if (listEndereco == null) {
            listEndereco = new ArrayList<>();
        }
        return listEndereco;
    }

    /**
     * @param listEndereco the listEndereco to set
     */
    public void setListEndereco(List<Endereco> listEndereco) {
        this.listEndereco = listEndereco;
    }

    /**
     * @return the listTelefone
     */
    public List<Telefone> getListTelefone() {
        if (listTelefone == null) {
            listTelefone = new ArrayList<>();
        }
        return listTelefone;
    }

    /**
     * @param listTelefone the listTelefone to set
     */
    public void setListTelefone(List<Telefone> listTelefone) {
        this.listTelefone = listTelefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the nomePessoa
     */
    public String getNomePessoa() {
        return nomePessoa;
    }

    /**
     * @param nomePessoa the nomePessoa to set
     */
    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public DominioTipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(DominioTipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getIeRg() {
        return ieRg;
    }

    public void setIeRg(String ieRg) {
        this.ieRg = ieRg;
    }

}
