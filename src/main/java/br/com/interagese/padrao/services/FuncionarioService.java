/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.services;

import br.com.interagese.padrao.rest.util.PadraoService;
import br.com.interagese.syscontabil.models.Funcionario;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Programacao
 */
@Service
public class FuncionarioService extends PadraoService<Funcionario> {

    public List<Funcionario> buscarPorDepartamento(Integer idDepartamento) {
        return em.createQuery("SELECT o from Funcionario o where o.departamento.id = " + idDepartamento)
                .getResultList();
    }

    public List<Funcionario> buscaPorFuncao(Integer idFuncao) {
        return em.createQuery("SELECT o from Funcionario o where o.funcao.id = " + idFuncao)
                .getResultList();
    }

    public List<Funcionario> buscaPorNome(String nomeFuncionario) {
        return em.createQuery("SELECT o FROM Funcionario o WHERE o.nomeFuncionario like '%" + nomeFuncionario + "%'").getResultList();
    }
    
    public List<Funcionario> buscaPorNomeFuncao (String nomeFuncao){
        return em.createQuery("SELECT o FROM Funcionario o WHERE o.funcao.nomeFuncao like '%"+ nomeFuncao + "%'").getResultList();
    }
                                                            // '%"+ NOME +"% == PARA NOME COMPLETO
                                                            // '"+ NOME +"%' == PARA SOMENTE O COMEÇO
                                                            // '"%+ NOME +"' == PARA SOMENTE O FINAL
}
