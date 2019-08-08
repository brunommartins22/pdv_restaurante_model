/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.models.Cenario;
import br.com.interagese.syscontabil.models.Cliente;
import br.com.interagese.syscontabil.models.RegraProduto;
import java.math.BigInteger;
import java.util.List;
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
    private ClienteService clienteService;
    @Autowired
    private ProdutoClienteService produtoClienteService;
    @Autowired
    private CenarioService cenarioService;

    @Override
    public List<RegraProduto> findRange(String complementoConsulta, int apartirDe, int quantidade, String ordernacao) {
        List<RegraProduto> result = super.findRange(complementoConsulta, apartirDe, quantidade, ordernacao); //To change body of generated methods, choose Tools | Templates.

        if (!result.isEmpty()) {
            for (RegraProduto rp : result) {
               
                if (rp.getClienteId() != null) {
                    Cliente cliente = clienteService.findById(rp.getClienteId());
                    rp.setNomeCliente(cliente != null ? cliente.getNomeFantasia(): "Todos");
                } else {
                    rp.setNomeCliente("Todos");
                }
                
                if (rp.getCenarioId() != null) {
                    Cenario cenario = cenarioService.findById(rp.getCenarioId());
                    rp.setNomeCenario(cenario != null ? cenario.getNomeCenario() : "Todos");
                } else {
                    rp.setNomeCenario("Todos");
                }
               
            }
        }
        
        return result;
    }
    
    public RegraProduto loadRegraProduto(BigInteger clienteId, Long ean) {
        String sql = "select o from RegraProduto o where";
        if (ean != null) {
            sql += " o.eanProduto = '" + ean + "'";
        } else {
            sql += " o.clienteId = '" + clienteId + "'";
        }

        TypedQuery<RegraProduto> query = em.createQuery(sql, RegraProduto.class);

        return query.getResultList().isEmpty() ? null : query.getSingleResult();
    }

}
