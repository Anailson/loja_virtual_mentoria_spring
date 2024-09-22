package jdev.mentoria.lojavirtual.service;


import jdev.mentoria.lojavirtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;
}
