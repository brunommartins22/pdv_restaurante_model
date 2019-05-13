/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.padrao.rest.models.Cidade;
import br.com.interagese.padrao.rest.models.Estado;
import br.com.interagese.padrao.rest.models.Usuario;
import br.com.interagese.rest.domain.DominioTipoPessoa;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_cli")
    @SequenceGenerator(name = "gen_cli", sequenceName = "seq_cli")
    private Long id;
    @Column(length = 50, nullable = false)
    private String nmCliente;
    @Column(length = 14, nullable = false, unique = true)
    private String cpfCnpj;
    @Column(length = 255)
    private String nmFantasia;
    @Enumerated(EnumType.STRING)
    private DominioTipoPessoa tpCliente;
    @Column(length = 120)
    private String email;
    @Column(length = 12, nullable = false, unique = true)
    private String rgIe;
    @Column(length = 255)
    private String endereco;
    @Column(length = 120)
    private String numero;
    @Column(length = 120)
    private String bairro;
    @Column(length = 120)
    private String pais;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "cliente_fk_cidade"))
    private Cidade cidade;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "cliente_fk_estado"))
    private Estado estado;
    @Column(length = 8, unique = true)
    private String cep;
    @Column(length = 255)
    private String perimetro;
    @Column(length = 12)
    private String fone1;
    @Column(length = 12)
    private String fone2;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "cliente_fk_usuario"))
    private Usuario usuarioRegistrador;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRegistro;
    
    
    

    public Cliente() {
        dataRegistro = new Date();
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

    /**
     * @return the nmCliente
     */
    public String getNmCliente() {
        return nmCliente;
    }

    /**
     * @param nmCliente the nmCliente to set
     */
    public void setNmCliente(String nmCliente) {
        this.nmCliente = nmCliente;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the nmFantasia
     */
    public String getNmFantasia() {
        return nmFantasia;
    }

    /**
     * @param nmFantasia the nmFantasia to set
     */
    public void setNmFantasia(String nmFantasia) {
        this.nmFantasia = nmFantasia;
    }

    /**
     * @return the tpCliente
     */
    public DominioTipoPessoa getTpCliente() {
        return tpCliente;
    }

    /**
     * @param tpCliente the tpCliente to set
     */
    public void setTpCliente(DominioTipoPessoa tpCliente) {
        this.tpCliente = tpCliente;
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
     * @return the rgIe
     */
    public String getRgIe() {
        return rgIe;
    }

    /**
     * @param rgIe the rgIe to set
     */
    public void setRgIe(String rgIe) {
        this.rgIe = rgIe;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the cidade
     */
    public Cidade getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the perimetro
     */
    public String getPerimetro() {
        return perimetro;
    }

    /**
     * @param perimetro the perimetro to set
     */
    public void setPerimetro(String perimetro) {
        this.perimetro = perimetro;
    }

    /**
     * @return the fone1
     */
    public String getFone1() {
        return fone1;
    }

    /**
     * @param fone1 the fone1 to set
     */
    public void setFone1(String fone1) {
        this.fone1 = fone1;
    }

    /**
     * @return the fone2
     */
    public String getFone2() {
        return fone2;
    }

    /**
     * @param fone2 the fone2 to set
     */
    public void setFone2(String fone2) {
        this.fone2 = fone2;
    }

    /**
     * @return the usuarioRegistrador
     */
    public Usuario getUsuarioRegistrador() {
        return usuarioRegistrador;
    }

    /**
     * @param usuarioRegistrador the usuarioRegistrador to set
     */
    public void setUsuarioRegistrador(Usuario usuarioRegistrador) {
        this.usuarioRegistrador = usuarioRegistrador;
    }

    /**
     * @return the dataRegistro
     */
    public Date getDataRegistro() {
        if (dataRegistro == null) {
            dataRegistro = new Date();
        }
        return dataRegistro;
    }

    /**
     * @param dataRegistro the dataRegistro to set
     */
    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
