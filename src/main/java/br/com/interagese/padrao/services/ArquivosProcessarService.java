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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.LongType;
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

    private void insertProdutosInDataBase(List<Produto> produtos, Long clienteId, Long arquivoId) throws Exception {
        int cont = 0;
        Long contArquivo = 0L;
        String sql = "";
        List<String> resp = new ArrayList<>();
        SQLQuery query = null;

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
                        query = em.unwrap(Session.class).createSQLQuery("insert into syscontabil.produto_cliente(id,rgdata,rgevento,rgcodusu,rgusuario,cest_cliente,cest_padrao,cest_informado,codigo_produto,ean,ncm_cliente,ncm_padrao,ncm_informado,nome_produto,cliente_id,ativo,log)"
                                + "values(:id,now(),'1',:idUsuario,:nomeUsuario,:cest,:cestPadrao,:cestInformado,:codigoProduto,:ean,:ncm,:ncmPadrao,:ncmInformado,:nomeProduto,:idCliente,:ativo,:log)");

                        if (produtoValidado == null) {
                            produtoJob.setId(idProduto);

                            query.setParameter("id", produtoJob.getId());
                            query.setParameter("idUsuario", produtoJob.getAtributoPadrao().getIdUsuario());
                            query.setParameter("nomeUsuario", produtoJob.getAtributoPadrao().getNmUsuario());
                            query.setParameter("cest", produtoJob.getCest() != null && !produtoJob.getCest().equalsIgnoreCase("null") ? produtoJob.getCest().trim() : null);
                            query.setParameter("cestPadrao", produtoJob.getCestPadrao());
                            query.setParameter("cestInformado", produtoJob.getCestInformado() != null && !produtoJob.getCestInformado().equalsIgnoreCase("null") ? produtoJob.getCestInformado().trim() : null);
                            query.setParameter("codigoProduto", produtoJob.getCodigoProduto() != null && !produtoJob.getCodigoProduto().equalsIgnoreCase("null") ? produtoJob.getCodigoProduto().trim() : null);
                            System.out.println("ean = " + produtoJob.getEan());
                            query.setParameter("ean", produtoJob.getEan(), LongType.INSTANCE);
                            query.setParameter("ncm", produtoJob.getNcm() != null && !produtoJob.getNcm().equalsIgnoreCase("null") ? produtoJob.getNcm().trim() : null);
                            query.setParameter("ncmPadrao", produtoJob.getNcmPadrao().trim());
                            query.setParameter("ncmInformado", produtoJob.getNcmInformado() != null && !produtoJob.getNcmInformado().equalsIgnoreCase("null") ? produtoJob.getNcmInformado().trim() : null);
                            query.setParameter("nomeProduto", produtoJob.getNmProduto() != null && !produtoJob.getNmProduto().equalsIgnoreCase("null") ? produtoJob.getNmProduto() : null);
                            query.setParameter("idCliente", produtoJob.getClienteId());
                            query.setParameter("ativo", produtoJob.isAtivo());
                            query.setParameter("log", produtoJob.getLog());

                            query.executeUpdate();

                            for (ProdutoCenarioJob produtoCenarioJob : produtoJob.getListaProdutoCenario()) {

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

                                query = em.unwrap(Session.class).createSQLQuery(sqlColumn + sqlValue);

                                if (produtoCenarioJob.getIdCenario() == null) {

                                    for (int i = 0; i < resultCenarioId.size(); i++) {
                                        produtoCenarioJob.setId(idCenario);
                                        produtoCenarioJob.setIdProduto(idProduto);

                                        query.setParameter("id", produtoCenarioJob.getId());
                                        query.setParameter("idUsuario", produtoCenarioJob.getAtributoPadrao().getIdUsuario());
                                        query.setParameter("nomeUsuario", produtoCenarioJob.getAtributoPadrao().getNmUsuario());
                                        query.setParameter("divergente", true);
                                        query.setParameter("confirmado", false);
                                        query.setParameter("nomeRegra", produtoCenarioJob.getNmRegra());
                                        query.setParameter("idRegra", produtoCenarioJob.getIdRegra());
                                        query.setParameter("idCenario", resultCenarioId.get(i));
                                        query.setParameter("idProdutoCliente", produtoCenarioJob.getIdProduto());
                                        query.setParameter("cstIcmsSaidaCliente", (produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() != null && !produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() : null));
                                        query.setParameter("aliquotaIcmsSaidaCliente", produtoCenarioJob.getTributoEstadualCliente().getAliquotaIcmsSaidaCliente());
                                        query.setParameter("cstPisSaidaCliente", (produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente().equals("null") ? produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() : null));
                                        query.setParameter("aliquotaPisSaidaCliente", produtoCenarioJob.getTributoFederalCliente().getAliquotaPisSaidaCliente());
                                        query.setParameter("cstCofinsSaidaCliente", (produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() : null));
                                        query.setParameter("aliquotaCofinsSaidaCliente", produtoCenarioJob.getTributoFederalCliente().getAliquotaCofinsSaidaCliente());
                                        query.setParameter("cstIpiSaidaCliente", (produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() : null));
                                        query.setParameter("aliquotaIpiSaidaCliente", produtoCenarioJob.getTributoFederalCliente().getAliquotaIpiSaidaCliente());
                                        if (produtoJob.isAtivo()) {
                                            query.setParameter("cstIcmsSaidaPadrao", (produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao() != null && !produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao() : null));
                                            query.setParameter("aliquotaIcmsSaidaPadrao", produtoCenarioJob.getTributoEstadualPadrao().getAliquotaIcmsSaidaPadrao());
                                            query.setParameter("cstPisSaidaPadrao", (produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao() != null && !produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao() : null));
                                            query.setParameter("aliquotaPisSaidaPadrao", produtoCenarioJob.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                                            query.setParameter("cstCofinsSaidaPadrao", (produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao() != null && !produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao() : null));
                                            query.setParameter("aliquotaCofinsSaidaPadrao", produtoCenarioJob.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                                            query.setParameter("cstIpiSaidaPadrao", (produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao() != null && !produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao() : null));
                                            query.setParameter("aliquotaIpiSaidaPadrao", produtoCenarioJob.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
                                        }
                                        query.setParameter("cstIcmsSaidaInformado", (produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() != null && !produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() : null));
                                        query.setParameter("aliquotaIcmsSaidaInformado", produtoCenarioJob.getTributoEstadualCliente().getAliquotaIcmsSaidaCliente());
                                        query.setParameter("cstPisSaidaInformado", (produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente().equals("null") ? produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() : null));
                                        query.setParameter("aliquotaPisSaidaInformado", produtoCenarioJob.getTributoFederalCliente().getAliquotaPisSaidaCliente());
                                        query.setParameter("cstCofinsSaidaInformado", (produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() : null));
                                        query.setParameter("aliquotaCofinsSaidaInformado", produtoCenarioJob.getTributoFederalCliente().getAliquotaCofinsSaidaCliente());
                                        query.setParameter("cstIpiSaidaInformado", (produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() : null));
                                        query.setParameter("aliquotaIpiSaidaInformado", produtoCenarioJob.getTributoFederalCliente().getAliquotaIpiSaidaCliente());

                                        query.executeUpdate();

                                        idCenario++;
                                    }
                                } else {
                                    produtoCenarioJob.setId(idCenario);
                                    produtoCenarioJob.setIdProduto(idProduto);

                                    query.setParameter("id", produtoCenarioJob.getId());
                                    query.setParameter("idUsuario", produtoCenarioJob.getAtributoPadrao().getIdUsuario());
                                    query.setParameter("nomeUsuario", produtoCenarioJob.getAtributoPadrao().getNmUsuario());
                                    query.setParameter("divergente", true);
                                    query.setParameter("confirmado", false);
                                    query.setParameter("nomeRegra", produtoCenarioJob.getNmRegra());
                                    query.setParameter("idRegra", produtoCenarioJob.getIdRegra());
                                    query.setParameter("idCenario", produtoCenarioJob.getIdCenario());
                                    query.setParameter("idProdutoCliente", produtoCenarioJob.getIdProduto());
                                    query.setParameter("cstIcmsSaidaCliente", (produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() != null && !produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() : null));
                                    query.setParameter("aliquotaIcmsSaidaCliente", produtoCenarioJob.getTributoEstadualCliente().getAliquotaIcmsSaidaCliente());
                                    query.setParameter("cstPisSaidaCliente", (produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente().equals("null") ? produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() : null));
                                    query.setParameter("aliquotaPisSaidaCliente", produtoCenarioJob.getTributoFederalCliente().getAliquotaPisSaidaCliente());
                                    query.setParameter("cstCofinsSaidaCliente", (produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() : null));
                                    query.setParameter("aliquotaCofinsSaidaCliente", produtoCenarioJob.getTributoFederalCliente().getAliquotaCofinsSaidaCliente());
                                    query.setParameter("cstIpiSaidaCliente", (produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() : null));
                                    query.setParameter("aliquotaIpiSaidaCliente", produtoCenarioJob.getTributoFederalCliente().getAliquotaIpiSaidaCliente());
                                    if (produtoJob.isAtivo()) {
                                        query.setParameter("cstIcmsSaidaPadrao", (produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao() != null && !produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoEstadualPadrao().getCstIcmsSaidaPadrao() : null));
                                        query.setParameter("aliquotaIcmsSaidaPadrao", produtoCenarioJob.getTributoEstadualPadrao().getAliquotaIcmsSaidaPadrao());
                                        query.setParameter("cstPisSaidaPadrao", (produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao() != null && !produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalPadrao().getCstPisSaidaPadrao() : null));
                                        query.setParameter("aliquotaPisSaidaPadrao", produtoCenarioJob.getTributoFederalPadrao().getAliquotaPisSaidaPadrao());
                                        query.setParameter("cstCofinsSaidaPadrao", (produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao() != null && !produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalPadrao().getCstCofinsSaidaPadrao() : null));
                                        query.setParameter("aliquotaCofinsSaidaPadrao", produtoCenarioJob.getTributoFederalPadrao().getAliquotaCofinsSaidaPadrao());
                                        query.setParameter("cstIpiSaidaPadrao", (produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao() != null && !produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalPadrao().getCstIpiSaidaPadrao() : null));
                                        query.setParameter("aliquotaIpiSaidaPadrao", produtoCenarioJob.getTributoFederalPadrao().getAliquotaIpiSaidaPadrao());
                                    }
                                    query.setParameter("cstIcmsSaidaInformado", (produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() != null && !produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoEstadualCliente().getCstIcmsSaidaCliente() : null));
                                    query.setParameter("aliquotaIcmsSaidaInformado", produtoCenarioJob.getTributoEstadualCliente().getAliquotaIcmsSaidaCliente());
                                    query.setParameter("cstPisSaidaInformado", (produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente().equals("null") ? produtoCenarioJob.getTributoFederalCliente().getCstPisSaidaCliente() : null));
                                    query.setParameter("aliquotaPisSaidaInformado", produtoCenarioJob.getTributoFederalCliente().getAliquotaPisSaidaCliente());
                                    query.setParameter("cstCofinsSaidaInformado", (produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstCofinsSaidaCliente() : null));
                                    query.setParameter("aliquotaCofinsSaidaInformado", produtoCenarioJob.getTributoFederalCliente().getAliquotaCofinsSaidaCliente());
                                    query.setParameter("cstIpiSaidaInformado", (produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() != null && !produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente().equalsIgnoreCase("null") ? produtoCenarioJob.getTributoFederalCliente().getCstIpiSaidaCliente() : null));
                                    query.setParameter("aliquotaIpiSaidaInformado", produtoCenarioJob.getTributoFederalCliente().getAliquotaIpiSaidaCliente());

                                    query.executeUpdate();
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
                    }
                    inserts = inserts + max;

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
