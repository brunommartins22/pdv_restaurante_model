/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.padrao.rest.models.Estado;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Bruno Martins
 */

@Entity
@Table(name = "icms_inter_estadual")
public class IcmsInterEstadual implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "gen_icms_inter_estadual")
    @SequenceGenerator(name = "gen_icms_inter_estadual", sequenceName = "gen_icms_inter_estadual")
    private Long id;
    @ManyToOne
    private Estado estado;
    private Double percentualAliquota;
    
    //*************************** get && hashcode ******************************

    @Override
    public int hashCode() {
        int hash = 7;
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
        final IcmsInterEstadual other = (IcmsInterEstadual) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    //***************************** get && setts *******************************

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the percentualAliquota
     */
    public Double getPercentualAliquota() {
        return percentualAliquota;
    }

    /**
     * @param percentualAliquota the percentualAliquota to set
     */
    public void setPercentualAliquota(Double percentualAliquota) {
        this.percentualAliquota = percentualAliquota;
    }
    
    
}
