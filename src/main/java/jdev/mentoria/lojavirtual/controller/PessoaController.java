package jdev.mentoria.lojavirtual.controller;

import jdev.mentoria.lojavirtual.ExceptionMentoriaJava;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import jdev.mentoria.lojavirtual.repository.PessoaRepository;
import jdev.mentoria.lojavirtual.service.PessoaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // Importar o LoggerFactory
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PessoaController {

    private static final Logger logger = LoggerFactory.getLogger(PessoaController.class); // Inicializar o logger

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaUserService pessoaUserService;

    @ResponseBody
    @PostMapping(value = "/salvarPj")
    public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava {

       // logger.info("Iniciando o salvamento da Pessoa Jurídica: {}", pessoaJuridica); // Log de início

        // Validação objeto nulo
        if (pessoaJuridica == null) {
            logger.error("Tentativa de salvar uma Pessoa Jurídica nula."); // Log de erro
            throw new ExceptionMentoriaJava("Pessoa juridica não pode ser null");
        }

        if (pessoaJuridica.getId() == null && pessoaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
            logger.warn("CNPJ já cadastrado: {}", pessoaJuridica.getCnpj()); // Log de aviso
            throw new ExceptionMentoriaJava("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
        }

        pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);

       // logger.info("Pessoa Jurídica salva com sucesso: {}", pessoaJuridica); // Log de sucesso

        return new ResponseEntity<>(pessoaJuridica, HttpStatus.OK);
    }
}
