package jdev.mentoria.Usuario;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdev.mentoria.lojavirtual.LojaVirtualMentoriaApplication;
import jdev.mentoria.lojavirtual.controller.AcessoController;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.model.PessoaFisica;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.repository.PessoaRepository;
import jdev.mentoria.lojavirtual.service.PessoaUserService;
import junit.framework.TestCase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class TestePessoaUsuario extends TestCase {

    @Autowired
    private PessoaUserService pessoaUserService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    public void TestCadPessoaFisica(){

        PessoaJuridica pessoaJuridica = new PessoaJuridica();

        pessoaJuridica.setCnpj("116654616565456");
        pessoaJuridica.setNome("Lia empresa");
        pessoaJuridica.setEmail("lia@gmail.com");
        pessoaJuridica.setTelefone("6155464646");
        pessoaJuridica.setInscEstadual("24484646246");
        pessoaJuridica.setInscMunicipal("64264646400");
        pessoaJuridica.setNomeFantasia("ribeiro");
        pessoaJuridica.setRazaoSocial("santos");

        pessoaRepository.save(pessoaJuridica);

      /*  PessoaFisica pessoaFisica = new PessoaFisica();

        pessoaFisica.setCpf("002546254669");
        pessoaFisica.setNome("Lia");
        pessoaFisica.setEmail("lia@gmail.com");
        pessoaFisica.setTelefone("6155464646");
        pessoaFisica.setEmpresa(pessoaFisica);
*/

    }

}
