package com.gestion.hoteles.persistencia;

import com.gestion.hoteles.dominio.entidad.EntidadUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsuarioRepositorio extends JpaRepository<EntidadUsuario,Integer>{

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsById(int id);
    Optional<EntidadUsuario> findByDni(int dni);
    boolean existsByDni(int dni);

}
