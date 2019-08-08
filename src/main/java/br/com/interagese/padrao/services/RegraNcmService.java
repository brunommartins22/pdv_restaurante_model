/*
 * RegraNcmDtoo change this license header, choose License Headers in Project Properties.
 * RegraNcmDtoo change this template file, choose RegraNcmDtoools | RegraNcmDtoemplates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.util.PadraoService;

import br.com.interagese.syscontabil.models.Cenario;
import br.com.interagese.syscontabil.models.RegraNcm;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class RegraNcmService extends PadraoService<RegraNcm> {

    @Autowired
    private CenarioService cenarioService;

    @Override
    public List<RegraNcm> findRange(String complementoConsulta, int apartirDe, int quantidade, String ordernacao) {
        List<RegraNcm> result = super.findRange(complementoConsulta, apartirDe, quantidade, ordernacao); //To change body of generated methods, choose Tools | Templates.

        if (!result.isEmpty()) {
            for (RegraNcm rt : result) {
                rt.setNomeRegime(rt.getRegimeTributario().getDescricao());
                String nome = "";
                if (rt.getCenarioId() != null) {
                    Cenario cenario = cenarioService.findById(rt.getCenarioId());
                    nome = cenario != null ? cenario.getNomeCenario() : "Todos";
                } else {
                    nome = "Todos";
                }
                rt.setNomeCenario(nome);
            }
        }
        
        return result;
    }

    public RegraNcm loadRegraNcm(String codigoNcm, String regime) {

        String sql = "select * from syscontabil.regra_ncm where codigo_ncm = :ncm and regime_tributario_id = :regime";

        TypedQuery<RegraNcm> lista = (TypedQuery<RegraNcm>) em.createNativeQuery(sql,RegraNcm.class)
                .setParameter("ncm", codigoNcm)
                .setParameter("regime", regime);

        return lista.getResultList().isEmpty() ? null : lista.getSingleResult();
    }
    
}
