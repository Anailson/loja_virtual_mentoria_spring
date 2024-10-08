package jdev.mentoria.lojavirtual.security;


import jdev.mentoria.lojavirtual.service.ImplementacaoUserDetailsService;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpSessionListener;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {

    @Autowired
    private ImplementacaoUserDetailsService implementacaoUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .disable().authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()

                //redireciona ou da um retorno para o index quando deslogar do sistema
                .anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")

                //mapear a logout do sistema
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                //filtra as requisições para o login de JWT
                .and().addFilterAfter(new JWTLoginFilter("/login",authenticationManager()), UsernamePasswordAuthenticationFilter.class)

                .addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    //CONSULTA USER NO BANCO COM O SPRING SECURITY
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /*Ignorando URLS no momento para não autenticar - ser assim definir com a regra do negócio*/
    @Override
    public void configure(WebSecurity web) throws Exception {
       // web.ignoring().antMatchers(HttpMethod.GET,"/salvarAcesso","/deleteAcesso")
         //       .antMatchers(HttpMethod.POST,"/salvarAcesso","/deleteAcesso");
        /*Ignorando URLS no momento para não autenticar */
    }
}
