/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

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
@Table(name = "cenario_federal")
public class CenarioFederal implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "gen_cenario_federal")
    @SequenceGenerator(name = "gen_cenario_federal",sequenceName = "seq_cenario_federal")
    private Long id;
    @Column(length = 255, nullable = false,unique = true)
    private String nomeCenario;
    @Column(length = 10,nullable = false)
    private String cfop;
    @Column(length = 12)
    private String cstPisEntrada;
    @Column(length = 12)
    private Double aliquotaPisEntrada;
    @Column(length = 12)
    private Double aliquotaPisEntradaST;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntrada;
    @Column(length = 12)
    private Double reducaoBaseCalculoIcmsEntradaST;
    @Column(length = 12)
    private String cstPisSaida;
    @Column(length = 12)
    private Double aliquotaPisSaida;
    @Column(length = 12)
    private Double aliquotaPisSaidaST;
    @Column(length = 12)
    private Double reducaoBaseCalculoPisSaida;
    @Column(length = 12)
    private Double reducaoBaseCalculoPisSaidaST;
    @Column(length = 12)
    private String cstCofinsEntrada;
    @Column(length = 12)
    private Double aliquotaCofinsEntrada;
    @Column(length = 12)
    private Double aliquotaCofinsEntradaST;
    @Column(length = 12)
    private Double reducaoBaseCalculoCofinsEntrada;
    @Column(length = 12)
    private Double reducaoBaseCalculoCofinsEntradaST;
    @Column(length = 12)
    private String cstCofinsSaida;
    @Column(length = 12)
    private Double aliquotaCofinsSaida;
    @Column(length = 12)
    private Double aliquotaCofinsSaidaST;
    @Column(length = 12)
    private Double reducaoBaseCalculoCofinsSaida;
    @Column(length = 12)
    private Double reducaoBaseCalculoCofinsSaidaST;
    @Column(length = 12)
    private String cstIpiEntrada;
    @Column(length = 12)
    private Double aliquotaIpiEntrada;
    @Column(length = 12)
    private String cstIpiSaida;
    @Column(length = 12)
    private Double aliquotaIpiSaida;
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();
    
    //************************* Equals && Hashcode *****************************
    
    @Override
    public int hashCode(){
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.getId());
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
        final CenarioFederal other = (CenarioFederal) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.syscontabil.models.CenarioFederal{" + "id=" + getId() + '}';
    }
    
    //************************* get && setts ***********************************
    public Long getId() {
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }

    /**
     * @return the nomeCenario
     */
    public String getNomeCenario() {
        return nomeCenario;
    }

    /**
     * @param nomeCenario the nomeCenario to set
     */
    public void setNomeCenario(String nomeCenario) {
        this.nomeCenario = nomeCenario;
    }

    /**
     * @return the cfop
     */
    public String getCfop() {
        return cfop;
    }

    /**
     * @param cfop the cfop to set
     */
    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    /**
     * @return the cstPisEntrada
     */
    public String getCstPisEntrada() {
        return cstPisEntrada;
    }

    /**
     * @param cstPisEntrada the cstPisEntrada to set
     */
    public void setCstPisEntrada(String cstPisEntrada) {
        this.cstPisEntrada = cstPisEntrada;
    }

    /**
     * @return the aliquotaPisEntrada
     */
    public Double getAliquotaPisEntrada() {
        return aliquotaPisEntrada;
    }

    /**
     * @param aliquotaPisEntrada the aliquotaPisEntrada to set
     */
    public void setAliquotaPisEntrada(Double aliquotaPisEntrada) {
        this.aliquotaPisEntrada = aliquotaPisEntrada;
    }

    /**
     * @return the aliquotaPisEntradaST
     */
    public Double getAliquotaPisEntradaST() {
        return aliquotaPisEntradaST;
    }

    /**
     * @param aliquotaPisEntradaST the aliquotaPisEntradaST to set
     */
    public void setAliquotaPisEntradaST(Double aliquotaPisEntradaST) {
        this.aliquotaPisEntradaST = aliquotaPisEntradaST;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntrada
     */
    public Double getReducaoBaseCalculoIcmsEntrada() {
        return reducaoBaseCalculoIcmsEntrada;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntrada the reducaoBaseCalculoIcmsEntrada to set
     */
    public void setReducaoBaseCalculoIcmsEntrada(Double reducaoBaseCalculoIcmsEntrada) {
        this.reducaoBaseCalculoIcmsEntrada = reducaoBaseCalculoIcmsEntrada;
    }

    /**
     * @return the reducaoBaseCalculoIcmsEntradaST
     */
    public Double getReducaoBaseCalculoIcmsEntradaST() {
        return reducaoBaseCalculoIcmsEntradaST;
    }

    /**
     * @param reducaoBaseCalculoIcmsEntradaST the reducaoBaseCalculoIcmsEntradaST to set
     */
    public void setReducaoBaseCalculoIcmsEntradaST(Double reducaoBaseCalculoIcmsEntradaST) {
        this.reducaoBaseCalculoIcmsEntradaST = reducaoBaseCalculoIcmsEntradaST;
    }

    /**
     * @return the cstPisSaida
     */
    public String getCstPisSaida() {
        return cstPisSaida;
    }

    /**
     * @param cstPisSaida the cstPisSaida to set
     */
    public void setCstPisSaida(String cstPisSaida) {
        this.cstPisSaida = cstPisSaida;
    }

    /**
     * @return the aliquotaPisSaida
     */
    public Double getAliquotaPisSaida() {
        return aliquotaPisSaida;
    }

    /**
     * @param aliquotaPisSaida the aliquotaPisSaida to set
     */
    public void setAliquotaPisSaida(Double aliquotaPisSaida) {
        this.aliquotaPisSaida = aliquotaPisSaida;
    }

    /**
     * @return the aliquotaPisSaidaST
     */
    public Double getAliquotaPisSaidaST() {
        return aliquotaPisSaidaST;
    }

    /**
     * @param aliquotaPisSaidaST the aliquotaPisSaidaST to set
     */
    public void setAliquotaPisSaidaST(Double aliquotaPisSaidaST) {
        this.aliquotaPisSaidaST = aliquotaPisSaidaST;
    }

    /**
     * @return the reducaoBaseCalculoPisSaida
     */
    public Double getReducaoBaseCalculoPisSaida() {
        return reducaoBaseCalculoPisSaida;
    }

    /**
     * @param reducaoBaseCalculoPisSaida the reducaoBaseCalculoPisSaida to set
     */
    public void setReducaoBaseCalculoPisSaida(Double reducaoBaseCalculoPisSaida) {
        this.reducaoBaseCalculoPisSaida = reducaoBaseCalculoPisSaida;
    }

    /**
     * @return the reducaoBaseCalculoPisSaidaST
     */
    public Double getReducaoBaseCalculoPisSaidaST() {
        return reducaoBaseCalculoPisSaidaST;
    }

    /**
     * @param reducaoBaseCalculoPisSaidaST the reducaoBaseCalculoPisSaidaST to set
     */
    public void setReducaoBaseCalculoPisSaidaST(Double reducaoBaseCalculoPisSaidaST) {
        this.reducaoBaseCalculoPisSaidaST = reducaoBaseCalculoPisSaidaST;
    }

    /**
     * @return the cstCofinsEntrada
     */
    public String getCstCofinsEntrada() {
        return cstCofinsEntrada;
    }

    /**
     * @param cstCofinsEntrada the cstCofinsEntrada to set
     */
    public void setCstCofinsEntrada(String cstCofinsEntrada) {
        this.cstCofinsEntrada = cstCofinsEntrada;
    }

    /**
     * @return the aliquotaCofinsEntrada
     */
    public Double getAliquotaCofinsEntrada() {
        return aliquotaCofinsEntrada;
    }

    /**
     * @param aliquotaCofinsEntrada the aliquotaCofinsEntrada to set
     */
    public void setAliquotaCofinsEntrada(Double aliquotaCofinsEntrada) {
        this.aliquotaCofinsEntrada = aliquotaCofinsEntrada;
    }

    /**
     * @return the aliquotaCofinsEntradaST
     */
    public Double getAliquotaCofinsEntradaST() {
        return aliquotaCofinsEntradaST;
    }

    /**
     * @param aliquotaCofinsEntradaST the aliquotaCofinsEntradaST to set
     */
    public void setAliquotaCofinsEntradaST(Double aliquotaCofinsEntradaST) {
        this.aliquotaCofinsEntradaST = aliquotaCofinsEntradaST;
    }

    /**
     * @return the reducaoBaseCalculoCofinsEntrada
     */
    public Double getReducaoBaseCalculoCofinsEntrada() {
        return reducaoBaseCalculoCofinsEntrada;
    }

    /**
     * @param reducaoBaseCalculoCofinsEntrada the reducaoBaseCalculoCofinsEntrada to set
     */
    public void setReducaoBaseCalculoCofinsEntrada(Double reducaoBaseCalculoCofinsEntrada) {
        this.reducaoBaseCalculoCofinsEntrada = reducaoBaseCalculoCofinsEntrada;
    }

    /**
     * @return the reducaoBaseCalculoCofinsEntradaST
     */
    public Double getReducaoBaseCalculoCofinsEntradaST() {
        return reducaoBaseCalculoCofinsEntradaST;
    }

    /**
     * @param reducaoBaseCalculoCofinsEntradaST the reducaoBaseCalculoCofinsEntradaST to set
     */
    public void setReducaoBaseCalculoCofinsEntradaST(Double reducaoBaseCalculoCofinsEntradaST) {
        this.reducaoBaseCalculoCofinsEntradaST = reducaoBaseCalculoCofinsEntradaST;
    }

    /**
     * @return the cstCofinsSaida
     */
    public String getCstCofinsSaida() {
        return cstCofinsSaida;
    }

    /**
     * @param cstCofinsSaida the cstCofinsSaida to set
     */
    public void setCstCofinsSaida(String cstCofinsSaida) {
        this.cstCofinsSaida = cstCofinsSaida;
    }

    /**
     * @return the aliquotaCofinsSaida
     */
    public Double getAliquotaCofinsSaida() {
        return aliquotaCofinsSaida;
    }

    /**
     * @param aliquotaCofinsSaida the aliquotaCofinsSaida to set
     */
    public void setAliquotaCofinsSaida(Double aliquotaCofinsSaida) {
        this.aliquotaCofinsSaida = aliquotaCofinsSaida;
    }

    /**
     * @return the aliquotaCofinsSaidaST
     */
    public Double getAliquotaCofinsSaidaST() {
        return aliquotaCofinsSaidaST;
    }

    /**
     * @param aliquotaCofinsSaidaST the aliquotaCofinsSaidaST to set
     */
    public void setAliquotaCofinsSaidaST(Double aliquotaCofinsSaidaST) {
        this.aliquotaCofinsSaidaST = aliquotaCofinsSaidaST;
    }

    /**
     * @return the reducaoBaseCalculoCofinsSaida
     */
    public Double getReducaoBaseCalculoCofinsSaida() {
        return reducaoBaseCalculoCofinsSaida;
    }

    /**
     * @param reducaoBaseCalculoCofinsSaida the reducaoBaseCalculoCofinsSaida to set
     */
    public void setReducaoBaseCalculoCofinsSaida(Double reducaoBaseCalculoCofinsSaida) {
        this.reducaoBaseCalculoCofinsSaida = reducaoBaseCalculoCofinsSaida;
    }

    /**
     * @return the reducaoBaseCalculoCofinsSaidaST
     */
    public Double getReducaoBaseCalculoCofinsSaidaST() {
        return reducaoBaseCalculoCofinsSaidaST;
    }

    /**
     * @param reducaoBaseCalculoCofinsSaidaST the reducaoBaseCalculoCofinsSaidaST to set
     */
    public void setReducaoBaseCalculoCofinsSaidaST(Double reducaoBaseCalculoCofinsSaidaST) {
        this.reducaoBaseCalculoCofinsSaidaST = reducaoBaseCalculoCofinsSaidaST;
    }

    /**
     * @return the cstIpiEntrada
     */
    public String getCstIpiEntrada() {
        return cstIpiEntrada;
    }

    /**
     * @param cstIpiEntrada the cstIpiEntrada to set
     */
    public void setCstIpiEntrada(String cstIpiEntrada) {
        this.cstIpiEntrada = cstIpiEntrada;
    }

    /**
     * @return the aliquotaIpiEntrada
     */
    public Double getAliquotaIpiEntrada() {
        return aliquotaIpiEntrada;
    }

    /**
     * @param aliquotaIpiEntrada the aliquotaIpiEntrada to set
     */
    public void setAliquotaIpiEntrada(Double aliquotaIpiEntrada) {
        this.aliquotaIpiEntrada = aliquotaIpiEntrada;
    }

    /**
     * @return the cstIpiSaida
     */
    public String getCstIpiSaida() {
        return cstIpiSaida;
    }

    /**
     * @param cstIpiSaida the cstIpiSaida to set
     */
    public void setCstIpiSaida(String cstIpiSaida) {
        this.cstIpiSaida = cstIpiSaida;
    }

    /**
     * @return the aliquotaIpiSaida
     */
    public Double getAliquotaIpiSaida() {
        return aliquotaIpiSaida;
    }

    /**
     * @param aliquotaIpiSaida the aliquotaIpiSaida to set
     */
    public void setAliquotaIpiSaida(Double aliquotaIpiSaida) {
        this.aliquotaIpiSaida = aliquotaIpiSaida;
    }

    /**
     * @return the atributoPadrao
     */
    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    /**
     * @param atributoPadrao the atributoPadrao to set
     */
    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

    
}
