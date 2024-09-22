package jdev.mentoria.testeControllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdev.mentoria.lojavirtual.LojaVirtualMentoriaApplication;
import jdev.mentoria.lojavirtual.controller.AcessoController;
import jdev.mentoria.lojavirtual.model.Acesso;
import jdev.mentoria.lojavirtual.repository.AcessoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class TestController {

    @Autowired
    private AcessoController acessoController;

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Teste responsável pelo cadastro que contém perfil de usuários - salvarAcesso /salvarAcesso
     */
    @Test
    public void testRestApiCadastroAcesso() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Acesso acesso = new Acesso();
        //acesso.setDescricao("ROLE_ADM"); //   validar caso já tenho Acesso com a mesma descrição  -  acesso.setDescricao("ROLE_ADM" + Calendar.getInstance().getTimeInMillis());

        acesso.setDescricao("ROLE_ADM" + Calendar.getInstance().getTimeInMillis());

        ResultActions retornoAPi = mockMvc
                .perform(MockMvcRequestBuilders.post("/salvarAcesso")
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Retorno da API: " + retornoAPi.andReturn().getResponse().getContentAsString());

        /* CONVERTER O RETORNO DA API PARA UM OBJETO DE ACESSO */
        Acesso objetoRetono = objectMapper
                .readValue(retornoAPi.andReturn().getResponse().getContentAsString(), Acesso.class);

        assertEquals(acesso.getDescricao(), objetoRetono.getDescricao());
    }

    @Test
    public void testRestApiDeleteAcesso() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_TESTE_DELETE");

        acesso = acessoRepository.save(acesso);

        ResultActions retornoAPi = mockMvc
                .perform(MockMvcRequestBuilders.post("/deleteAcesso")
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Retorno da API: " + retornoAPi.andReturn().getResponse().getContentAsString());
        System.out.println("Status de retorno: " + retornoAPi.andReturn().getResponse().getStatus());

        assertEquals("Acesso Removido", retornoAPi.andReturn().getResponse().getContentAsString());
        assertEquals(200, retornoAPi.andReturn().getResponse().getStatus());
    }

    @Test
    public void testRestApiDeletePorId() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_TESTE_DELETE");

        acesso = acessoRepository.save(acesso);

        ResultActions retornoAPi = mockMvc
                .perform(MockMvcRequestBuilders.delete("/deleteAcessoPorId/" + acesso.getId())
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Retorno da API: " + retornoAPi.andReturn().getResponse().getContentAsString());
        System.out.println("Status de retorno: " + retornoAPi.andReturn().getResponse().getStatus());

        assertEquals("Acesso Removido", retornoAPi.andReturn().getResponse().getContentAsString());
        assertEquals(200, retornoAPi.andReturn().getResponse().getStatus());
    }

    @Test
    public void testRestApiObterAcessoID() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_OBTER_ID");

        acesso = acessoRepository.save(acesso);

        ResultActions retornoAPi = mockMvc
                .perform(MockMvcRequestBuilders.get("/obterAcesso/" + acesso.getId())
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        assertEquals(200, retornoAPi.andReturn().getResponse().getStatus());

        Acesso acessoRetorno = objectMapper.readValue(retornoAPi.andReturn().getResponse().getContentAsString(), Acesso.class);

        assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());
        assertEquals(acesso.getId(), acessoRetorno.getId());
    }

    @Test
    public void testRestApiBuscarDesc() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_TESTE_OBTER_LIST");

        acesso = acessoRepository.save(acesso);

        ResultActions retornoAPi = mockMvc
                .perform(MockMvcRequestBuilders.get("/buscarPorDesc/OBTER_LIST")
                        .content(objectMapper.writeValueAsString(acesso))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        assertEquals(200, retornoAPi.andReturn().getResponse().getStatus());

        /* CONVERTE EM UMA LISTA */
        List<Acesso> retornoApiList = objectMapper.readValue(retornoAPi.andReturn().getResponse().getContentAsString(), new TypeReference<List<Acesso>>() {});

        assertEquals(1, retornoApiList.size());
        assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());

        acessoRepository.deleteById(acesso.getId());
    }
}
