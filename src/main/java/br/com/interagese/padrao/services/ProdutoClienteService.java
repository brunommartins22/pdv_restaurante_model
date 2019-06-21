/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.padrao.rest.util.TransformNativeQuery;
import br.com.interagese.syscontabil.models.Cliente;
import br.com.interagese.syscontabil.models.ProdutoCliente;
import br.com.interagese.syscontabil.models.RegraProduto;
import br.com.interagese.syscontabil.models.TributoFederal;
import br.com.interagese.syscontabil.temp.ClienteProdutoTemp;
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
public class ProdutoClienteService extends PadraoService<ProdutoCliente> {

    @Autowired
    private RegraProdutoService regraProdutoService;
    @Autowired
    private RegraRegimeTributarioService regraRegimeTributarioService;

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

    public List<ProdutoCliente> loadProductClientById(BigInteger clienteId) {
        if (clienteId != null) {
            String sql = "select o from ProdutoCliente o where o.clienteId = '" + clienteId + "' order by o.nomeProduto";
            TypedQuery<ProdutoCliente> query = em.createQuery(sql, ProdutoCliente.class);

            return query.getResultList();

        }
        return null;
    }

    public ClienteProdutoTemp loadRuleProductClient(Cliente cliente) {

        ClienteProdutoTemp temp = new ClienteProdutoTemp();
        temp.setClienteId(new BigInteger(cliente.getId().toString()));
        temp.setCpfCnpj(cliente.getCpfCnpj());
        temp.setNomeCliente(cliente.getRazaoSocial());
        List<ProdutoCliente> listProductClient = loadProductClientById(new BigInteger(cliente.getId().toString()));

        for (ProdutoCliente produtoCliente : listProductClient) {
            String ncmPadrao = null;
            String cestPadrao = null;
            TributoFederal tributoFederalPadrao = null;
            //************************ Rule product ****************************
            RegraProduto regraProduto = regraProdutoService.loadRegraProduto(new BigInteger(cliente.getId().toString()), produtoCliente.getEan());

            if (regraProduto != null) {

                String sql = "Select o.ncm,o.cest from ProdutoGeral o where o.ean ='" + produtoCliente.getEan() + "'";

                Object[] o = em.createQuery(sql, Object[].class).getSingleResult();
                ncmPadrao = (String) o[0];
                cestPadrao = (String) o[1];

                tributoFederalPadrao = regraProduto.getTributoFederal();
            } else {

//                RegraRegimeTributario regraRegimeTributario = 
            }

            //*************** insert data in productClient *********************
            produtoCliente.setNcmPadrao(ncmPadrao == null || ncmPadrao.isEmpty() ? produtoCliente.getNcmPadrao() : ncmPadrao);
            produtoCliente.setCestPadrao(cestPadrao == null || cestPadrao.isEmpty() ? produtoCliente.getCestPadrao() : cestPadrao);
            produtoCliente.setTributoFederal(tributoFederalPadrao == null ? produtoCliente.getTributoFederal() : tributoFederalPadrao);
        }

        //******** insert update list for new result ListProductClient *********
        temp.setResultProdutoCliente(listProductClient);

        return temp;
    }
}
