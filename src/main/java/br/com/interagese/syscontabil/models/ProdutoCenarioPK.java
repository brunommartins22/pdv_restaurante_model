/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Bruno Martins
 */

@Embeddable
public class ProdutoCenarioPK implements Serializable {
    
    @Column(length = 20, nullable = false)
    private Long produtoId;
    @Column(length = 20, nullable = false)
    private Long cenarioId;
    //****************************** get && setts ******************************

    /**
     * @return the produtoId
     */
    public Long getProdutoId() {
        return produtoId;
    }

    /**
     * @param produtoId the produtoId to set
     */
    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    /**
     * @return the cenarioId
     */
    public Long getCenarioId() {
        return cenarioId;
    }

    /**
     * @param cenarioId the cenarioId to set
     */
    public void setCenarioId(Long cenarioId) {
        this.cenarioId = cenarioId;
    }

    
}
