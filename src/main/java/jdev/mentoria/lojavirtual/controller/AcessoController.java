package jdev.mentoria.lojavirtual.controller;


import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(value = "/deleteAcesso") /*Mapeando a URL para receber JSON*/
    public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso){/*REceber o JSON e converte para objeto*/

        acessoRepository.deleteById(acesso.getId());

        return new ResponseEntity("Acesso removido",HttpStatus.OK);
    }
}
