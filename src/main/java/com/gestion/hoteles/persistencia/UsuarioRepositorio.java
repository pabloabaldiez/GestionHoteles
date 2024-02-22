package com.gestion.hoteles.persistencia;

import com.gestion.hoteles.dominio.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,Integer>{

    boolean existsByEmailOrUsername(String email, String username);

    //Optional <Usuario> findUsuarioPorId(int id);
    //Optional <Usuario> findById(Integer id);
    //Optional <List<Usuario>> findListaUsuarios();

}
