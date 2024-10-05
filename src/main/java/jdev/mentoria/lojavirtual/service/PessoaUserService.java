package jdev.mentoria.lojavirtual.service;


import jdev.mentoria.lojavirtual.model.PessoaJuridica;
import jdev.mentoria.lojavirtual.model.Usuario;
import jdev.mentoria.lojavirtual.repository.PessoaRepository;
import jdev.mentoria.lojavirtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class PessoaUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica juridica){

        // juridica = pessoaRepository.save(juridica);

         for(int i = 0; i < juridica.getEnderecos().size(); i++){
             juridica.getEnderecos().get(i).setPessoa(juridica);
             juridica.getEnderecos().get(i).setEmpresa(juridica);
         }

        juridica = pessoaRepository.save(juridica);

         Usuario usuarioPJ = usuarioRepository.findUserByPessoa(juridica.getId(), juridica.getEmail());

         if(usuarioPJ == null){

             String constraint = usuarioRepository.consultaConstraintAcesso();
             if(constraint != null){
                jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;"); //Consulta o banco pra limpar a contraint
             }
             usuarioPJ = new Usuario();
             usuarioPJ.setDataAtualSenha(Calendar.getInstance(). getTime());
             usuarioPJ.setEmpresa(juridica);
             usuarioPJ.setPessoa(juridica);
             usuarioPJ.setLogin(juridica.getEmail());

             String senha = "" + Calendar.getInstance().getTimeInMillis();//senha que sera enviada por email
             String senhaCript = new BCryptPasswordEncoder().encode(senha); ///senha criptgrafada

             usuarioPJ.setSenha(senhaCript);

             usuarioPJ = usuarioRepository.save(usuarioPJ);

             usuarioRepository.insereAcessoUserPJ(usuarioPJ.getId());

         }
           return juridica;
    }

}


/**
 * Criando as validações ao salvar mais especificas
 */