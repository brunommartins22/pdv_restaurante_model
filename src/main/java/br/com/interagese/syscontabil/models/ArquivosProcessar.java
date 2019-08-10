/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.erplibrary.AtributoPadrao;
import br.com.interagese.syscontabil.domains.DominioStatusArquivo;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Joao
 */
@Entity
@Table(name = "arquivos_processar")
public class ArquivosProcessar implements Serializable {

    /**
     * @return the statusArquivo
     */
    public DominioStatusArquivo getStatusArquivo() {
        return statusArquivo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_arquivos_processar")
    @SequenceGenerator(name = "gen_arquivos_processar", sequenceName = "seq_arquivos_processar")
    private Long id;
    
    @ManyToOne
    private Cliente cliente;
    
    @Column(length = 200, nullable = false)
    private String nome;
    
    @Column(length = 200, nullable = false)
    private String caminho;
    
    @Column(nullable = false)
    private Long numeroRegistros;
    
    @Column(nullable = false)
    private Long numeroRegistrosProcessados;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final DominioStatusArquivo statusArquivo;
    
    @Column
    private Long ultimoRegistroProcessado;
    
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();
    
    @Transient
    private String descricaoStatus;
    
    @Transient
    private Double percentualProcessado;
    
    public ArquivosProcessar() {
        this.statusArquivo = DominioStatusArquivo.PENDENTE;
        this.numeroRegistrosProcessados = 0L;
    }
    
    //**************************** Equals && HashCode **************************
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.getId());
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
        final ArquivosProcessar other = (ArquivosProcessar) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.model.ArquivosProcessar{" + "id=" + getId() + '}';
    }

    //**************************** get && setts ********************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Long getQuantidadeRegistros() {
        return numeroRegistros;
    }

    public void setQuantidadeRegistros(Long quantidadeRegistros) {
        this.numeroRegistros = quantidadeRegistros;
    }

    public Long getNumeroRegistrosProcessados() {
        return numeroRegistrosProcessados;
    }

    public void setNumeroRegistrosProcessados(Long numeroRegistrosProcessados) {
        this.numeroRegistrosProcessados = numeroRegistrosProcessados;
    }

    public Long getUltimoRegistroProcessado() {
        return ultimoRegistroProcessado;
    }

    public void setUltimoRegistroProcessado(Long ultimoRegistroProcessado) {
        this.ultimoRegistroProcessado = ultimoRegistroProcessado;
    }

    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

    public Long getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(Long numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public String getDescricaoStatus() {
        return descricaoStatus;
    }

    public void setDescricaoStatus(String descricaoStatus) {
        this.descricaoStatus = descricaoStatus;
    }

    public Double getPercentualProcessado() {
        return percentualProcessado;
    }

    public void setPercentualProcessado(Double percentualProcessado) {
        this.percentualProcessado = percentualProcessado;
    }

    
   
}
