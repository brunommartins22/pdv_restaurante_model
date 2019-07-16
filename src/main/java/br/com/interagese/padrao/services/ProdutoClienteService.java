/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.padrao.rest.util.TransformNativeQuery;
import br.com.interagese.syscontabil.domains.DominioRegras;
import br.com.interagese.syscontabil.domains.DominioValidacaoProduto;
import br.com.interagese.syscontabil.models.Cliente;
import br.com.interagese.syscontabil.models.ProdutoCliente;
import br.com.interagese.syscontabil.models.RegraNcm;
import br.com.interagese.syscontabil.models.RegraProduto;
import br.com.interagese.syscontabil.models.RegraRegimeTributario;
import br.com.interagese.syscontabil.models.TributoEstadual;
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
    private RegraRegimeService regraRegimeTributarioService;
    @Autowired
    private RegraNcmService regraNcmService;

    public List<ClienteProdutoTemp> loadProductClient() throws Exception {

        String sql = "SELECT "
                + "  c.id AS CODIGO,"
                + "  c.tipo_regime as REGIME,"
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
        temp.setRegime(cliente.getTipoRegime().getDescricao());
        temp.setCpfCnpj(cliente.getCpfCnpj());
        temp.setNomeCliente(cliente.getRazaoSocial());
        List<ProdutoCliente> listProductClient = loadProductClientById(new BigInteger(cliente.getId().toString()));

        for (ProdutoCliente produtoCliente : listProductClient) {
            TributoFederal tributoFederalPadrao = null;
            TributoEstadual tributoEstadualPadrao = null;

            //******************************************************************
            String sql = "Select o.ncm,o.cest from ProdutoGeral o where o.ean ='" + produtoCliente.getEan() + "'";

            Object[] o = em.createQuery(sql, Object[].class).getSingleResult();
            String ncmPadrao = (String) o[0];
            String cestPadrao = (String) o[1];

            //************************ Rule Product ****************************
            RegraProduto regraProduto = regraProdutoService.loadRegraProduto(new BigInteger(cliente.getId().toString()), produtoCliente.getEan());

            if (regraProduto != null) {
                tributoFederalPadrao = regraProduto.getTributoFederal();
                tributoEstadualPadrao = regraProduto.getTributoEstadual();
                produtoCliente.setRegras(DominioRegras.PRODUTO);

            } else {

                if (produtoCliente.getNcmCliente() != null) {
                    //************************ Rule Ncm ****************************

                    RegraNcm regraNcm = regraNcmService.loadRegraNcm(produtoCliente.getNcmCliente(), cliente.getTipoRegime().getDescricao());

                    if (regraNcm != null) {
                        tributoFederalPadrao = regraNcm.getTributoFederal();
                        tributoEstadualPadrao = regraNcm.getTributoEstadual();
                        produtoCliente.setRegras(DominioRegras.NCM);

                    } else {

                        if (cliente.getTipoRegime() != null) {
                            //************************ Rule Regime Tributário ****************************

                            RegraRegimeTributario regimeTributario = regraRegimeTributarioService.loadRegraRegimeTributario(cliente.getTipoRegime().getDescricao());

                            if (regimeTributario != null) {
                                tributoFederalPadrao = regimeTributario.getTributoFederal();
                                tributoEstadualPadrao = regimeTributario.getTributoEstadual();
                                produtoCliente.setRegras(DominioRegras.REGIME);
                            }

                        }

                    }

                }

            }

            //*************** insert data in productClient *********************
            produtoCliente.setNcmPadrao(ncmPadrao == null || ncmPadrao.isEmpty() ? produtoCliente.getNcmPadrao() : ncmPadrao);
            produtoCliente.setCestPadrao(cestPadrao == null || cestPadrao.isEmpty() ? produtoCliente.getCestPadrao() : cestPadrao);
            produtoCliente.setTributoFederal(tributoFederalPadrao == null ? produtoCliente.getTributoFederal() : tributoFederalPadrao);
            produtoCliente.setTributoEstadual(tributoEstadualPadrao == null ? produtoCliente.getTributoEstadual() : tributoEstadualPadrao);
        }

        //******** insert update list for new result ListProductClient *********
        temp.setResultProdutoCliente(listProductClient);

        return temp;
    }

    public void confirmarRoleClient(ProdutoCliente produtoCliente) throws Exception {
        if (produtoCliente != null) {
            switch (produtoCliente.getRegras()) {
                case PRODUTO: {

                    break;
                }
                case NCM: {

                    break;
                }
                case REGIME: {

                    break;
                }
            }

            produtoCliente.setNcmCliente(produtoCliente.getNcmPadrao());
            produtoCliente.setCestCliente(produtoCliente.getCestPadrao());

            if (produtoCliente.getTributoEstadualCliente() != null) {
                //****************************** ICMS **************************
                //****************************** Tributos de Entrada **********************************
                produtoCliente.getTributoEstadualCliente().setCstIcmsEntradaCliente(produtoCliente.getTributoEstadualCliente().getCstIcmsEntradaCliente());
                produtoCliente.getTributoEstadualCliente().setAliquotaIcmsEntradaCliente(produtoCliente.getTributoEstadual().getAliquotaIcmsEntrada());
                produtoCliente.getTributoEstadualCliente().setAliquotaIcmsEntradaSTCliente(produtoCliente.getTributoEstadual().getAliquotaIcmsEntradaST());
                produtoCliente.getTributoEstadualCliente().setReducaoBaseCalculoIcmsEntradaCliente(produtoCliente.getTributoEstadual().getReducaoBaseCalculoIcmsEntrada());
                produtoCliente.getTributoEstadualCliente().setReducaoBaseCalculoIcmsEntradaSTCliente(produtoCliente.getTributoEstadual().getReducaoBaseCalculoIcmsEntradaST());
                //****************************** Tributos de Saída ************************************
                produtoCliente.getTributoEstadualCliente().setCstIcmsSaidaCliente(produtoCliente.getTributoEstadual().getCstIcmsSaida());
                produtoCliente.getTributoEstadualCliente().setAliquotaIcmsSaidaCliente(produtoCliente.getTributoEstadual().getAliquotaIcmsSaida());
                produtoCliente.getTributoEstadualCliente().setAliquotaIcmsSaidaSTCliente(produtoCliente.getTributoEstadual().getAliquotaIcmsSaidaST());
                produtoCliente.getTributoEstadualCliente().setReducaoBaseCalculoIcmsSaidaCliente(produtoCliente.getTributoEstadual().getReducaoBaseCalculoIcmsSaida());
                produtoCliente.getTributoEstadualCliente().setReducaoBaseCalculoIcmsSaidaSTCliente(produtoCliente.getTributoEstadual().getReducaoBaseCalculoIcmsSaidaST());
            }
            if (produtoCliente.getTributoFederalCliente() != null) {
                //************************* Pis ********************************
                //****************************** Tributos de Entrada **********************************
                produtoCliente.getTributoFederalCliente().setCstPisEntradaCliente(produtoCliente.getTributoFederal().getCstPisEntrada());
                produtoCliente.getTributoFederalCliente().setAliquotaPisEntradaCliente(produtoCliente.getTributoFederal().getAliquotaPisEntrada());
                //****************************** Tributos de Saída ************************************
                produtoCliente.getTributoFederalCliente().setCstPisSaidaCliente(produtoCliente.getTributoFederal().getCstPisSaida());
                produtoCliente.getTributoFederalCliente().setAliquotaPisSaidaCliente(produtoCliente.getTributoFederal().getAliquotaPisSaida());
                //************************* Cofins *****************************
                //****************************** Tributos de Entrada **********************************
                produtoCliente.getTributoFederalCliente().setCstCofinsEntradaCliente(produtoCliente.getTributoFederal().getCstCofinsEntrada());
                produtoCliente.getTributoFederalCliente().setAliquotaCofinsEntradaCliente(produtoCliente.getTributoFederal().getAliquotaCofinsEntrada());
                //****************************** Tributos de Saída ************************************
                produtoCliente.getTributoFederalCliente().setCstCofinsSaidaCliente(produtoCliente.getTributoFederal().getCstCofinsSaida());
                produtoCliente.getTributoFederalCliente().setAliquotaCofinsSaidaCliente(produtoCliente.getTributoFederal().getAliquotaCofinsSaida());
                //**************************** Ipi *****************************
                //****************************** Tributos de Entrada **********************************
                produtoCliente.getTributoFederalCliente().setCstIpiEntradaCliente(produtoCliente.getTributoFederal().getCstIpiEntrada());
                produtoCliente.getTributoFederalCliente().setAliquotaIpiEntradaCliente(produtoCliente.getTributoFederal().getAliquotaIpiEntrada());
                //****************************** Tributos de Saída ************************************
                produtoCliente.getTributoFederalCliente().setCstIpiSaidaCliente(produtoCliente.getTributoFederal().getCstIpiSaida());
                produtoCliente.getTributoFederalCliente().setAliquotaIpiSaidaCliente(produtoCliente.getTributoFederal().getAliquotaIpiSaida());
            }
            
            produtoCliente.setDominioValidacaoProduto(DominioValidacaoProduto.VALIDADO);

        }
    }

    @Override
    public ProdutoCliente update(ProdutoCliente obj) throws Exception {
        confirmarRoleClient(obj);
        return super.update(obj);
    }

}
