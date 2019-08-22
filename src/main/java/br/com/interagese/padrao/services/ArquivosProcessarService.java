/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.domains.DominioStatusArquivo;
import br.com.interagese.syscontabil.models.ArquivosProcessar;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author joao
 */
@Service
public class ArquivosProcessarService extends PadraoService<ArquivosProcessar> {
    
    @Override
    public String getWhere(String complementoConsulta) {
        String consultaSQL = "";
        
        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {
            if (Utils.somenteNumeros(complementoConsulta)) {
                consultaSQL = "o.id = '" + complementoConsulta;
            } else {
                consultaSQL = "o.statusArquivo = '" + complementoConsulta + "' ";
            }
        }
        
        if(!consultaSQL.contains("statusArquivo")){
            if(!consultaSQL.trim().equals("")){
                consultaSQL += " and";
            }
            consultaSQL += " o.statusArquivo <> 'FINALIZADO' ";
        }
        
        return consultaSQL;
    }
    
    @Override
    public List<ArquivosProcessar> findRange(String complementoConsulta, int apartirDe, int quantidade, String ordernacao) {
        List<ArquivosProcessar> result = super.findRange(complementoConsulta, apartirDe, quantidade, ordernacao); //To change body of generated methods, choose Tools | Templates.

        if (!result.isEmpty()) {
            for (ArquivosProcessar ap : result) {
                ap.setDescricaoStatus(ap.getStatusArquivo().getDescricao());
                
                if (ap.getStatusArquivo() == DominioStatusArquivo.EMANDAMENTO) {
                    ap.setPercentualProcessado((ap.getNumeroRegistrosProcessados() * 100.0)/ap.getNumeroRegistros());
                }
                
                if (ap.getDataFimProcesso() != null && ap.getDataInicioProcesso() != null){
                    Long duracao = ap.getDataFimProcesso().getTime() - ap.getDataInicioProcesso().getTime();
                    ap.setDuracaoProcesso(((duracao.doubleValue())/1000.00));
                }
            }
        }
        
        return result;
    }
   
}
