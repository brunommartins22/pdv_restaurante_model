/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.syscontabil.models.fileProcessed;

/**
 *
 * @author Joao
 */
public class Arquivo {

    private Long id;

    private Long clienteId;

    private String nmArquivo;

    private String caminho;

    private Long numeroRegistros;

    private Long numeroRegistrosProcessados;

    private Long ultimoRegistroProcessado;
    
    private Long codUsuario;
    
    private String nmUsuario;
    
    private String statusArquivo;

    //**************************** get && setts ********************************

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
     * @return the clienteId
     */
    public Long getClienteId() {
        return clienteId;
    }

    /**
     * @param clienteId the clienteId to set
     */
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * @return the nmArquivo
     */
    public String getNmArquivo() {
        return nmArquivo;
    }

    /**
     * @param nmArquivo the nmArquivo to set
     */
    public void setNmArquivo(String nmArquivo) {
        this.nmArquivo = nmArquivo;
    }

    /**
     * @return the caminho
     */
    public String getCaminho() {
        return caminho;
    }

    /**
     * @param caminho the caminho to set
     */
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    /**
     * @return the numeroRegistros
     */
    public Long getNumeroRegistros() {
        return numeroRegistros;
    }

    /**
     * @param numeroRegistros the numeroRegistros to set
     */
    public void setNumeroRegistros(Long numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    /**
     * @return the numeroRegistrosProcessados
     */
    public Long getNumeroRegistrosProcessados() {
        return numeroRegistrosProcessados;
    }

    /**
     * @param numeroRegistrosProcessados the numeroRegistrosProcessados to set
     */
    public void setNumeroRegistrosProcessados(Long numeroRegistrosProcessados) {
        this.numeroRegistrosProcessados = numeroRegistrosProcessados;
    }

    /**
     * @return the ultimoRegistroProcessado
     */
    public Long getUltimoRegistroProcessado() {
        return ultimoRegistroProcessado;
    }

    /**
     * @param ultimoRegistroProcessado the ultimoRegistroProcessado to set
     */
    public void setUltimoRegistroProcessado(Long ultimoRegistroProcessado) {
        this.ultimoRegistroProcessado = ultimoRegistroProcessado;
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
     * @return the statusArquivo
     */
    public String getStatusArquivo() {
        return statusArquivo;
    }

    /**
     * @param statusArquivo the statusArquivo to set
     */
    public void setStatusArquivo(String statusArquivo) {
        this.statusArquivo = statusArquivo;
    }

    /**
     * @return the codUsuario
     */
    public Long getCodUsuario() {
        return codUsuario;
    }

    /**
     * @param codUsuario the codUsuario to set
     */
    public void setCodUsuario(Long codUsuario) {
        this.codUsuario = codUsuario;
    }
   

}
