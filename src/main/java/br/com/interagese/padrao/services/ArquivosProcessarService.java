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
import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            String sqlError = "UPDATE syscontabil.arquivos_processar set status_arquivo ='PARALIZADO',erros= :erro where id = :id";
            em.createNativeQuery(sqlError)
                    .setParameter("erro", ex.getMessage())
                    .setParameter("id", arquivoSelecionado.getId())
                    .executeUpdate();
            arquivoSelecionado = null;
        }

    }

    private void loadFile(ArquivosProcessar arquivoSelecionado) throws Exception {

        //********************** modificar status file *************************
        em.createNativeQuery("update syscontabil.arquivos_processar set status_arquivo ='EMANDAMENTO',data_inicio_processo = :dataInicio where id = :id")
                .setParameter("dataInicio", new Date())
                .setParameter("id", arquivoSelecionado.getId())
                .executeUpdate();

        //********************** load Produtos *********************************
        List<Produto> produtos = produtoClienteService.loadProdutos(arquivoSelecionado);

        if (produtos.isEmpty()) {
            throw new Exception("Arquivo informado sem registros para validação !!");
        }

        insertProdutosInDataBase(produtos, arquivoSelecionado.getCliente().getId(), arquivoSelecionado.getId());

    }

    @Transactional
    private void insertProdutosInDataBase(List<Produto> produtos, Long clienteId, Long arquivoId) throws Exception {
        int cont = 1;
        Long contArquivo = 0L;
        SQLQuery queryProdutoCliente = em.unwrap(Session.class).createSQLQuery("insert into syscontabil.produto_cliente(id,rgdata,rgevento,rgcodusu,rgusuario,cest_cliente,cest_padrao,cest_informado,codigo_produto,ean,ncm_cliente,ncm_padrao,ncm_informado,nome_produto,cliente_id,ativo,log)"
                + "values(:id,now(),'1',:idUsuario,:nomeUsuario,:cest,:cestPadrao,:cestInformado,:codigoProduto,:ean,:ncm,:ncmPadrao,:ncmInformado,:nomeProduto,:idCliente,:ativo,:log)");;
        SQLQuery queryProdutoCenario = null;

        if (!produtos.isEmpty()) {

            Long idProduto = getUltimoId("syscontabil.seq_produto_cliente").longValue();
            Long idCenario = getUltimoId("syscontabil.seq_produto_cenario").longValue();

            List<Long> resultCenarioId = getCenariosId(clienteId);

            if (!resultCenarioId.isEmpty()) {

                for (int p = 0; p < produtos.size(); p++) {
                    Produto produtoJob = produtos.get(p);

                    System.out.println("posição(produto) = " + (cont++) + " registro(s)");

                    //******************************************* PRODUTO CLIENTE **********************************************
                    Produto produtoValidado = produtoClienteService.loadValidacaoProduto(clienteId, produtoJob.getCodigoProduto(), produtoJob.getEan());

                    if (produtoValidado == null) {

                        produtoJob.setId(idProduto);

                        queryProdutoCliente.setParameter("id", produtoJob.getId());
                        queryProdutoCliente.setParameter("idUsuario", produtoJob.getAtributoPadrao().getIdUsuario());
                        queryProdutoCliente.setParameter("nomeUsuario", produtoJob.getAtributoPadrao().getNmUsuario());
                        queryProdutoCliente.setParameter("cest", produtoJob.getCest() != null && !produtoJob.getCest().equalsIgnoreCase("null") ? produtoJob.getCest().trim() : null);
                        queryProdutoCliente.setParameter("cestPadrao", produtoJob.getCestPadrao());
                        queryProdutoCliente.setParameter("cestInformado", produtoJob.getCestInformado() != null && !produtoJob.getCestInformado().equalsIgnoreCase("null") ? produtoJob.getCestInformado().trim() : null);
                        queryProdutoCliente.setParameter("codigoProduto", produtoJob.getCodigoProduto() != null && !produtoJob.getCodigoProduto().equalsIgnoreCase("null") ? produtoJob.getCodigoProduto().trim() : null);
                        queryProdutoCliente.setParameter("ean", produtoJob.getEan(), LongType.INSTANCE);
                        queryProdutoCliente.setParameter("ncm", produtoJob.getNcm() != null && !produtoJob.getNcm().equalsIgnoreCase("null") ? produtoJob.getNcm().trim() : null);
                        queryProdutoCliente.setParameter("ncmPadrao", produtoJob.getNcmPadrao().trim());
                        queryProdutoCliente.setParameter("ncmInformado", produtoJob.getNcmInformado() != null && !produtoJob.getNcmInformado().equalsIgnoreCase("null") ? produtoJob.getNcmInformado().trim() : null);
                        queryProdutoCliente.setParameter("nomeProduto", produtoJob.getNmProduto() != null && !produtoJob.getNmProduto().equalsIgnoreCase("null") ? produtoJob.getNmProduto() : null);
                        queryProdutoCliente.setParameter("idCliente", produtoJob.getClienteId());
                        queryProdutoCliente.setParameter("ativo", produtoJob.isAtivo());
                        queryProdutoCliente.setParameter("log", produtoJob.getLog());

                        queryProdutoCliente.executeUpdate();

                        //******************************************* PRODUTO CENARIO **********************************************
                        String sqlColumn = "insert into syscontabil.produto_cenario(id,rgdata,rgevento,rgcodusu,rgusuario,divergente,confirmado,dominio_regras,regra_id,cenario_id,produto_cliente_id"
                                + ",cst_icms_saida_cliente,aliquota_icms_Saida_cliente"
                                + ",cst_pis_saida_cliente,aliquota_pis_saida_cliente"
                                + ",cst_cofins_saida_cliente,aliquota_cofins_saida_cliente"
                                + ",cst_ipi_saida_cliente,aliquota_ipi_saida_cliente"
                                + ",cst_icms_saida_informado,aliquota_icms_saida_informado"
                                + ",cst_pis_saida_informado,aliquota_pis_saida_informado"
                                + ",cst_cofins_saida_informado,aliquota_cofins_saida_informado"
                                + ",cst_ipi_saida_informado,aliquota_ipi_saida_informado";

                        String sqlValue = "values(:id,now(),'1',:idUsuario,:nomeUsuario,:divergente,:confirmado,:nomeRegra,:idRegra,:idCenario,:idProdutoCliente,"
                                + ":cstIcmsSaidaCliente,:aliquotaIcmsSaidaCliente,"
                                + ":cstPisSaidaCliente,:aliquotaPisSaidaCliente,"
                                + ":cstCofinsSaidaCliente,:aliquotaCofinsSaidaCliente,"
                                + ":cstIpiSaidaCliente,:aliquotaIpiSaidaCliente,"
                                + ":cstIcmsSaidaInformado,:aliquotaIcmsSaidaInformado,"
                                + ":cstPisSaidaInformado,:aliquotaPisSaidaInformado,"
                                + ":cstCofinsSaidaInformado,:aliquotaCofinsSaidaInformado,"
                                + ":cstIpiSaidaInformado,:aliquotaIpiSaidaInformado";

                        if (produtoJob.isAtivo()) {
                            sqlColumn += ",cst_icms_saida_padrao,aliquota_icms_saida_padrao"
                                    + ",cst_pis_saida_padrao,aliquota_pis_saida_padrao"
                                    + ",cst_cofins_saida_padrao,aliquota_cofins_saida_padrao"
                                    + ",cst_ipi_saida_padrao,aliquota_ipi_saida_padrao)";

                            sqlValue += ",:cstIcmsSaidaPadrao,:aliquotaIcmsSaidaPadrao,"
                                    + ":cstPisSaidaPadrao,:aliquotaPisSaidaPadrao,"
                                    + ":cstCofinsSaidaPadrao,:aliquotaCofinsSaidaPadrao,"
                                    + ":cstIpiSaidaPadrao,:aliquotaIpiSaidaPadrao)";
                        } else {
                            sqlColumn += ")";
                            sqlValue += ")";
                        }

                        queryProdutoCenario = em.unwrap(Session.class).createSQLQuery(sqlColumn + sqlValue);

                        for (ProdutoCenarioJob produtoCenarioJob : produtoJob.getListaProdutoCenario()) {

                            if (produtoCenarioJob.getIdCenario() == null) {

                                for (int i = 0; i < resultCenarioId.size(); i++) {
                                    produtoCenarioJob.setId(idCenario);
                                    produtoCenarioJob.setIdProduto(idProduto);

                                    queryProdutoCenario.setParameter("id", produtoCenarioJob.getId());
                                    queryProdutoCenario.setParameter("idUsuario", produtoCenarioJob.getAtributoPadrao().getIdUsuario());
                                    queryProdutoCenario.setParameter("nomeUsuario", produtoCenarioJob.getAtributoPadrao().getNmUsuario());
                                    queryProdutoCenario.setParameter("divergente", true);
                                    queryProdutoCenario.setParameter("confirmado", false);
                                    queryProdutoCenario.setParameter("nomeRegra", produtoCenarioJob.getNmRegra());
                                    queryProdutoCenario.setParameter("idRegra", produtoCenarioJob.getIdRegra(), LongType.INSTANCE);
                                    queryProdutoCenario.setParameter("idCenario", resultCenarioId.get(i));
                                    queryProdutoCenario.setParameter("idProdutoCliente", produtoCenarioJob.getIdProduto());
                                    queryProdutoCenario.setParameter("cstIcmsSaidaCliente", (produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() != null && !produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() : null));
                                    queryProdutoCenario.setParameter("aliquotaIcmsSaidaCliente", produtoCenarioJob.getTributoEstadualCliente().getAliquotaIcmsSaidaCliente());
                                    queryProdutoCenario.setParameter("cstPisSaidaCliente", (produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente().equals("null") ? produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() : null));
                                    queryProdutoCenario.setParameter("aliquotaPisSaidaCliente", produtoCenarioJob.getTributoFederalCliente().getAliquotaPisSaidaCliente());
                                    queryProdutoCenario.setParameter("cstCofinsSaidaCliente", (produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() : null));
                                    queryProdutoCenario.setParameter("aliquotaCofinsSaidaCliente", produtoCenarioJob.getTributoFederalCliente().getAliquotaCofinsSaidaCliente());
                                    queryProdutoCenario.setParameter("cstIpiSaidaCliente", (produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() : null));
                                    queryProdutoCenario.setParameter("aliquotaIpiSaidaCliente", produtoCenarioJob.getTributoFederalCliente().getAliquotaIpiSaidaCliente());
                                    if (produtoJob.isAtivo()) {
                                        queryProdutoCenario.setParameter("cstIcmsSaidaPadrao", (produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao() != null && !produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao() : null));
                                        queryProdutoCenario.setParameter("aliquotaIcmsSaidaPadrao", produtoCenarioJob.getTributoEstadualPadrao().getAliquotaIcmsSaidaPadrao());
                                        queryProdutoCenario.setParameter("cstPisSaidaPadrao", (produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao() != null && !produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao() : null));
                                        queryProdutoCenario.setParameter("aliquotaPisSaidaPadrao", produtoCenarioJob.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                                        queryProdutoCenario.setParameter("cstCofinsSaidaPadrao", (produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao() != null && !produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao() : null));
                                        queryProdutoCenario.setParameter("aliquotaCofinsSaidaPadrao", produtoCenarioJob.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                                        queryProdutoCenario.setParameter("cstIpiSaidaPadrao", (produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao() != null && !produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao() : null));
                                        queryProdutoCenario.setParameter("aliquotaIpiSaidaPadrao", produtoCenarioJob.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
                                    }
                                    queryProdutoCenario.setParameter("cstIcmsSaidaInformado", (produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() != null && !produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() : null));
                                    queryProdutoCenario.setParameter("aliquotaIcmsSaidaInformado", produtoCenarioJob.getTributoEstadualCliente().getAliquotaIcmsSaidaCliente());
                                    queryProdutoCenario.setParameter("cstPisSaidaInformado", (produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente().equals("null") ? produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() : null));
                                    queryProdutoCenario.setParameter("aliquotaPisSaidaInformado", produtoCenarioJob.getTributoFederalCliente().getAliquotaPisSaidaCliente());
                                    queryProdutoCenario.setParameter("cstCofinsSaidaInformado", (produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() : null));
                                    queryProdutoCenario.setParameter("aliquotaCofinsSaidaInformado", produtoCenarioJob.getTributoFederalCliente().getAliquotaCofinsSaidaCliente());
                                    queryProdutoCenario.setParameter("cstIpiSaidaInformado", (produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() : null));
                                    queryProdutoCenario.setParameter("aliquotaIpiSaidaInformado", produtoCenarioJob.getTributoFederalCliente().getAliquotaIpiSaidaCliente());
                                    queryProdutoCenario.executeUpdate();

                                    idCenario++;
                                }
                            } else {
                                produtoCenarioJob.setId(idCenario);
                                produtoCenarioJob.setIdProduto(idProduto);

                                queryProdutoCenario.setParameter("id", produtoCenarioJob.getId());
                                queryProdutoCenario.setParameter("idUsuario", produtoCenarioJob.getAtributoPadrao().getIdUsuario());
                                queryProdutoCenario.setParameter("nomeUsuario", produtoCenarioJob.getAtributoPadrao().getNmUsuario());
                                queryProdutoCenario.setParameter("divergente", true);
                                queryProdutoCenario.setParameter("confirmado", false);
                                queryProdutoCenario.setParameter("nomeRegra", produtoCenarioJob.getNmRegra());
                                queryProdutoCenario.setParameter("idRegra", produtoCenarioJob.getIdRegra(), LongType.INSTANCE);
                                queryProdutoCenario.setParameter("idCenario", produtoCenarioJob.getIdCenario());
                                queryProdutoCenario.setParameter("idProdutoCliente", produtoCenarioJob.getIdProduto());
                                queryProdutoCenario.setParameter("cstIcmsSaidaCliente", (produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() != null && !produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() : null));
                                queryProdutoCenario.setParameter("aliquotaIcmsSaidaCliente", produtoCenarioJob.getTributoEstadualCliente().getAliquotaIcmsSaidaCliente());
                                queryProdutoCenario.setParameter("cstPisSaidaCliente", (produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente().equals("null") ? produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() : null));
                                queryProdutoCenario.setParameter("aliquotaPisSaidaCliente", produtoCenarioJob.getTributoFederalCliente().getAliquotaPisSaidaCliente());
                                queryProdutoCenario.setParameter("cstCofinsSaidaCliente", (produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() : null));
                                queryProdutoCenario.setParameter("aliquotaCofinsSaidaCliente", produtoCenarioJob.getTributoFederalCliente().getAliquotaCofinsSaidaCliente());
                                queryProdutoCenario.setParameter("cstIpiSaidaCliente", (produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() : null));
                                queryProdutoCenario.setParameter("aliquotaIpiSaidaCliente", produtoCenarioJob.getTributoFederalCliente().getAliquotaIpiSaidaCliente());
                                if (produtoJob.isAtivo()) {
                                    queryProdutoCenario.setParameter("cstIcmsSaidaPadrao", (produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao() != null && !produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao() : null));
                                    queryProdutoCenario.setParameter("aliquotaIcmsSaidaPadrao", produtoCenarioJob.getTributoEstadualPadrao().getAliquotaIcmsSaidaPadrao());
                                    queryProdutoCenario.setParameter("cstPisSaidaPadrao", (produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao() != null && !produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao() : null));
                                    queryProdutoCenario.setParameter("aliquotaPisSaidaPadrao", produtoCenarioJob.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                                    queryProdutoCenario.setParameter("cstCofinsSaidaPadrao", (produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao() != null && !produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao() : null));
                                    queryProdutoCenario.setParameter("aliquotaCofinsSaidaPadrao", produtoCenarioJob.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                                    queryProdutoCenario.setParameter("cstIpiSaidaPadrao", (produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao() != null && !produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao() : null));
                                    queryProdutoCenario.setParameter("aliquotaIpiSaidaPadrao", produtoCenarioJob.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
                                }
                                queryProdutoCenario.setParameter("cstIcmsSaidaInformado", (produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() != null && !produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() : null));
                                queryProdutoCenario.setParameter("aliquotaIcmsSaidaInformado", produtoCenarioJob.getTributoEstadualCliente().getAliquotaIcmsSaidaCliente());
                                queryProdutoCenario.setParameter("cstPisSaidaInformado", (produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente().equals("null") ? produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() : null));
                                queryProdutoCenario.setParameter("aliquotaPisSaidaInformado", produtoCenarioJob.getTributoFederalCliente().getAliquotaPisSaidaCliente());
                                queryProdutoCenario.setParameter("cstCofinsSaidaInformado", (produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() : null));
                                queryProdutoCenario.setParameter("aliquotaCofinsSaidaInformado", produtoCenarioJob.getTributoFederalCliente().getAliquotaCofinsSaidaCliente());
                                queryProdutoCenario.setParameter("cstIpiSaidaInformado", (produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() : null));
                                queryProdutoCenario.setParameter("aliquotaIpiSaidaInformado", produtoCenarioJob.getTributoFederalCliente().getAliquotaIpiSaidaCliente());

                                queryProdutoCenario.executeUpdate();
                                idCenario++;
                            }
                            //************************ registers processed *************************
                            contArquivo++;
                            em.createNativeQuery("update syscontabil.arquivos_processar set numero_registros_processados =" + contArquivo + " where id =" + arquivoId).executeUpdate();
                        }
                        //************************ final register processed *************************
                        em.createNativeQuery("update syscontabil.arquivos_processar set ultimo_registro_processado =" + idProduto + " where id =" + arquivoId).executeUpdate();
                        idProduto++;
                    } else {

                    }

                    if ((p - 1) == 100 || (p + 1) == produtos.size()) {
                        alterValueSeq("syscontabil.seq_produto_cliente", idProduto);
                        alterValueSeq("syscontabil.seq_produto_cenario", idCenario);
                    }
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
