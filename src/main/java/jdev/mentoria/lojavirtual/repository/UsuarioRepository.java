package jdev.mentoria.lojavirtual.repository;

import jdev.mentoria.lojavirtual.model.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {  //ou JpaRepository

    //METODO PARA CONSULTAR USUÁRIO NO BANCO
    @Query(value = "select u from Usuario u where u.login = ?1")
    Usuario findUserByLogin(String login);

    @Query(value = "select u from Usuario u where u.pessoa.id = ?1 or u.login =?2")
    Usuario findUserByPessoa(Long id, String email);

    @Query(value = "select constraint_name from information_schema.constraint_column_usage\n" +
            " where table_name ='usuarios_acesso' and column_name = 'acesso_id'\n" +
            "and constraint_name <> 'unique_acesso_user';", nativeQuery = true)
    String consultaConstraintAcesso();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into usuarios_acesso(usuario_id, acesso_id) values (?1, (select id from acesso where descricao = 'ROLE_USER'))")  //1 é o ID do ROLER_USER NO BANCO
    void insereAcessoUserPJ(Long idUser);
}
