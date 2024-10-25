package jdev.mentoria.Usuario;

import jdev.mentoria.lojavirtual.ExceptionMentoriaJava;
import jdev.mentoria.lojavirtual.LojaVirtualMentoriaApplication;
import jdev.mentoria.lojavirtual.controller.PessoaController;
import jdev.mentoria.lojavirtual.enums.TipoEndereco;
import jdev.mentoria.lojavirtual.model.Endereco;
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
        pessoaJuridica.setCnpj("08012728000109");
        pessoaJuridica.setNome("julia");
        pessoaJuridica.setEmail("julia@gmail.com");
        pessoaJuridica.setTelefone("6158664646");
        pessoaJuridica.setInscEstadual("36566185003");
        pessoaJuridica.setInscMunicipal("098776049846");
        pessoaJuridica.setNomeFantasia("dev");
        pessoaJuridica.setRazaoSocial("dev");
         //1728140306628  senha pegando em debung Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbmFpbHNvbjJAZ21haWwuY29tIiwiZXhwIjoxNzI5ODY4NjQxfQ.v95DW7ld-j-Yv3cZX5BlRyBDJDQAntmt-JLcFtS0Dyc-1x8oAT1ZlyYly0i_MQKcFYE3E8IWmNRDsWdJe19kxg

        Endereco endereco1 = new Endereco();
        endereco1.setBairro("javva");
        endereco1.setCep("6464654654");
        endereco1.setComplemento("azul");
        endereco1.setEmpresa(pessoaJuridica);
        endereco1.setNumero("5660");
        endereco1.setPessoa(pessoaJuridica);
        endereco1.setRuaLogra("Av brasilia");
        endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
        endereco1.setUf("SP");
        endereco1.setCidade("brasilia");
        endereco1.setBairro("brasilia");

        Endereco endereco2 = new Endereco();
        endereco2.setBairro("remain");
        endereco2.setCep("6464654654");
        endereco2.setComplemento("branco");
        endereco2.setEmpresa(pessoaJuridica);
        endereco2.setNumero("5660");
        endereco2.setPessoa(pessoaJuridica);
        endereco2.setRuaLogra("Av bahia");
        endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
        endereco2.setUf("BA");
        endereco2.setCidade("brasilia");
        endereco2.setBairro("brasilia");


        pessoaJuridica.getEnderecos().add(endereco1);
        pessoaJuridica.getEnderecos().add(endereco2);

         pessoaJuridica =   pessoaController.salvarPj(pessoaJuridica).getBody();

         assertEquals(true, pessoaJuridica.getId() > 0);

         for(Endereco endereco: pessoaJuridica.getEnderecos()){
             assertEquals(true, endereco.getId() > 0);
         }
         assertEquals(2, pessoaJuridica.getEnderecos().size());
    }
}
