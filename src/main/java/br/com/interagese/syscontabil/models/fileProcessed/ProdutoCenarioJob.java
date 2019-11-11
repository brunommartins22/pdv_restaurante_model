/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models.fileProcessed;

import br.com.interagese.syscontabil.models.TributoEstadualCliente;
import br.com.interagese.syscontabil.models.TributoEstadualPadrao;
import br.com.interagese.syscontabil.models.TributoFederalCliente;
import br.com.interagese.syscontabil.models.TributoFederalPadrao;
import javax.persistence.Embedded;

/**
 *
 * @author Bruno Martins
 */
public class ProdutoCenarioJob {

    private Long id;
    private Long idProduto;
    private Long idCenario;
    private String nmRegra;
    private Long idRegra;
    @Embedded
    private TributoEstadualCliente tributoEstadualCliente;
    @Embedded
    private TributoEstadualPadrao tributoEstadualPadrao;
    @Embedded
    private TributoFederalCliente tributoFederalCliente;
    @Embedded
    private TributoFederalPadrao tributoFederalPadrao;
    private boolean divergente;
    private AtributoPadrao atributoPadrao;

    /**
     * @return the idProduto
     */
    public Long getIdProduto() {
        return idProduto;
    }

    /**
     * @param idProduto the idProduto to set
     */
    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    /**
     * @return the idCenario
     */
    public Long getIdCenario() {
        return idCenario;
    }

    /**
     * @param idCenario the idCenario to set
     */
    public void setIdCenario(Long idCenario) {
        this.idCenario = idCenario;
    }

    /**
     * @return the nmRegra
     */
    public String getNmRegra() {
        return nmRegra;
    }

    /**
     * @param nmRegra the nmRegra to set
     */
    public void setNmRegra(String nmRegra) {
        this.nmRegra = nmRegra;
    }

    /**
     * @return the idRegra
     */
    public Long getIdRegra() {
        return idRegra;
    }

    /**
     * @param idRegra the idRegra to set
     */
    public void setIdRegra(Long idRegra) {
        this.idRegra = idRegra;
    }

    /**
     * @return the divergente
     */
    public boolean isDivergente() {
        return divergente;
    }

    /**
     * @param divergente the divergente to set
     */
    public void setDivergente(boolean divergente) {
        this.divergente = divergente;
    }

    /**
     * @return the atributoPadrao
     */
    public AtributoPadrao getAtributoPadrao() {
        if (atributoPadrao == null) {
            atributoPadrao = new AtributoPadrao();
        }
        return atributoPadrao;
    }

    /**
     * @param atributoPadrao the atributoPadrao to set
     */
    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

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
     * @return the tributoEstadualCliente
     */
    public TributoEstadualCliente getTributoEstadualCliente() {
        if (tributoEstadualCliente == null) {
            tributoEstadualCliente = new TributoEstadualCliente();
        }
        return tributoEstadualCliente;
    }

    /**
     * @param tributoEstadualCliente the tributoEstadualCliente to set
     */
    public void setTributoEstadualCliente(TributoEstadualCliente tributoEstadualCliente) {
        this.tributoEstadualCliente = tributoEstadualCliente;
    }

    /**
     * @return the tributoEstadualPadrao
     */
    public TributoEstadualPadrao getTributoEstadualPadrao() {
        if (tributoEstadualPadrao == null) {
            tributoEstadualPadrao = new TributoEstadualPadrao();
        }
        return tributoEstadualPadrao;
    }

    /**
     * @param tributoEstadualPadrao the tributoEstadualPadrao to set
     */
    public void setTributoEstadualPadrao(TributoEstadualPadrao tributoEstadualPadrao) {
        this.tributoEstadualPadrao = tributoEstadualPadrao;
    }

    /**
     * @return the tributoFederalCliente
     */
    public TributoFederalCliente getTributoFederalCliente() {
        if (tributoFederalCliente == null) {
            tributoFederalCliente = new TributoFederalCliente();
        }
        return tributoFederalCliente;
    }

    /**
     * @param tributoFederalCliente the tributoFederalCliente to set
     */
    public void setTributoFederalCliente(TributoFederalCliente tributoFederalCliente) {
        this.tributoFederalCliente = tributoFederalCliente;
    }

    /**
     * @return the tributoFederalPadrao
     */
    public TributoFederalPadrao getTributoFederalPadrao() {
        if (tributoFederalPadrao == null) {
            tributoFederalPadrao = new TributoFederalPadrao();
        }
        return tributoFederalPadrao;
    }

    /**
     * @param tributoFederalPadrao the tributoFederalPadrao to set
     */
    public void setTributoFederalPadrao(TributoFederalPadrao tributoFederalPadrao) {
        this.tributoFederalPadrao = tributoFederalPadrao;
    }

}
