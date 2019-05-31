/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.padrao.rest.util.TransformNativeQuery;
import br.com.interagese.syscontabil.models.Cliente;
import br.com.interagese.syscontabil.models.ProdutoCliente;
import br.com.interagese.syscontabil.models.RegraProduto;
import br.com.interagese.syscontabil.models.TributoFederal;
import br.com.interagese.syscontabil.temp.ClienteProdutoTemp;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class ProdutoClienteService extends PadraoService<ProdutoCliente> {

    @Autowired
    private RegraProdutoService regraProdutoService;

    public List<ClienteProdutoTemp> loadProductClient() throws Exception {

        String sql = "SELECT "
                + "  c.id AS CODIGO,"
                + "  c.cpf_cnpj AS CPF_CNPJ,"
                + "  c.razao_social AS CLIENTE,"
                + "  (SELECT count(*) from syscontabil.produto_cliente pc WHERE pc.cliente_id = c.id AND pc.status='PENDENTE') AS PENDENTE,"
                + "  (SELECT COUNT(*) FROM syscontabil.produto_cliente PC WHERE PC.cliente_id = C.ID AND pc.status='VALIDADO') AS VALIDADO "
                + "FROM syscontabil.cliente c ";

        List<Object[]> result = em.createNativeQuery(sql).getResultList();

        List<ClienteProdutoTemp> resultClienteProduto = new TransformNativeQuery(ClienteProdutoTemp.class).execute(result);
        resultClienteProduto.forEach((cp) -> {
            cp.setQtdRegistro(cp.getQtdRegistrosPendentes().add(cp.getQtdRegistrosAtualizados()));
        });
        return resultClienteProduto;

    }

    public List<ProdutoCliente> loadProductClientById(Long idCliente) {
        if (idCliente != null) {
            String sql = "select o from ProdutoCliente o where o.clienteId = :clienteid order by o.nomeProduto";
            TypedQuery<ProdutoCliente> query = em.createQuery(sql, ProdutoCliente.class);
            query.setParameter("clienteid", idCliente);

            return query.getResultList();

        }
        return null;
    }

//    public ClienteProdutoTemp loadRuleProductClient(Cliente cliente, List<ProdutoCliente> listProductClient) {
//
//        ClienteProdutoTemp temp = new ClienteProdutoTemp();
//        temp.setCliente(cliente);
//        temp.setSize(new Long(listProductClient.size()));
//
//        listProductClient.forEach((produtoCliente) -> {
//            String ncmPadrao = null;
//            String cestPadrao = null;
//            TributoFederal tributoFederalPadrao = null;
//            //************************ Rule product ****************************
//            RegraProduto regraProduto = regraProdutoService.loadRegraProduto(cliente.getId(), produtoCliente.getEan());
//
//            if (regraProduto != null) {
//
//                if (produtoCliente.getEan() != null) {
//                    String sql = "Select o.ncm,o.cest from ProdutoGeral o where o.ean ='" + produtoCliente.getEan() + "'";
//
//                    Object[] o = em.createQuery(sql, Object[].class).getSingleResult();
//                    ncmPadrao = (String) o[0];
//                    cestPadrao = (String) o[1];
//                }
//
//                tributoFederalPadrao = regraProduto.getTributoFederal();
//            }
//
//            //*************** insert data in productClient *********************
//            produtoCliente.setNcmPadrao(ncmPadrao);
//            produtoCliente.setCestPadrao(cestPadrao);
//            produtoCliente.setTributoFederal(tributoFederalPadrao);
//        });
//        //******** insert update list for new result ListProductClient *********
//        temp.setResultProdutocliente(listProductClient);
//
//        return temp;
//    }
}
