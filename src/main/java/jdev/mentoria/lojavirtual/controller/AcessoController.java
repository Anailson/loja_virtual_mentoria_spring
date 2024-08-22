package jdev.mentoria.lojavirtual.controller;


import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @ResponseBody  /*Retorno da API*/
    @PostMapping(value = "/salvarAcesso") /*Mapeando a URL para receber JSON*/
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso){/*REceber o JSON e converte para objeto*/

        Acesso acessoSalvo = acessoService.save(acesso);

        return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
    }

    @ResponseBody  /*Retorno da API*/
    @PostMapping(value = "/deleteAcesso") /*Mapeando a URL para receber JSON e deletando pelo objeto todo*/
    public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso){/*REceber o JSON e converte para objeto*/

        acessoRepository.deleteById(acesso.getId());

        return new ResponseEntity("Acesso Removido",HttpStatus.OK);
    }

    //@Secured({"ROLER_GERENTE", "ROLE_AMIN"})--- SOMENTE USUARIO COM PERGIL PODE EXECUTAR O METODO
    @ResponseBody  /*Retorno da API*/
    @DeleteMapping(value = "/deleteAcessoPorId/{id}") /*Mapeando a URL para receber JSON DELETE POR ID*/
    public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id){/*REceber o JSON e converte para objeto*/

        acessoRepository.deleteById(id);

        return new ResponseEntity("Acesso Removido",HttpStatus.OK);
    }

    /*Carregando registros*/
    @ResponseBody  /*Retorno da API*/
    @GetMapping(value = "/obterAcesso/{id}") /*Mapeando a URL para receber JSON DELETE POR ID*/
    public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id){/*REceber o JSON e converte para objeto*/

       Acesso acesso = acessoRepository.findById(id).get();
        return new ResponseEntity<>(acesso, HttpStatus.OK);
    }
    /*busca por descrição*/
    @ResponseBody  /*Retorno da API*/
    @GetMapping(value = "/buscarPorDesc/{desc}") /*Mapeando a URL para receber JSON DELETE POR ID*/
    public ResponseEntity<List<Acesso>> buscarPorDesc(@PathVariable("desc") String desc){/*REceber o JSON e converte para objeto*/

        List<Acesso>  acesso = acessoRepository.buscarAcessoDesc(desc);
        return new ResponseEntity<List<Acesso>>(acesso, HttpStatus.OK);
    }

}
