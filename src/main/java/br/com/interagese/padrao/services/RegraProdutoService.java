/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.domains.DominioRegras;
import br.com.interagese.syscontabil.models.RegraProduto;
import br.com.interagese.syscontabil.models.RegraProdutoHistorico;
import br.com.interagese.syscontabil.models.ProdutoGeral;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class RegraProdutoService extends PadraoService<RegraProduto> {

    @Autowired
    private ProdutoGeralService pgs;
    @Autowired
    private ProdutoCenarioService produtoCenarioService;
    @Autowired
    private RegraProdutoHistoricoService regraProdutoHistoricoService;
    
    /**
     * CORRIGIR SQL DE CONSULTA
     */
    public RegraProduto loadRegraProduto(BigInteger clienteId, Long ean) {
        String sql = "select o from RegraProduto o where";
        if (ean != null) {
            sql += " o.eanProduto = '" + ean + "'";
        } else {
            sql += " o.cliente.id = '" + clienteId + "'";
        }

        TypedQuery<RegraProduto> query = em.createQuery(sql, RegraProduto.class);

        return query.getResultList().isEmpty() ? null : query.getSingleResult();
    }

    public boolean existeProdutoCenario(RegraProduto regraProduto) {

        String sqlComplementar = "";
        if (regraProduto.getId() != null) {
            sqlComplementar = " and o.id <> :id ";
        }

        if (regraProduto.getEanProduto()!= null) {
            sqlComplementar += " and o.eanProduto = :ean ";
        } else {
            sqlComplementar += " and o.eanProduto is null ";
        }
        
        if (regraProduto.getCliente()!= null && regraProduto.getCliente().getId() != null) {
            sqlComplementar += " and o.cliente.id = :clienteId ";
        } else {
            sqlComplementar += " and o.cliente.id is null ";
            regraProduto.setCliente(null);
        }
        
        if (regraProduto.getCodigoProduto()!= null) {
            sqlComplementar += " and o.codigoProduto = :codpro ";
        } else {
            sqlComplementar += " and o.codigoProduto is null ";
        }
        
        if (regraProduto.getCenario() != null) {
            sqlComplementar += " and o.cenario.id = :cenarioId ";
        } else {
            sqlComplementar += " and o.cenario.id is null ";
        }
        
        Query query = em.createQuery("SELECT o from RegraProduto o where o.atributoPadrao.dominioEvento <> 3 " + sqlComplementar);

        if (regraProduto.getEanProduto()!= null) {
            query.setParameter("ean", regraProduto.getEanProduto());
        }
        
        if (regraProduto.getCliente()!= null && regraProduto.getCliente().getId() != null) {
            query.setParameter("clienteId", regraProduto.getCliente().getId());
        }
        
        if (regraProduto.getCodigoProduto()!= null) {
            query.setParameter("codpro", regraProduto.getCodigoProduto());
        }
        
        if (regraProduto.getCenario() != null) {
            query.setParameter("cenarioId", regraProduto.getCenario().getId());
        }
        
        if (regraProduto.getId() != null) {
            query.setParameter("id", regraProduto.getId());
        }

        List<RegraProduto> lista = query.getResultList();

        return !lista.isEmpty();

    }
    
    @Override
    public RegraProduto create(RegraProduto obj) throws Exception {
        validar(obj);
        RegraProduto regra = super.create(obj);
        
        String json = Utils.serializar((Object) obj, null);
        RegraProdutoHistorico h = (RegraProdutoHistorico) Utils.deserializar(json , RegraProdutoHistorico.class);
        h.setRegraProduto(regra);
        h.setId(null);
        regraProdutoHistoricoService.create(h);
        
        produtoCenarioService.changeRule(DominioRegras.PRODUTO, regra);
        return super.create(obj);
    }
    
    @Override
    public RegraProduto update(RegraProduto obj) throws Exception {
        validar(obj);
        RegraProduto regra = super.update(obj);
        
        String json = Utils.serializar((Object) obj, null);
        RegraProdutoHistorico h = (RegraProdutoHistorico) Utils.deserializar(json , RegraProdutoHistorico.class);
        h.setRegraProduto(regra);
        h.setId(null);
        regraProdutoHistoricoService.create(h);
        
        return regra;
    }

    public void validar(RegraProduto regraProduto) throws Exception {
        if (existeProdutoCenario(regraProduto)) {
            addErro("Ja existe uma regra para o produto e cenário informados!");
        }
    }
    
    @Override
    public List<RegraProduto> findRange(String complementoConsulta, int apartirDe, int quantidade, String ordernacao) {
        List<RegraProduto> result = super.findRange(complementoConsulta, apartirDe, quantidade, ordernacao); //To change body of generated methods, choose Tools | Templates.

        if (!result.isEmpty()) {
            result.forEach((rt) -> {
                if(rt.getCodigoProduto() == null){
                    rt.setNomeProduto("Todos");
                } else {
                    ProdutoGeral pro = pgs.findById(rt.getCodigoProduto());
                    rt.setNomeProduto(pro.getNomeProduto());
                }
                
                if(rt.getCliente() == null){
                    rt.setNomeCliente("Todos");
                } else {
                    rt.setNomeCliente(rt.getCliente().getRazaoSocial());
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
}
