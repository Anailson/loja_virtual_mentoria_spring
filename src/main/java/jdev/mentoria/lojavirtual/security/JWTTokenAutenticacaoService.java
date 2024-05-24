package jdev.mentoria.lojavirtual.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * CRIAR A AUTENTICAÇÃO E RETORNA A AUTENTICAÇÃO JWT
 */
@Service
public class JWTTokenAutenticacaoService {

    //TOKEN DE VALIDADE DE 20 DIAS - http://extraconversion.com/pt/tempo/dias/dias-para-milissegundos.html
    private static final long EXPIRATION_TIME = 1728000000;

    //CHAVE DE SENHA PARA JUNTAR COM O JWT
    private static final String SECRET = "SS/dasda/dafsdfsfsd";

    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    //GERAR O TOKEN E DA A RESPOSTA PARA O CLIENTE JWT
    public void addAuthentication(HttpServletResponse response, String username) throws Exception{

         //MONTAGEM DO TOKEN
        String JWT = Jwts.builder().//CHAMAR O GERADOR DE TOKNE
                setSubject(username)//ADICIONAR O USER
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))//tempo de EXPIRAÇÃO
                .signWith(SignatureAlgorithm.ES512,SECRET).compact();
        //exemplo: Bearer ++fsafsdf+dsf+sdf+dsf++**f*sd
        String toke = TOKEN_PREFIX + " " + JWT;
        //DA A RESPOSTA PARA TELA E PARA O CLINETE, OUTRA API, NAVEGADOR E OUTRA CHAMADA JAVA ETC
        response.addHeader(HEADER_STRING, toke);

        //USADO PARA VER NO POSTAMN PARA TESTE
        response.getWriter().write("{\"Authorization\": \"" + toke + "\"}");


    }


}
