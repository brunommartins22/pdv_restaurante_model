/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.models.ProdutoGeral;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class ProdutoGeralService extends PadraoService<ProdutoGeral> {
    
    @Override
    public String getWhere(String complementoConsulta) {
        String consultaSQL = "";

        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {
            if (Utils.somenteNumeros(complementoConsulta)) {
               /* consultaSQL = "o.id  = " + complementoConsulta +" or "
                            + "o.ean = " + complementoConsulta;*/
                
            } else {
                consultaSQL =  "o.id  = " + complementoConsulta +" or "+
                               "o.ean = " + complementoConsulta +" or "+
                               "o.ncm  LIKE '%" + complementoConsulta + "%' or "+
                               "o.cest LIKE '%" + complementoConsulta + "' ";
            }
        }
        setOrder("order by o.id");
        return consultaSQL;
    }
    
    
    
}
