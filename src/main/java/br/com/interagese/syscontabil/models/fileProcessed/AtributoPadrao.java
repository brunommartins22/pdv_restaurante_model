/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models.fileProcessed;

import java.util.Date;

/**
 *
 * @author Bruno Martins
 */
public class AtributoPadrao {
    
    private Long idUsuario;
    private String nmUsuario;
    private int rgEvento;
    private Date rgData;
    
    //***************************** get && setts *******************************

    /**
     * @return the idUsuario
     */
    public Long getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the nmUsuario
     */
    public String getNmUsuario() {
        return nmUsuario;
    }

    /**
     * @param nmUsuario the nmUsuario to set
     */
    public void setNmUsuario(String nmUsuario) {
        this.nmUsuario = nmUsuario;
    }

    /**
     * @return the rgEvento
     */
    public int getRgEvento() {
        return rgEvento;
    }

    /**
     * @param rgEvento the rgEvento to set
     */
    public void setRgEvento(int rgEvento) {
        this.rgEvento = rgEvento;
    }

    /**
     * @return the rgData
     */
    public Date getRgData() {
        return rgData;
    }

    /**
     * @param rgData the rgData to set
     */
    public void setRgData(Date rgData) {
        this.rgData = rgData;
    }
    
}
