package jdev.mentoria.lojavirtual;

import jdev.mentoria.lojavirtual.controller.AcessoController;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import jdev.mentoria.lojavirtual.service.AcessoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.UpperCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class LojaVirtualMentoriaApplicationTests {

	@Autowired
	private AcessoController acessoController;

	@Test
	public void testCadastraAcesso() {

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_ADMIN");

		acessoController.salvarAcesso(acesso);
	}

}
