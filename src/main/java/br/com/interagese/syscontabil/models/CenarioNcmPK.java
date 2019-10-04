/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import br.com.interagese.syscontabil.domains.DominioRegime;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author Bruno Martins
 */

@Embeddable
public class CenarioNcmPK implements Serializable {
    
    @Column(length = 9, nullable = false)
    private String codigoNcm;
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private DominioRegime regimeTributarioId;
    @Column(nullable = false)
    private Long cenarioId;
    
    //****************************** get && setts ******************************

    /**
     * @return the codigoNcm
     */
    public String getCodigoNcm() {
        return codigoNcm;
    }

    /**
     * @param codigoNcm the codigoNcm to set
     */
    public void setCodigoNcm(String codigoNcm) {
        this.codigoNcm = codigoNcm;
    }

    /**
     * @return the regimeTributarioId
     */
    public DominioRegime getRegimeTributarioId() {
        return regimeTributarioId;
    }

    /**
     * @param regimeTributarioId the regimeTributarioId to set
     */
    public void setRegimeTributarioId(DominioRegime regimeTributarioId) {
        this.regimeTributarioId = regimeTributarioId;
    }

    public Long getCenarioId() {
        return cenarioId;
    }

    public void setCenarioId(Long cenarioId) {
        this.cenarioId = cenarioId;
    }
    
}
