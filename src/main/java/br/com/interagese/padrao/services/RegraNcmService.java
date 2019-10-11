/*
 * RegraNcmDtoo change this license header, choose License Headers in Project Properties.
 * RegraNcmDtoo change this template file, choose RegraNcmDtoools | RegraNcmDtoemplates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.domains.DominioRegime;
import br.com.interagese.syscontabil.domains.DominioRegras;

import br.com.interagese.syscontabil.models.RegraNcm;
import br.com.interagese.syscontabil.models.RegraNcmHistorico;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.interagese.erplibrary.Utils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Bruno Martins
 */
@Service
public class RegraNcmService extends PadraoService<RegraNcm> {

    @Autowired
    private ProdutoCenarioService produtoCenarioService;
    @Autowired
    private RegraNcmHistoricoService regraNcmHistoricoService;

    @Override
    public List<RegraNcm> findRange(String complementoConsulta, int apartirDe, int quantidade, String ordernacao) {
        List<RegraNcm> result = super.findRange(complementoConsulta, apartirDe, quantidade, ordernacao); //To change body of generated methods, choose Tools | Templates.

        if (!result.isEmpty()) {
            result.forEach((rt) -> {
                if (rt.getRegimeTributario() == null) {
                    rt.setNomeRegime("Todos");
                } else {
                    rt.setNomeRegime(rt.getRegimeTributario().getDescricao());
                }

                if (rt.getCenario() == null) {
                    rt.setNomeCenario("Todos");
                } else {
                    rt.setNomeCenario(rt.getCenario().getNomeCenario());
                }

                if (rt.getCliente() == null) {
                    rt.setNomeCliente("Todos");
                } else {
                    rt.setNomeCliente(rt.getCliente().getRazaoSocial());
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

        if (regraNcm.getCliente() != null && regraNcm.getCliente().getId() != null) {
            sqlComplementar += " and o.cliente.id = :clienteId ";
        } else {
            sqlComplementar += " and o.cliente.id is null ";
            regraNcm.setCliente(null);
        }

        Query query = em.createQuery("SELECT o from RegraNcm o where o.ncm = :ncm and o.atributoPadrao.dominioEvento <> 3 " + sqlComplementar);
        query.setParameter("ncm", regraNcm.getNcm());

        if (regraNcm.getRegimeTributario() != null) {
            query.setParameter("regime", regraNcm.getRegimeTributario());
        }

        if (regraNcm.getCenario() != null) {
            query.setParameter("cenarioId", regraNcm.getCenario().getId());
        }

        if (regraNcm.getId() != null) {
            query.setParameter("id", regraNcm.getId());
        }

        if (regraNcm.getCliente() != null && regraNcm.getCliente().getId() != null) {
            query.setParameter("clienteId", regraNcm.getCliente().getId());
        }

        List<RegraNcm> lista = query.getResultList();

        return !lista.isEmpty();

    }

    @Override
    public RegraNcm create(RegraNcm obj) throws Exception {
        validar(obj);
        super.create(obj);

        String json = Utils.serializar((Object) obj, null);
        RegraNcmHistorico h = (RegraNcmHistorico) Utils.deserializar(json, RegraNcmHistorico.class);
        h.setRegraNcm(obj);
        h.setId(null);
        regraNcmHistoricoService.create(h);

        produtoCenarioService.changeRule(DominioRegras.NCM, obj);

        return obj;
    }

    @Override
    public RegraNcm update(RegraNcm obj) throws Exception {
        validar(obj);
        super.update(obj);

        String json = Utils.serializar((Object) obj, null);
        RegraNcmHistorico h = (RegraNcmHistorico) Utils.deserializar(json, RegraNcmHistorico.class);
        h.setRegraNcm(obj);
        h.setId(null);
        regraNcmHistoricoService.create(h);

        produtoCenarioService.updateRule(DominioRegras.NCM, obj);
        return obj;
    }

    public void validar(RegraNcm regraNcm) throws Exception {
        if (existeNcmRegimeCenario(regraNcm)) {
            addErro("Ja existe uma regra para o ncm, regime tributário, cenário e cliente informados!");
        }
    }

    public RegraNcm loadRegraNcm(Long cenarioId, String ncm, Long clienteId) throws Exception {

        //********************** load cliente ******************************
        List<String> resultRegime = em.createNativeQuery("SELECT tipo_regime as regime FROM syscontabil.cliente WHERE id = '" + clienteId + "'").getResultList();
        String regime = null;
        if (!resultRegime.isEmpty()) {

            regime = resultRegime.get(0);
        }

        RegraNcm regraNcm = null;
        if (regime != null && !regime.isEmpty()) {
            TypedQuery<RegraNcm> result = (TypedQuery<RegraNcm>) em.createNativeQuery("SELECT * FROM syscontabil.regra_ncm WHERE (cenario_id =" + cenarioId + " and ncm = " + (ncm != null ? "'" + ncm + "'" : null) + " and regime_tributario =" + (regime != null ? "'" + regime + "'" : null) + ") or (cenario_id is null and ncm = " + (ncm != null ? "'" + ncm + "'" : null) + " and regime_tributario =" + (regime != null ? "'" + regime + "'" : null) + ")", RegraNcm.class);
            if (!result.getResultList().isEmpty()) {
                regraNcm = result.getSingleResult();
            }
        }

        return regraNcm;

    }
}
