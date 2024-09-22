package jdev.mentoria.lojavirtual;

import jdev.mentoria.lojavirtual.controller.AcessoController;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Calendar;
import java.util.List;

//@Profile("test") - caso fosse realizar teste com o banco especificos de testes no properties
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class LojaVirtualMentoriaApplicationTests extends TestCase {

	@Autowired
	private AcessoController acessoController;

	@Autowired
	private AcessoRepository acessoRepository;

	@Test
	public void testCadastraAcesso() throws ExceptionMentoriaJava {

		String descAcesso = "ROLE_ADM" + Calendar.getInstance().getTimeInMillis();

		Acesso acesso = new Acesso();

		acesso.setDescricao(descAcesso);

		assertEquals(true, acesso.getId() == null);

		/*Gravou no banco de dados*/
		acesso = acessoController.salvarAcesso(acesso).getBody();

		assertEquals(true, acesso.getId() > 0);

		/*Validar dados salvos de forma correta*/
//		assertEquals("ROLE_ADMIN", acesso.getDescricao()); /  acesso.setDescricao("ROLE_ADM" + Calendar.getInstance().getTimeInMillis());
		assertEquals("ROLE_ADMIN", + Calendar.getInstance().getTimeInMillis());

		/*Teste de carregamento*/

		Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();

		assertEquals(acesso.getId(), acesso2.getId());

		/*Teste de delete*/

		acessoRepository.deleteById(acesso2.getId());

		acessoRepository.flush();/*Roda esse SQL de delete no banco de dados*/

		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);

		assertEquals(true, acesso3 == null);

		/*Teste de query*/
		acesso = new Acesso();

		acesso.setDescricao("ROLE_ALUNO");

		acesso = acessoController.salvarAcesso(acesso).getBody();

		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());

		assertEquals(1, acessos.size());
		acessoRepository.deleteById(acesso.getId());


	}

}
