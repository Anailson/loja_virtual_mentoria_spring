package jdev.mentoria.Usuario;



import jdev.mentoria.lojavirtual.ExceptionMentoriaJava;
import jdev.mentoria.lojavirtual.LojaVirtualMentoriaApplication;
import jdev.mentoria.lojavirtual.controller.PessoaController;
import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class TestePessoaUsuario extends TestCase {


    @Autowired
    private PessoaController pessoaController;

    @Test
    public void TestCadPessoaFisica() throws ExceptionMentoriaJava {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setCnpj("08012528000192");
        pessoaJuridica.setNome("anailson");
        pessoaJuridica.setEmail("anailson@gmail.com");
        pessoaJuridica.setTelefone("6158664646");
        pessoaJuridica.setInscEstadual("36566185003");
        pessoaJuridica.setInscMunicipal("098776049846");
        pessoaJuridica.setNomeFantasia("dev");
        pessoaJuridica.setRazaoSocial("dev");

        pessoaController.salvarPj(pessoaJuridica);



    }

}
