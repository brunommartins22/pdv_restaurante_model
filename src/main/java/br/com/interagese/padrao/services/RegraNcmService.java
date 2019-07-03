/*
 * RegraNcmDtoo change this license header, choose License Headers in Project Properties.
 * RegraNcmDtoo change this template file, choose RegraNcmDtoools | RegraNcmDtoemplates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.erplibrary.AtributoPadrao;
import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.domains.DominioRegime;
import br.com.interagese.syscontabil.dto.RegraNcmDto;
import br.com.interagese.syscontabil.models.RegraNcm;
import br.com.interagese.syscontabil.models.RegraNcmPK;
import br.com.interagese.syscontabil.models.TributoFederal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class RegraNcmService extends PadraoService<RegraNcmDto> {

    public String getWhereDto(String complementoConsulta) {
        return " o.id.codigoNcm like '" + complementoConsulta + "%' ";
    }

    public String getWhereDto(Map filtros) {

        String consulta = "";

        if (filtros.containsKey("campoFiltro") && !filtros.get("campoFiltro").equals("")) {
            consulta = " o.id.codigoNcm like '" + filtros.get("campoFiltro") + "%' ";
        }

        if (filtros.containsKey("filtroRegimeConfigurado") && !filtros.get("filtroRegimeConfigurado").equals("")) {
            if (!consulta.equals("")) {
                consulta += " and ";
            }

            consulta += " (select count(n) from regraNcm n where n.id.regimeTributarioId = '" + filtros.containsKey("filtroRegimeConfigurado") + "') > 0 ";
        }

        if (filtros.containsKey("filtroRegimeNaoConfigurado") && !filtros.get("filtroRegimeNaoConfigurado").equals("")) {
            if (!consulta.equals("")) {
                consulta += " and ";
            }

            consulta += " (select count(n) from regraNcm n where n.id.regimeTributarioId = '" + filtros.containsKey("filtroRegimeNaoConfigurado") + "') > 0 ";
        }

        return consulta;
    }

    public List<RegraNcmDto> findRangeDto(String complementoConsulta, int apartirDe, int quantidade) {

        String consultaId = "SELECT distinct(o.id.codigoNcm) from RegraNcm o ";
        String consulta = "SELECT o from RegraNcm o ";

        String consultaSQL = "";
        consultaSQL = getWhere(complementoConsulta);

        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {
            consultaId += " where " + consultaSQL;
        }

        boolean existeAtributoPadrao = Utils.existeAtributoPadraoByClass(RegraNcm.class);

//        String sqlComplementar = "";
        if (existeAtributoPadrao) {
            consultaId += (consultaId.contains("where") ? " and " : " where ") + " o.atributoPadrao.dominioEvento <>  3";
        }

        consultaId += " order by o.id.codigoNcm";

        List<String> listaCodigoNcm = em.createQuery(consultaId)
                .setMaxResults(quantidade).
                setFirstResult(apartirDe).
                getResultList();

        List<RegraNcmDto> listaDto = new ArrayList<>();

        for (String ncm : listaCodigoNcm) {

            List<RegraNcm> listaResultNcm = em.createQuery(consulta + " where o.id.codigoNcm = (:ncm) and o.atributoPadrao.dominioEvento <> 3 order by o.id.codigoNcm")
                    .setParameter("ncm", ncm)
                    .setMaxResults(3) // cada registro da regra possui 3 registros da tabela
                    .getResultList();

            listaDto.add(getDto(listaResultNcm));

        }

        return listaDto;
    }

    public List<RegraNcmDto> searchDto(Map filtros, int apartirDe, int quantidade) {

        String consultaId = "SELECT distinct(o.id.codigoNcm) from RegraNcm o ";
        String consulta = "SELECT o from RegraNcm o ";

        String consultaSQL = "";
        consultaSQL = getWhereDto(filtros);

        if (filtros != null && !filtros.isEmpty()) {
            consultaId += " where " + consultaSQL + " ";
        }

        consultaId += " order by o.id.codigoNcm";

        List<String> listaCodigoNcm = em.createQuery(consultaId)
                .setMaxResults(quantidade).
                setFirstResult(apartirDe).
                getResultList();

        List<RegraNcmDto> listaDto = new ArrayList<>();

        for (String ncm : listaCodigoNcm) {

            List<RegraNcm> listaResultNcm = em.createQuery(consulta + " where o.id.codigoNcm = (:ncm) and o.atributoPadrao.dominioEvento <> 3 order by o.id.codigoNcm")
                    .setParameter("ncm", ncm)
                    .setMaxResults(3) // cada registro da regra possui 3 registros da tabela
                    .getResultList();

            listaDto.add(getDto(listaResultNcm));

        }

        return listaDto;
    }

    public String countDto(Map filtros) {
        String consulta = "SELECT count(distinct o.id.codigoNcm) from RegraNcm o ";

        if (filtros.containsKey("campoFiltro") && !filtros.get("campoFiltro").equals("")) {
            consulta += " where o.id.codigoNcm like '" + filtros.get("campoFiltro") + "%' ";
        }

        if (filtros.containsKey("filtroRegimeConfigurado") && !filtros.get("filtroRegimeConfigurado").equals("")) {
            if (!consulta.equals("")) {
                consulta += (consulta.contains("where") ? " and " : " where ");
            }

            consulta += " (select count(n) from regraNcm n where n.id.regimeTributarioId = '" + filtros.containsKey("filtroRegimeConfigurado") + "') > 0 ";
        }

        if (filtros.containsKey("filtroRegimeNaoConfigurado") && !filtros.get("filtroRegimeNaoConfigurado").equals("")) {
            if (!consulta.equals("")) {
                consulta += (consulta.contains("where") ? " and " : " where ");
            }

            consulta += " (select count(n) from regraNcm n where n.id.regimeTributarioId = '" + filtros.containsKey("filtroRegimeNaoConfigurado") + "') > 0 ";
        }

        Long l = (Long) em.createQuery(consulta).getSingleResult();

        if (l == null) {
            l = 0L;
        }

        return l.toString();
    }

    public RegraNcmDto findByIdDto(Object id) {
        List<RegraNcm> listaResultNcm = em.createQuery("SELECT o from RegraNcm o where o.id.codigoNcm = (:ncm) and o.atributoPadrao.dominioEvento <> 3 order by o.id.codigoNcm")
                .setParameter("ncm", id.toString())
                .setMaxResults(3) // cada registro da regra possui 3 registros da tabela
                .getResultList();

        if (!listaResultNcm.isEmpty()) {
            return getDto(listaResultNcm);
        } else {
            return null;
        }
    }

    public RegraNcmDto createDto(RegraNcmDto obj) throws Exception {
        try {
            setID(obj);

            AtributoPadrao atributoPadrao = Utils.getAtributoPadraoByObject(obj);
            if (atributoPadrao != null) {
                atributoPadrao.setDataAlteracao(new Date());
            }

            if (obj.getRegraNcmLucroPresumido().getId() != null) {
                // obj.getRegraNcmLucroPresumido().setId(new RegraNcmPK());
                // obj.getRegraNcmLucroPresumido().getId().setCodigoNcm(obj.getId());
                obj.getRegraNcmLucroPresumido().getId().setRegimeTributarioId(DominioRegime.LUCROPRESUMIDO);
                obj.getRegraNcmLucroPresumido().setAtributoPadrao(obj.getAtributoPadrao());
                em.persist(obj.getRegraNcmLucroPresumido());
            }

            if (obj.getRegraNcmLucroReal().getId() != null) {
                // obj.getRegraNcmLucroReal().setId(new RegraNcmPK());
                // obj.getRegraNcmLucroReal().getId().setCodigoNcm(obj.getId());
                obj.getRegraNcmLucroReal().getId().setRegimeTributarioId(DominioRegime.LUCROREAL);
                obj.getRegraNcmLucroReal().setAtributoPadrao(obj.getAtributoPadrao());
                em.persist(obj.getRegraNcmLucroReal());
            }

            if (obj.getRegraNcmSimplesNacional().getId() != null) {
                // obj.getRegraNcmSimplesNacional().setId(new RegraNcmPK());
                // obj.getRegraNcmSimplesNacional().getId().setCodigoNcm(obj.getId());
                obj.getRegraNcmSimplesNacional().getId().setRegimeTributarioId(DominioRegime.SIMPLESNACIONAL);
                obj.getRegraNcmSimplesNacional().setAtributoPadrao(obj.getAtributoPadrao());
                em.persist(obj.getRegraNcmSimplesNacional());
            }

            return obj;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public RegraNcmDto updateDto(RegraNcmDto obj) throws Exception {

        AtributoPadrao atributoPadrao = Utils.getAtributoPadraoByObject(obj);
        if (atributoPadrao != null) {
            atributoPadrao.setDataAlteracao(new Date());
        }

        if (obj.getRegraNcmLucroPresumido().getId() != null && obj.getRegraNcmLucroPresumido().getId().getCodigoNcm() != null) {
            obj.getRegraNcmLucroPresumido().setAtributoPadrao(obj.getAtributoPadrao());
            em.merge(obj.getRegraNcmLucroPresumido());
        }

        if (obj.getRegraNcmLucroReal().getId() != null && obj.getRegraNcmLucroReal().getId().getCodigoNcm() != null) {
            obj.getRegraNcmLucroReal().setAtributoPadrao(obj.getAtributoPadrao());
            em.merge(obj.getRegraNcmLucroReal());
        }

        if (obj.getRegraNcmSimplesNacional().getId() != null && obj.getRegraNcmSimplesNacional().getId().getCodigoNcm() != null) {
            obj.getRegraNcmSimplesNacional().setAtributoPadrao(obj.getAtributoPadrao());
            em.merge(obj.getRegraNcmSimplesNacional());
        }

        return obj;
    }

    public RegraNcmDto deleteDto(RegraNcmDto obj) throws Exception {

        AtributoPadrao atributoPadrao = Utils.getAtributoPadraoByObject(obj);
        if (atributoPadrao != null) {
            atributoPadrao.setDataAlteracao(new Date());
        }

        if (obj.getRegraNcmLucroPresumido().getId() != null) {
            obj.getRegraNcmLucroPresumido().setAtributoPadrao(obj.getAtributoPadrao());
            em.merge(obj.getRegraNcmLucroPresumido());
        }

        if (obj.getRegraNcmLucroReal().getId() != null) {
            obj.getRegraNcmLucroReal().setAtributoPadrao(obj.getAtributoPadrao());
            em.merge(obj.getRegraNcmLucroReal());
        }

        if (obj.getRegraNcmSimplesNacional().getId() != null) {
            obj.getRegraNcmSimplesNacional().setAtributoPadrao(obj.getAtributoPadrao());
            em.merge(obj.getRegraNcmSimplesNacional());
        }

        return obj;
    }

    public RegraNcmDto getDto(List<RegraNcm> listaResultNcm) {

        if (listaResultNcm.size() > 0) {

            RegraNcmDto dto = new RegraNcmDto();
            dto.setRegraNcmLucroPresumido(new RegraNcm());
            dto.getRegraNcmLucroPresumido().setId(new RegraNcmPK());
            dto.getRegraNcmLucroPresumido().setTributoFederal(new TributoFederal());
            dto.setRegraNcmLucroReal(new RegraNcm());
            dto.getRegraNcmLucroReal().setId(new RegraNcmPK());
            dto.getRegraNcmLucroReal().setTributoFederal(new TributoFederal());
            dto.setRegraNcmSimplesNacional(new RegraNcm());
            dto.getRegraNcmSimplesNacional().setId(new RegraNcmPK());
            dto.getRegraNcmSimplesNacional().setTributoFederal(new TributoFederal());

            for (RegraNcm regraNcm : listaResultNcm) {

                dto.setId(regraNcm.getId().getCodigoNcm());

                if (regraNcm.getId().getRegimeTributarioId().equals(DominioRegime.LUCROPRESUMIDO)) {
                    dto.setRegraNcmLucroPresumido(regraNcm);
                    dto.setSituacaoLucroPresumido("Configurado");
                }
                if (regraNcm.getId().getRegimeTributarioId().equals(DominioRegime.LUCROREAL)) {
                    dto.setRegraNcmLucroReal(regraNcm);
                    dto.setSituacaoLucroReal("Configurado");
                }
                if (regraNcm.getId().getRegimeTributarioId().equals(DominioRegime.SIMPLESNACIONAL)) {
                    dto.setRegraNcmSimplesNacional(regraNcm);
                    dto.setSituacaoSimplesNacional("Configurado");
                }

            }

            return dto;
        } else {
            return null;
        }

    }

    public List<RegraNcmDto> getListaAgrupada(List<RegraNcm> listaResultNcm) {

        List<RegraNcmDto> listaResult = new ArrayList<RegraNcmDto>();

        if (listaResultNcm.size() > 0) {

            RegraNcmDto dto = new RegraNcmDto();

            for (RegraNcm regraNcm : listaResultNcm) {

                if (dto.getId() == null) {
                    dto.setId(regraNcm.getId().getCodigoNcm());
                }

                if (!regraNcm.getId().getCodigoNcm().equals(dto.getId())) {
                    listaResult.add(dto);
                    dto = new RegraNcmDto();
                    dto.setId(regraNcm.getId().getCodigoNcm());
                }

                if (regraNcm.getId().getRegimeTributarioId().equals(DominioRegime.LUCROPRESUMIDO)) {
                    dto.setRegraNcmLucroPresumido(regraNcm);
                }
                if (regraNcm.getId().getRegimeTributarioId().equals(DominioRegime.LUCROREAL)) {
                    dto.setRegraNcmLucroReal(regraNcm);
                }
                if (regraNcm.getId().getRegimeTributarioId().equals(DominioRegime.SIMPLESNACIONAL)) {
                    dto.setRegraNcmSimplesNacional(regraNcm);
                }
            }

            listaResult.add(dto); // adiciona o ultimo registro

        }

        return listaResult;

    }

    public RegraNcm loadRegraNcm(String codigoNcm, String regime) {

        String sql = "select * from syscontabil.regra_ncm where codigo_ncm = :ncm and regime_tributario_id = :regime";

        RegraNcm ncm = (RegraNcm) em.createNativeQuery(sql)
                .setParameter("ncm", codigoNcm)
                .setParameter("regime", regime).getSingleResult();
        
        return ncm;
    }

}
