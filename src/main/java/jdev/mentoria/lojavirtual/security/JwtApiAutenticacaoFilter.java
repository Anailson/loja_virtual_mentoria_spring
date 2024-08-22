package jdev.mentoria.lojavirtual.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Filtro onde as requisiões serão capturadas para autenticar
public class JwtApiAutenticacaoFilter extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

         //Estabelece a autenticaçã do user
        Authentication authentication = new JWTTokenAutenticacaoService().getaAuthentication((HttpServletRequest) request, (HttpServletResponse) response);

        //colocar o processo de autenticação para o spring security
        SecurityContextHolder.getContext().setAuthentication(authentication);

       chain.doFilter(request, response);
    }
}
