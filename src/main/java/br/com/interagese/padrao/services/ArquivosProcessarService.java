/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.util.FiltroParametro;
import br.com.interagese.padrao.rest.util.FiltroParametroItem;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.domains.DominioStatusArquivo;
import br.com.interagese.syscontabil.models.ArquivosProcessar;
import br.com.interagese.syscontabil.models.fileProcessed.Produto;
import br.com.interagese.syscontabil.models.fileProcessed.ProdutoCenarioJob;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author joao
 */
@Service

public class ArquivosProcessarService extends PadraoService<ArquivosProcessar> {

    @Autowired
    private ProdutoClienteService produtoClienteService;

    @Override
    public String getWhere(String complementoConsulta) {
        String consultaSQL = "";

        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {
            if (Utils.somenteNumeros(complementoConsulta)) {
                consultaSQL = "o.id = '" + complementoConsulta;
            } else {
                consultaSQL = "o.statusArquivo = '" + complementoConsulta + "' ";
            }
        }

        if (!consultaSQL.contains("statusArquivo")) {
            if (!consultaSQL.trim().equals("")) {
                consultaSQL += " and";
            }
            consultaSQL += " o.statusArquivo <> 'FINALIZADO' ";
        }

        return consultaSQL;
    }

    @Override
    public List<ArquivosProcessar> findRange(String complementoConsulta, int apartirDe, int quantidade, String ordernacao) {
        List<ArquivosProcessar> result = super.findRange(complementoConsulta, apartirDe, quantidade, ordernacao); //To change body of generated methods, choose Tools | Templates.

        if (!result.isEmpty()) {
            for (ArquivosProcessar ap : result) {
                ap.setDescricaoStatus(ap.getStatusArquivo().getDescricao());

                if (ap.getStatusArquivo() == DominioStatusArquivo.EMANDAMENTO) {
                    ap.setPercentualProcessado((ap.getNumeroRegistrosProcessados() * 100.0) / ap.getNumeroRegistros());
                }

                if (ap.getDataFimProcesso() != null && ap.getDataInicioProcesso() != null) {
                    Long duracao = ap.getDataFimProcesso().getTime() - ap.getDataInicioProcesso().getTime();
                    ap.setDuracaoProcesso(((duracao.doubleValue()) / 1000.00));
                }
            }
        }

        return result;
    }

    @Override
    public String getWhere(FiltroParametro filtroParametro) {
        String consultaSQL = "";

//        FiltroParametro filtroParametro = (FiltroParametro) filtros;
        if (!filtroParametro.getItens().isEmpty()) {
            for (FiltroParametroItem i : filtroParametro.getItens()) {
                if (i.getField().equals("clienteId")) {
                    consultaSQL = " o.cliente.id = '" + i.getValue() + "' ";
                }
            }
        }

        return consultaSQL;
    }

    @Override
    public List<ArquivosProcessar> findRange(FiltroParametro filtro) {
        List<ArquivosProcessar> result = super.findRange(filtro); //To change body of generated methods, choose Tools | Templates.

        if (!result.isEmpty()) {
            for (ArquivosProcessar ap : result) {
                ap.setDescricaoStatus(ap.getStatusArquivo().getDescricao());

                if (ap.getStatusArquivo() == DominioStatusArquivo.EMANDAMENTO) {
                    ap.setPercentualProcessado((ap.getNumeroRegistrosProcessados() * 100.0) / ap.getNumeroRegistros());
                }

                if (ap.getDataFimProcesso() != null && ap.getDataInicioProcesso() != null) {
                    Long duracao = ap.getDataFimProcesso().getTime() - ap.getDataInicioProcesso().getTime();
                    ap.setDuracaoProcesso(((duracao.doubleValue()) / 1000.00));
                }
            }
        }

        return result;
    }

    //************************* syscotabil_job *********************************
    //************************* Execute Job ************************************
    //" segundo min hora dia mes dia-da-semana "
    protected ArquivosProcessar arquivoSelecionado = null;

    @Scheduled(cron = "5 * * * * *")
    public void loadProcess() {

        try {
            System.out.println("Verificando Arquivos na Base de Dados ...");
            arquivoSelecionado = getFind("PARALIZADO");
            if (arquivoSelecionado != null) {
                loadFile(arquivoSelecionado);
            }

            arquivoSelecionado = getFind("PENDENTE");
            if (arquivoSelecionado != null) {
                loadFile(arquivoSelecionado);
            } else {
                System.out.println("Nenhum Arquivo Encontrado na Base de Dados !!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            em.createNativeQuery("UPDATE syscontabil.arquivos_processar set status_arquivo ='PARALIZADO',erros='" + ex.getMessage() + "' where id = " + arquivoSelecionado.getId());
            arquivoSelecionado = null;
        }

    }

    private void loadFile(ArquivosProcessar arquivoSelecionado) throws Exception {

        //********************** modificar status file *************************
        em.createNativeQuery("update syscontabil.arquivos_processar set status_arquivo ='EMANDAMENTO',data_inicio_processo = now() where id ='" + arquivoSelecionado.getId() + "'").executeUpdate();

        //********************** load Produtos *********************************
        List<Produto> produtos = produtoClienteService.loadProdutos(arquivoSelecionado);

        if (produtos.isEmpty()) {
            throw new Exception("Arquivo informado sem registros para validação !!");
        }

        insertProdutosInDataBase(produtos, arquivoSelecionado.getCliente().getId(), arquivoSelecionado.getId());

    }

    private void insertProdutosInDataBase(List<Produto> produtos, Long clienteId, Long arquivoId) throws Exception {
        int cont = 0;
        Long contArquivo = 0L;
        String sql = "";
        List<String> resp = new ArrayList<>();
        if (!produtos.isEmpty()) {

            Long idProduto = getUltimoId("syscontabil.seq_produto_cliente").longValue();
            Long idCenario = getUltimoId("syscontabil.seq_produto_cenario").longValue();

            List<Long> resultCenarioId = getCenariosId(clienteId);

            if (!resultCenarioId.isEmpty()) {
                int max = 100;
                int size = produtos.size();
                int calc = size / max;
                if ((size % max) > 0) {
                    calc++;
                }
                int inserts = 0;

                for (int p = 0; p < calc; p++) {
                    for (int e = inserts; e < (inserts + max); e++) {
                        if (e >= size) {
                            break;
                        }
                        cont++;
                        Produto produtoJob = produtos.get(e);

                        System.out.println("posição(produto) = " + cont + " registro(s)");

                        Produto produtoValidado = produtoClienteService.loadValidacaoProduto(clienteId, produtoJob.getCodigoProduto(), produtoJob.getEan());

                        if (produtoValidado == null) {
                            produtoJob.setId(idProduto);

                            sql = "insert into syscontabil.produto_cliente(id,rgdata,rgevento,rgcodusu,rgusuario,cest_cliente,cest_padrao,cest_informado,codigo_produto,ean,ncm_cliente,ncm_padrao,ncm_informado,nome_produto,cliente_id)"
                                    + "values(" + produtoJob.getId() + ","
                                    + "now(),"
                                    + "'1',"
                                    + "" + produtoJob.getAtributoPadrao().getIdUsuario() + ","
                                    + "" + (produtoJob.getAtributoPadrao().getNmUsuario() != null ? "'" + produtoJob.getAtributoPadrao().getNmUsuario() + "'" : null) + ","
                                    + "" + (produtoJob.getCest() != null ? "'" + produtoJob.getCest() + "'" : null) + ","
                                    + "" + (produtoJob.getCestPadrao() != null ? "'" + produtoJob.getCestPadrao() + "'" : null) + ","
                                    + "" + (produtoJob.getCestInformado() != null ? "'" + produtoJob.getCestInformado() + "'" : null) + ","
                                    + "" + (produtoJob.getCodigoProduto() != null ? "'" + produtoJob.getCodigoProduto() + "'" : null) + ","
                                    + "" + produtoJob.getEan() + ","
                                    + "" + (produtoJob.getNcm() != null ? "'" + produtoJob.getNcm() + "'" : null) + ","
                                    + "" + (produtoJob.getNcmPadrao() != null ? "'" + produtoJob.getNcmPadrao() + "'" : null) + ","
                                    + "" + (produtoJob.getNcmInformado() != null ? "'" + produtoJob.getNcmInformado() + "'" : null) + ","
                                    + "" + (produtoJob.getNmProduto() != null ? "'" + produtoJob.getNmProduto() + "'" : null) + ","
                                    + "" + produtoJob.getClienteId() + ")";

                            resp.add(sql);

                            for (ProdutoCenarioJob produtoCenarioJob : produtoJob.getListaProdutoCenario()) {

                                if (produtoCenarioJob.getIdCenario() == null) {

                                    for (int i = 0; i < resultCenarioId.size(); i++) {
                                        produtoCenarioJob.setId(idCenario);
                                        produtoCenarioJob.setIdProduto(idProduto);
                                        sql = "insert into syscontabil.produto_cenario(id,rgdata,rgevento,rgcodusu,rgusuario,divergente,confirmado,dominio_regras,regra_id,cenario_id,produto_cliente_id"
                                                + ",cst_icms_saida_cliente,aliquota_icms_Saida_cliente"
                                                + ",cst_pis_saida_cliente,aliquota_pis_saida_cliente"
                                                + ",cst_cofins_saida_cliente,aliquota_cofins_saida_cliente"
                                                + ",cst_ipi_saida_cliente,aliquota_ipi_saida_cliente"
                                                + ",cst_icms_saida_padrao,aliquota_icms_saida_padrao"
                                                + ",cst_pis_saida_padrao,aliquota_pis_saida_padrao"
                                                + ",cst_cofins_saida_padrao,aliquota_cofins_saida_padrao"
                                                + ",cst_ipi_saida_padrao,aliquota_ipi_saida_padrao"
                                                + ",cst_icms_saida_informado,aliquota_icms_saida_informado"
                                                + ",cst_pis_saida_informado,aliquota_pis_saida_informado"
                                                + ",cst_cofins_saida_informado,aliquota_cofins_saida_informado"
                                                + ",cst_ipi_saida_informado,aliquota_ipi_saida_informado)"
                                                + "values(" + produtoCenarioJob.getId() + ","
                                                + "now(),"
                                                + "'1',"
                                                + "" + produtoCenarioJob.getAtributoPadrao().getIdUsuario() + ","
                                                + "" + (produtoCenarioJob.getAtributoPadrao().getNmUsuario() != null ? "'" + produtoCenarioJob.getAtributoPadrao().getNmUsuario() + "'" : null) + ","
                                                + "'true',"
                                                + "'false',"
                                                + "" + ((produtoCenarioJob.getNmRegra() != null && !produtoCenarioJob.getNmRegra().isEmpty()) ? "'" + produtoCenarioJob.getNmRegra() + "'" : null) + ","
                                                + "" + produtoCenarioJob.getIdRegra() + ","
                                                + "" + resultCenarioId.get(i) + ","
                                                + "" + produtoCenarioJob.getIdProduto() + ","
                                                + "'" + produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() + "',"
                                                + "" + produtoCenarioJob.getTributoEstadualCliente().getAliquotaIcmsSaidaCliente() + ","
                                                + "'" + produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() + "',"
                                                + "" + produtoCenarioJob.getTributoFederalCliente().getAliquotaPisSaidaCliente() + ","
                                                + "'" + produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() + "',"
                                                + "" + produtoCenarioJob.getTributoFederalCliente().getAliquotaCofinsSaidaCliente() + ","
                                                + "'" + produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() + "',"
                                                + "" + produtoCenarioJob.getTributoFederalCliente().getAliquotaIpiSaidaCliente() + ","
                                                + "'" + produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao() + "',"
                                                + "" + produtoCenarioJob.getTributoEstadualPadrao().getAliquotaIcmsSaidaPadrao() + ","
                                                + "'" + produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao() + "',"
                                                + "" + produtoCenarioJob.getTributoFederalPadrao().getAliquotaPisSaidaPadrao() + ","
                                                + "'" + produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao() + "',"
                                                + "" + produtoCenarioJob.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao() + ","
                                                + "'" + produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao() + "',"
                                                + "" + produtoCenarioJob.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao() + ","
                                                + "'" + produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() + "',"
                                                + "" + produtoCenarioJob.getTributoEstadualCliente().getAliquotaIcmsSaidaCliente() + ","
                                                + "'" + produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() + "',"
                                                + "" + produtoCenarioJob.getTributoFederalCliente().getAliquotaPisSaidaCliente() + ","
                                                + "'" + produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() + "',"
                                                + "" + produtoCenarioJob.getTributoFederalCliente().getAliquotaCofinsSaidaCliente() + ","
                                                + "'" + produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() + "',"
                                                + "" + produtoCenarioJob.getTributoFederalCliente().getAliquotaIpiSaidaCliente() + ")";

                                        resp.add(sql);
                                        idCenario++;
                                    }
                                } else {
                                    produtoCenarioJob.setId(idCenario);
                                    produtoCenarioJob.setIdProduto(idProduto);
                                    sql = "insert into syscontabil.produto_cenario(id,rgdata,rgevento,rgcodusu,rgusuario,divergente,confirmado,dominio_regras,regra_id,cenario_id,produto_cliente_id"
                                            + ",cst_icms_saida_cliente,aliquota_icms_Saida_cliente"
                                            + ",cst_pis_saida_cliente,aliquota_pis_saida_cliente"
                                            + ",cst_cofins_saida_cliente,aliquota_cofins_saida_cliente"
                                            + ",cst_ipi_saida_cliente,aliquota_ipi_saida_cliente"
                                            + ",cst_icms_saida_padrao,aliquota_icms_saida_padrao"
                                            + ",cst_pis_saida_padrao,aliquota_pis_saida_padrao"
                                            + ",cst_cofins_saida_padrao,aliquota_cofins_saida_padrao"
                                            + ",cst_ipi_saida_padrao,aliquota_ipi_saida_padrao"
                                            + ",cst_icms_saida_informado,aliquota_icms_saida_informado"
                                            + ",cst_pis_saida_informado,aliquota_pis_saida_informado"
                                            + ",cst_cofins_saida_informado,aliquota_cofins_saida_informado"
                                            + ",cst_ipi_saida_informado,aliquota_ipi_saida_informado)"
                                            + "values(" + produtoCenarioJob.getId() + ","
                                            + "now(),"
                                            + "'1',"
                                            + "" + produtoCenarioJob.getAtributoPadrao().getIdUsuario() + ","
                                            + "" + (produtoCenarioJob.getAtributoPadrao().getNmUsuario() != null ? "'" + produtoCenarioJob.getAtributoPadrao().getNmUsuario() + "'" : null) + ","
                                            + "'true',"
                                            + "'false',"
                                            + "" + ((produtoCenarioJob.getNmRegra() != null && !produtoCenarioJob.getNmRegra().isEmpty()) ? "'" + produtoCenarioJob.getNmRegra() + "'" : null) + ","
                                            + "" + produtoCenarioJob.getIdRegra() + ","
                                            + "" + produtoCenarioJob.getIdCenario() + ","
                                            + "" + produtoCenarioJob.getIdProduto() + ","
                                            + "'" + produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() + "',"
                                            + "" + produtoCenarioJob.getTributoEstadualCliente().getAliquotaIcmsSaidaCliente() + ","
                                            + "'" + produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() + "',"
                                            + "" + produtoCenarioJob.getTributoFederalCliente().getAliquotaPisSaidaCliente() + ","
                                            + "'" + produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() + "',"
                                            + "" + produtoCenarioJob.getTributoFederalCliente().getAliquotaCofinsSaidaCliente() + ","
                                            + "'" + produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() + "',"
                                            + "" + produtoCenarioJob.getTributoFederalCliente().getAliquotaIpiSaidaCliente() + ","
                                            + "'" + produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao() + "',"
                                            + "" + produtoCenarioJob.getTributoEstadualPadrao().getAliquotaIcmsSaidaPadrao() + ","
                                            + "'" + produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao() + "',"
                                            + "" + produtoCenarioJob.getTributoFederalPadrao().getAliquotaPisSaidaPadrao() + ","
                                            + "'" + produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao() + "',"
                                            + "" + produtoCenarioJob.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao() + ","
                                            + "'" + produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao() + "',"
                                            + "" + produtoCenarioJob.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao() + ","
                                            + "'" + produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() + "',"
                                            + "" + produtoCenarioJob.getTributoEstadualCliente().getAliquotaIcmsSaidaCliente() + ","
                                            + "'" + produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() + "',"
                                            + "" + produtoCenarioJob.getTributoFederalCliente().getAliquotaPisSaidaCliente() + ","
                                            + "'" + produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() + "',"
                                            + "" + produtoCenarioJob.getTributoFederalCliente().getAliquotaCofinsSaidaCliente() + ","
                                            + "'" + produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() + "',"
                                            + "" + produtoCenarioJob.getTributoFederalCliente().getAliquotaIpiSaidaCliente() + ")";

                                    resp.add(sql);
                                    idCenario++;
                                }
                                //************************ registers processed *************************
                                em.createNativeQuery("update syscontabil.arquivos_processar set numero_registros_processados =" + (contArquivo++) + " where id =" + arquivoId).executeUpdate();
                            }
                            //************************ final register processed *************************
                            em.createNativeQuery("update syscontabil.arquivos_processar set ultimo_registro_processado =" + idProduto + " where id =" + arquivoId).executeUpdate();
                            idProduto++;
                        } else {

                        }
                    }
                    inserts = inserts + max;
                    executeUpdate(resp);
                    alterValueSeq("syscontabil.seq_produto_cliente", idProduto);
                    alterValueSeq("syscontabil.seq_produto_cenario", idCenario);
                    resp = new ArrayList<>();
                }

                em.createNativeQuery("update syscontabil.arquivos_processar set status_arquivo ='FINALIZADO',data_fim_processo = now() where status_arquivo='EMANDAMENTO'").executeUpdate();

                System.out.println("Finalizado com sucesso !!");

            }
        } else {
            throw new Exception("Cliente nao possui cenarios !!");
        }
    }

    private ArquivosProcessar getFind(String status) throws Exception {

        String sql = "SELECT * FROM syscontabil.arquivos_processar WHERE status_arquivo = '" + status + "' ORDER BY id LIMIT 1";
        TypedQuery<ArquivosProcessar> result = (TypedQuery<ArquivosProcessar>) em.createNativeQuery(sql, ArquivosProcessar.class);

        return result.getResultList().isEmpty() ? null : result.getSingleResult();

    }

    public List<Long> getCenariosId(Long clienteId) throws Exception {

        List<Long> result = em.createNativeQuery("select cenarios_id as id from syscontabil.cliente_cenarios where cliente_id=" + clienteId).getResultList();

        return result;
    }

}
