/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interagese.padrao.controllers;

import br.com.interagese.erplibrary.Utils;
import br.com.interagese.padrao.rest.util.IsServiceDefault;
import br.com.interagese.padrao.rest.util.PadraoController;
import br.com.interagese.padrao.services.RegraNcmService;
import br.com.interagese.syscontabil.dto.RegraNcmDto;
import br.com.interagese.syscontabil.models.RegraNcm;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bruno Martins
 */
@RestController
@RequestMapping("api/regra-ncm")
public class RegraNcmController extends PadraoController<RegraNcm> {

    @IsServiceDefault
    @Autowired
    private RegraNcmService RegraNcmService;
    
    @GetMapping(path = "/dto/{apartirDe}/{quantidade}/{filtro:.+}")
    public String findRangeDto(@PathVariable("apartirDe") int apartirDe, @PathVariable("quantidade") int quantidade,
            @PathVariable("filtro") String filtro) {

        String json = serializar(RegraNcmService.findRangeDto(filtro.equals("*") ? null : filtro, apartirDe, quantidade));

        return json;
    }

    @PostMapping(path = "/dto/{apartirDe}/{quantidade}")
    public String findRangeDto(@PathVariable("apartirDe") int apartirDe, @PathVariable("quantidade") int quantidade,
            @RequestBody Map filtros) {

        return serializar(RegraNcmService.searchDto(filtros, apartirDe, quantidade));
    }

    @GetMapping(path = "/dto/count/{filtro:.+}")
    public String countDto(@PathVariable("filtro") String filtro) {

        if (filtro != null && filtro.equals("*")) {
            filtro = null;
        }

        return RegraNcmService.countDto(filtro);
    }

    @GetMapping(path = "/dto/{id:.+}")
    public String findByIdDto(@PathVariable(name = "id") String id) {
        try {
            Class clazz = Utils.getIDTypeByClass(RegraNcmDto.class);

            if (clazz == String.class) {
                return serializar(RegraNcmService.findByIdDto(id));
            } else {
                return serializar(RegraNcmService.findByIdDto(Long.parseLong(id)));
            }

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(PadraoController.class.getName()).log(Level.SEVERE, null, ex);
            return returnException(ex);
        }
    }

    @PostMapping(path = "/dto")
    public String createDto(@RequestBody String json) {

        try {
            RegraNcmDto entity = (RegraNcmDto) deserializar(json, RegraNcmDto.class);

            return serializar(RegraNcmService.createDto(entity));
        } catch (Exception ex) {
            //FIXME - tem que gerar log de erro, mas não erro de validação
            Logger.getLogger(PadraoController.class.getName()).log(Level.SEVERE, null, ex);
            return returnException(ex);
        }

    }
    
    @PutMapping(path = "/dto")
    public String updateDto(@RequestBody String json) {
        try {
            RegraNcmDto entity = (RegraNcmDto) deserializar(json, RegraNcmDto.class);

            return serializar(RegraNcmService.updateDto(entity));
        } catch (Exception ex) {
            Logger.getLogger(PadraoController.class.getName()).log(Level.SEVERE, null, ex);
            return returnException(ex);
        }

    }

    @DeleteMapping(path = "/dto")
    public String deleteDto(@RequestBody String json) {
        try {
            RegraNcmDto entity = (RegraNcmDto) deserializar(json, RegraNcmDto.class);

            RegraNcmService.deleteDto(entity);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(PadraoController.class.getName()).log(Level.SEVERE, null, ex);
            return returnException(ex);
        }

    }

}
