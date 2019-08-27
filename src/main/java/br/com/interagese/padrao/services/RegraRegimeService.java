/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.syscontabil.models.RegraRegimeTributario;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.domains.DominioRegime;
import br.com.interagese.syscontabil.domains.DominioRegras;
import br.com.interagese.syscontabil.models.RegraRegimeTributarioHistorico;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author adam
 */
@Service
public class RegraRegimeService extends PadraoService<RegraRegimeTributario> {

    @Autowired
    private ProdutoCenarioService produtoCenarioService;
    @Autowired
    private RegraRegimeHistoricoService regraRegimeHistoricoService;
    
    //************************ create business rules ***************************
    @Override
    public String getWhere(String complementoConsulta) {
        String consultaSQL = "";

        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {
            if (Utils.somenteNumeros(complementoConsulta)) {
                consultaSQL = "o.id = '" + complementoConsulta;
            } else {
                consultaSQL = "o.regime_tributario = '" + complementoConsulta + "' ";
            }
        }

        return consultaSQL;
    }

    @Override
    public RegraRegimeTributario findById(Object id) {
        RegraRegimeTributario o = (RegraRegimeTributario) em.createQuery("SELECT o from RegraRegimeTributario o where o.id = :id ").setParameter("id", Long.parseLong(id.toString())).getSingleResult();

        return o;
    }

     /**
     * CORRIGIR SQL DE CONSULTA
     */
    public RegraRegimeTributario loadRegraRegimeTributario(String regime) {
        String sql = "select * from syscontabil.regra_regime_tributario where regime_tributario = :regime";

        TypedQuery<RegraRegimeTributario> lista = (TypedQuery<RegraRegimeTributario>) em.createNativeQuery(sql, RegraRegimeTributario.class).setParameter("regime", DominioRegime.valueOf(regime));

        return lista.getResultList().isEmpty() ? null : lista.getSingleResult();
    }

    @Override
    public List<RegraRegimeTributario> findRange(String complementoConsulta, int apartirDe, int quantidade, String ordernacao) {
        List<RegraRegimeTributario> result = super.findRange(complementoConsulta, apartirDe, quantidade, ordernacao); //To change body of generated methods, choose Tools | Templates.

        if (!result.isEmpty()) {
            result.forEach((rt) -> {
                rt.setNomeRegime(rt.getRegimeTributario().getDescricao());
                if(rt.getCenario() == null){
                    rt.setNomeCenario("Todos");
                } else {
                    rt.setNomeCenario(rt.getCenario().getNomeCenario());
                }
            });
        }

        return result;
    }
    
    public boolean existeRegimeCenario(RegraRegimeTributario regraRegime) {

        String sqlComplementar = "";
        if (regraRegime.getId() != null) {
            sqlComplementar = " and o.id <> :id ";
        }

        if (regraRegime.getCenario() != null) {
            sqlComplementar += " and o.cenario.id = :cenarioId ";
        } else {
            sqlComplementar += " and o.cenario.id is null ";
        }
        
        Query query = em.createQuery("SELECT o from RegraRegimeTributario o where o.regimeTributario = :regime and o.atributoPadrao.dominioEvento <> 3 " + sqlComplementar);
        query.setParameter("regime", regraRegime.getRegimeTributario());

        if (regraRegime.getCenario() != null) {
            query.setParameter("cenarioId", regraRegime.getCenario().getId());
        }
        
        if (regraRegime.getId() != null) {
            query.setParameter("id", regraRegime.getId());
        }

        List<RegraRegimeTributario> lista = query.getResultList();

        return !lista.isEmpty();

    }

    @Override
    public RegraRegimeTributario create(RegraRegimeTributario obj) throws Exception {
        validar(obj);
        
        RegraRegimeTributario regra = super.create(obj);
        
        String json = Utils.serializar((Object) obj, null);
        RegraRegimeTributarioHistorico h = (RegraRegimeTributarioHistorico) Utils.deserializar(json , RegraRegimeTributarioHistorico.class);
        h.setRegraRegimeTributario(regra);
        h.setId(null);
        regraRegimeHistoricoService.create(h);
        
        return regra;
    }
    
    @Override
    public RegraRegimeTributario update(RegraRegimeTributario obj) throws Exception {
        validar(obj);
        
        RegraRegimeTributario regra = super.update(obj);
        
        String json = Utils.serializar((Object) obj, null);
        RegraRegimeTributarioHistorico h = (RegraRegimeTributarioHistorico) Utils.deserializar(json , RegraRegimeTributarioHistorico.class);
        h.setRegraRegimeTributario(regra);
        h.setId(null);
        regraRegimeHistoricoService.create(h);
        
        produtoCenarioService.updateRule(DominioRegras.REGIME, regra);
        return regra;
    }

    public void validar(RegraRegimeTributario regraRegime) throws Exception {
        if (existeRegimeCenario(regraRegime)) {
            addErro("Ja existe uma regra para o regime tributário e cenário informados!");
        }
    }
}
