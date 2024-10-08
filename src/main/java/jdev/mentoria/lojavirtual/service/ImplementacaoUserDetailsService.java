package jdev.mentoria.lojavirtual.service;

import jdev.mentoria.lojavirtual.model.Usuario;
import jdev.mentoria.lojavirtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImplementacaoUserDetailsService  implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findUserByLogin(username);//RECEBE O LOGIN PARA CONSULTAA

        if(usuario == null){ //usuario não existir no BD
            throw  new UsernameNotFoundException("Usuário não foi encontrado");
        }
       //caso tenha usuário
        return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities()); //getAuthorities são as permissões
    }
}
