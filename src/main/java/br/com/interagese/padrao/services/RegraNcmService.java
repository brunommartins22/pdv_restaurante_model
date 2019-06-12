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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class RegraNcmService extends PadraoService<RegraNcmDto>{
   
    @Override
    public List<RegraNcmDto> findRange(String complementoConsulta, int apartirDe, int quantidade) {

        String consulta = "SELECT o from RegraNcm o ";
        String consultaSQL = "";
        consultaSQL = getWhere(complementoConsulta);

        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {
            consulta += " where " + consultaSQL;
        }

        boolean existeAtributoPadrao = Utils.existeAtributoPadraoByClass(RegraNcm.class);

//        String sqlComplementar = "";
        if (existeAtributoPadrao) {
            consulta += (consulta.contains("where") ? " and " : " where ") + " o.atributoPadrao.dominioEvento <>  3";
        }
        
        consulta += " order by o.id.codigoNcm";
        
        List<RegraNcm> listaResultNcm = em.createQuery(consulta)
                .setMaxResults(quantidade*3). // cada registro da regra possui 3 registros da tabela
                setFirstResult(apartirDe*3). // cada registro da regra possui 3 registros da tabela
                getResultList();
        
        return getListaAgrupada(listaResultNcm);
    }

    @Override
    public List<RegraNcmDto> search(Map filtros, int apartirDe, int quantidade) {

        String consulta = "SELECT o from RegraNcm o ";
        String consultaSQL = "";
        consultaSQL = getWhere(filtros);

        if (filtros != null && !filtros.isEmpty()) {
            consulta += " where " + consultaSQL + " ";
        }

        consulta += " order by o.id.codigoNcm";
        
        List<RegraNcm> listaResultNcm = em.createQuery(consulta)
                .setMaxResults(quantidade*3). // cada registro da regra possui 3 registros da tabela
                setFirstResult(apartirDe*3). // cada registro da regra possui 3 registros da tabela
                getResultList();
        
        return getListaAgrupada(listaResultNcm);
    }

    @Override
    public String count(String complementoConsulta) {
        String consultaSQL = "";
        consultaSQL = getWhere(complementoConsulta);

        String consulta = "SELECT count(o) from RegraNcm o ";

        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {
            consulta += " where " + consultaSQL;
        }

        Long l = (Long) em.createQuery(consulta).getSingleResult();
        if (l == null) {
            l = 0L;
        } else {
            l = l/3; // um registro para cada grupo de 3
        }

        return l.toString();
    }
    
    @Override
    public RegraNcmDto create(RegraNcmDto obj) throws Exception {
        try {
            setID(obj);

            AtributoPadrao atributoPadrao = Utils.getAtributoPadraoByObject(obj);
            if (atributoPadrao != null) {
                atributoPadrao.setDataAlteracao(new Date());
            }
            
            obj.getRegraNcmLucroPresumido().setId(new RegraNcmPK());
            obj.getRegraNcmLucroPresumido().getId().setCodigoNcm(obj.getId());
            obj.getRegraNcmLucroPresumido().getId().setRegimeTributarioId(DominioRegime.LUCROPRESUMIDO);
            obj.getRegraNcmLucroPresumido().setAtributoPadrao(obj.getAtributoPadrao());
            
            obj.getRegraNcmLucroReal().setId(new RegraNcmPK());
            obj.getRegraNcmLucroReal().getId().setCodigoNcm(obj.getId());
            obj.getRegraNcmLucroReal().getId().setRegimeTributarioId(DominioRegime.LUCROREAL);
            obj.getRegraNcmLucroReal().setAtributoPadrao(obj.getAtributoPadrao());
            
            obj.getRegraNcmSimplesNacional().setId(new RegraNcmPK());
            obj.getRegraNcmSimplesNacional().getId().setCodigoNcm(obj.getId());
            obj.getRegraNcmSimplesNacional().getId().setRegimeTributarioId(DominioRegime.SIMPLESNACIONAL);
            obj.getRegraNcmSimplesNacional().setAtributoPadrao(obj.getAtributoPadrao());
            
            em.persist(obj.getRegraNcmLucroPresumido());
            em.persist(obj.getRegraNcmLucroReal());
            em.persist(obj.getRegraNcmSimplesNacional());

            return obj;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    @Override
    public RegraNcmDto update(RegraNcmDto obj) throws Exception {

        AtributoPadrao atributoPadrao = Utils.getAtributoPadraoByObject(obj);
        if (atributoPadrao != null) {
            atributoPadrao.setDataAlteracao(new Date());
        }

        em.merge(obj.getRegraNcmLucroPresumido());
        em.merge(obj.getRegraNcmLucroReal());
        em.merge(obj.getRegraNcmSimplesNacional());
        return obj;
    }
    
    public List<RegraNcmDto> getListaAgrupada(List<RegraNcm> listaResultNcm){
        
        List<RegraNcmDto> listaResult = new ArrayList<RegraNcmDto>();
        
        RegraNcmDto dto = new RegraNcmDto();
        
        for( RegraNcm regraNcm: listaResultNcm){
            
            if(dto.getId() == null){
                dto.setId(regraNcm.getId().getCodigoNcm());
            }
            
            if(!regraNcm.getId().getCodigoNcm().equals(dto.getId())){
                listaResult.add(dto);
                dto = new RegraNcmDto();
                dto.setId(regraNcm.getId().getCodigoNcm());
            }
            
            if(regraNcm.getId().getRegimeTributarioId().equals(DominioRegime.LUCROPRESUMIDO)){
                dto.setRegraNcmLucroPresumido(regraNcm);
            }
            if(regraNcm.getId().getRegimeTributarioId().equals(DominioRegime.LUCROREAL)){
                dto.setRegraNcmLucroReal(regraNcm);
            }
            if(regraNcm.getId().getRegimeTributarioId().equals(DominioRegime.SIMPLESNACIONAL)){
                dto.setRegraNcmSimplesNacional(regraNcm);
            }
        }
        
        return listaResult;
    }
}