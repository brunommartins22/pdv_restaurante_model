/*
 * RegraNcmDtoo change this license header, choose License Headers in Project Properties.
 * RegraNcmDtoo change this template file, choose RegraNcmDtoools | RegraNcmDtoemplates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.domains.DominioRegime;

import br.com.interagese.syscontabil.models.RegraNcm;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class RegraNcmService extends PadraoService<RegraNcm> {

    @Override
    public List<RegraNcm> findRange(String complementoConsulta, int apartirDe, int quantidade, String ordernacao) {
        List<RegraNcm> result = super.findRange(complementoConsulta, apartirDe, quantidade, ordernacao); //To change body of generated methods, choose Tools | Templates.

        if (!result.isEmpty()) {
            result.forEach((rt) -> {
                if(rt.getRegimeTributario()== null){
                    rt.setNomeRegime("Todos");
                } else {
                    rt.setNomeRegime(rt.getRegimeTributario().getDescricao());
                }
                
                if(rt.getCenario() == null){
                    rt.setNomeCenario("Todos");
                } else {
                    rt.setNomeCenario(rt.getCenario().getNomeCenario());
                }
            });
        }

        return result;
    }

    /**
     * CORRIGIR SQL DE CONSULTA
     */
    public RegraNcm loadRegraNcm(String codigoNcm, String regime) {

        String sql = "select * from syscontabil.regra_ncm where codigo_ncm = :ncm and regime_tributario = :regime";

        TypedQuery<RegraNcm> lista = (TypedQuery<RegraNcm>) em.createNativeQuery(sql, RegraNcm.class)
                .setParameter("ncm", codigoNcm)
                .setParameter("regime", DominioRegime.valueOf(regime));

        return lista.getResultList().isEmpty() ? null : lista.getSingleResult();
    }

    public boolean existeNcmRegimeCenario(RegraNcm regraNcm) {

        String sqlComplementar = "";
        if (regraNcm.getId() != null) {
            sqlComplementar = " and o.id <> :id ";
        }

        if (regraNcm.getRegimeTributario() != null) {
            sqlComplementar += " and o.regimeTributario = :regime ";
        } else {
            sqlComplementar += " and o.regimeTributario is null ";
        }
        
        if (regraNcm.getCenario() != null) {
            sqlComplementar += " and o.cenario.id = :cenarioId ";
        } else {
            sqlComplementar += " and o.cenario.id is null ";
        }
        
        Query query = em.createQuery("SELECT o from RegraNcm o where o.ncm = :ncm and o.atributoPadrao.dominioEvento <> 3 " + sqlComplementar);
        query.setParameter("ncm", regraNcm.getNcm());

        if (regraNcm.getRegimeTributario()!= null) {
            query.setParameter("regime", regraNcm.getRegimeTributario());
        }
        
        if (regraNcm.getCenario() != null) {
            query.setParameter("cenarioId", regraNcm.getCenario().getId());
        }
        
        if (regraNcm.getId() != null) {
            query.setParameter("id", regraNcm.getId());
        }

        List<RegraNcm> lista = query.getResultList();

        return !lista.isEmpty();

    }
    
    @Override
    public RegraNcm create(RegraNcm obj) throws Exception {
        validar(obj);
        return super.create(obj);
    }
    
    @Override
    public RegraNcm update(RegraNcm obj) throws Exception {
        validar(obj);
        return super.update(obj);
    }

    public void validar(RegraNcm regraNcm) throws Exception {
        if (existeNcmRegimeCenario(regraNcm)) {
            addErro("Ja existe uma regra para o ncm, regime tributário e cenário informados!");
        }
    }
}
