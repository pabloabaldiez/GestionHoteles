package com.gestion.hoteles.persistencia;

import com.gestion.hoteles.dominio.entidad.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,Integer>{

    boolean existsByEmail(String email);
    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsById(int id);
    Optional<Usuario> findByDni(int dni);
    boolean existsByDni(String dni);

}
