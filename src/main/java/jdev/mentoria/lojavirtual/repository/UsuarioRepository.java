package jdev.mentoria.lojavirtual.repository;

import jdev.mentoria.lojavirtual.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    //METODO PARA CONSULTAR USUÁRIO NO BANCO
    @Query(value = "select u from Usuario u where u.login = ?1")
    Usuario findUserByLogin(String login);


}
