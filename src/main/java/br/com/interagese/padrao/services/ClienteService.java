/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.util.FiltroParametro;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.rest.domain.DominioTipoPessoa;
import br.com.interagese.syscontabil.models.Cliente;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class ClienteService extends PadraoService<Cliente> {

    public ClienteService() {
        order = " order by o.razaoSocial";
    }

    public boolean existeCpfCnpj(Cliente cliente) {

        String sqlComplementar = "";
        if (cliente.getId() != null) {
            sqlComplementar = " and o.id <> :id ";
        }

        Query query = em.createQuery("SELECT o from Cliente o where o.cpfCnpj = :cpfCnpj and o.atributoPadrao.dominioEvento <> 3 " + sqlComplementar);
        query.setParameter("cpfCnpj", cliente.getCpfCnpj());

        if (cliente.getId() != null) {
            query.setParameter("id", cliente.getId());
        }

        List<Cliente> lista = query.getResultList();

        return !lista.isEmpty();

    }

    @Override
    public String getWhere(FiltroParametro filtroParametro) {
        String consultaSQL = " 1 = 1";

        if (filtroParametro.getFilter("codigo") != null && !StringUtils.isEmpty(filtroParametro.getFilter("codigo").getValue())) {
            String valor = filtroParametro.getFilter("codigo").getValue();
            consultaSQL += " and o.id = " + valor + "";
        }

        if (filtroParametro.getFilter("nomeCliente") != null) {
            String valor = filtroParametro.getFilter("nomeCliente").getValue();
            consultaSQL += " and (o.razaoSocial  LIKE '%" + valor + "%' or "
                    + "o.nomeFantasia LIKE '%" + valor + "%' )";
        }

        if (filtroParametro.getFilter("tipoPessoa") != null) {
            String valor = filtroParametro.getFilter("tipoPessoa").getValue();
            consultaSQL += " and o.tipoCliente = '" + valor + "'";
        }

        return consultaSQL;
    }

    @Override
    public Cliente create(Cliente obj) throws Exception {
        validar(obj);
        return super.create(obj);
    }

    @Override
    public Cliente update(Cliente obj) throws Exception {
        validar(obj);
        return super.update(obj);
    }

    public void validar(Cliente cliente) throws Exception {
        if (existeCpfCnpj(cliente)) {
            addErro("CPF/CNPJ cadastrado anteriormente!");
        }
        if(cliente.getTipoCliente()==DominioTipoPessoa.JURIDICA){
            if(StringUtils.isEmpty(cliente.getIeSt())){
                cliente.setIeSt(null);
            }
            if(StringUtils.isEmpty(cliente.getIm())){
                cliente.setIm(null);
            }
            if(StringUtils.isEmpty(cliente.getSuframa())){
                cliente.setSuframa(null);
            }
        }
        
    }

    @Override
    public List<Cliente> findRange(String complementoConsulta, int apartirDe, int quantidade, String ordernacao) {
        List<Cliente> result = super.findRange(complementoConsulta, apartirDe, quantidade, ordernacao); //To change body of generated methods, choose Tools | Templates.

        if (!result.isEmpty()) {
            result.forEach((c) -> {
                if(c.getAtivo().equals(true)){
                    c.setSituacao("Ativo");
                } else {
                    c.setSituacao("Inativo");
                }
            });
        }

        return result;
    }

    @Override
    public List<Cliente> findRange(FiltroParametro filtro) {
        List<Cliente> result =  super.findRange(filtro); //To change body of generated methods, choose Tools | Templates.
        
        if (!result.isEmpty()) {
            result.forEach((c) -> {
                if(c.getAtivo().equals(true)){
                    c.setSituacao("Ativo");
                } else {
                    c.setSituacao("Inativo");
                }
            });
        }
        
        return result;
    }
    
    
}
