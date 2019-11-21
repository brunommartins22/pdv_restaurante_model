/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.restaurantemodel.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "telefone")
public class Telefone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_telefone")
    @SequenceGenerator(name = "gen_telefone", sequenceName = "seq_telefone")
    private Integer id;
    @Column(length = 15,nullable = false,unique = true)
    private String numero;
    /**
     * 1-Celular;2-fixo
     */
    @Column(length = 1,nullable = false)
    private String tipo;
    /**
     * 1-Oi;2-Tim;3-Vivo;4-Claro;
     */
    @Column(length = 1)
    private String nmOperadora;
    private boolean ativo;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    public String getTipoTelefoneDesc() {
        String result = "";
        switch (tipo) {
            case "1": {
                result = "CELULAR";
                break;
            }
            case "2": {
                result = "FIXO";
                break;
            }
        }
        return result;
    }

    public String getNmOperadoraDesc() {
        String result = "-";
        if (nmOperadora != null && !nmOperadora.equals("")) {
            switch (nmOperadora) {
                case "1": {
                    result = "OI";
                    break;
                }
                case "2": {
                    result = "TIM";
                    break;
                }
                case "3": {
                    result = "VIVO";
                    break;
                }
                case "4": {
                    result = "CLARO";
                    break;
                }
            }
        }
        return result;
    }

    //***************************** equals && hashcode *************************
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final Telefone other = (Telefone) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    //***************************** get && setts *******************************
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * 1-Oi;2-Tim;3-Vivo;4-Claro;
     *
     * @return the nmOperadora
     */
    public String getNmOperadora() {
        return nmOperadora;
    }

    /**
     * 1-Oi;2-Tim;3-Vivo;4-Claro;
     *
     * @param nmOperadora the nmOperadora to set
     */
    public void setNmOperadora(String nmOperadora) {
        this.nmOperadora = nmOperadora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

}
