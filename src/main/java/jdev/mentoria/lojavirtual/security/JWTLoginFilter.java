package jdev.mentoria.lojavirtual.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import jdev.mentoria.lojavirtual.model.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

   //CONFIGURANDO O GERENCIADOR DE AUTENTICAÇÃO
    public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {

       //OBRIGA A AUTENTICAR A URL
        super(new AntPathRequestMatcher(url));
        //GERENCIADOR DE AUTENTICAÇÃO
        setAuthenticationManager(authenticationManager);
    }

     //RETORNA O USUÁRIO AO PROCESSO A AUTENTICAÇÃO
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        //OBTER O USUÁRIO
        Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

        //retorna O USER COM LOGIN E SENHA
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(),user.getSenha()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        try {
            new JWTTokenAutenticacaoService().addAuthentication(response,authResult.getName());
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
}
