package jdev.mentoria.lojavirtual.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdev.mentoria.lojavirtual.ApplicationContextLoad;
import jdev.mentoria.lojavirtual.model.Usuario;
import jdev.mentoria.lojavirtual.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * CRIAR A AUTENTICAÇÃO E RETORNA A AUTENTICAÇÃO JWT
 */
@Service
public class JWTTokenAutenticacaoService {

    //TOKEN DE VALIDADE DE 20 DIAS - http://extraconversion.com/pt/tempo/dias/dias-para-milissegundos.html
    private static final long EXPIRATION_TIME = 1728000000;   //não pode ser alterado

    //CHAVE DE SENHA PARA JUNTAR COM O JWT - senha de API externa por exemplo
    private static final String SECRET = "SS/dasda/dafsdfsfsd";

    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    //GERAR O TOKEN E DA A RESPOSTA PARA O CLIENTE JWT
    public void addAuthentication(HttpServletResponse response, String username) throws Exception{

         //MONTAGEM DO TOKEN
        String JWT = Jwts.builder().//CHAMAR O GERADOR DE TOKNE
                setSubject(username)//ADICIONAR O USER
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))//tempo de EXPIRAÇÃO
                .signWith(SignatureAlgorithm.HS512,SECRET).compact();
        //exemplo: Bearer *++fsafsdf+dsf+sdf+dsf++**f*sd  (JWT codificado)
        String toke = TOKEN_PREFIX + " " + JWT;
        //DA A RESPOSTA PARA TELA E PARA O CLINETE, OUTRA API, NAVEGADOR E OUTRA CHAMADA JAVA ETC
        response.addHeader(HEADER_STRING, toke);

        //chamando a liberação de cors
        liberacaoCors(response);

        //USANDO PARA VER NO POSTAMN PARA TESTE
        response.getWriter().write("{\"Authorization\": \"" + toke + "\"}");

    }

    //RETORNA O USUÁRIO VALIDADO COM TOKEN OU CASO NÃO SEJA VALIDO RETORNA NULL
    public Authentication getaAuthentication(HttpServletRequest request, HttpServletResponse response){

        String token = request.getHeader(HEADER_STRING);

        if(token != null){

            String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim(); //retira o Beareh do token

            //FAZ A VALIDAÇÃO DO TOKEN DO USUÁRIO NA REQUISIÇÃO E OBTEM O USER
            String user = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(tokenLimpo)
                    .getBody().getSubject();//PEGAR O USUÁRIO ADM OU OUTROS USUARIOS

            if(user != null){ //user diferente null ou seja encontrado

                Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class).findUserByLogin(user); //carrega no banco para obter credenciais

                if (usuario != null){
                    return  new UsernamePasswordAuthenticationToken(
                            usuario.getLogin(),
                            usuario.getSenha(),
                            usuario.getAuthorities());

                }
            }
        }

        liberacaoCors(response);
        return null;
    }

    //FAZENDO LIBERAÇÃO CONTRA ERRO DE CORS NO NAVEGADOR - ALGO QUE OCORRER QUANDO ACESSAR RECURSOS POR EXEMPLO COM O ANGULAR.
    private void liberacaoCors(HttpServletResponse response){
        if(response.getHeader("Access-Control-Allow-Origin") == null){
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        if(response.getHeader("Access-Control-Allow-Headers") == null){
            response.addHeader("Access-Control-Allow-Headers", "*");
        }
        if(response.getHeader("Access-Control-Allow-Origin") == null){
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        if(response.getHeader("Access-Control-Allow-Methods") == null){
            response.addHeader("Access-Control-Allow-Methods", "*");
        }
    }


}
