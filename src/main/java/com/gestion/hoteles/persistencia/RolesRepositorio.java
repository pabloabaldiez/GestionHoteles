package com.gestion.hoteles.persistencia;

import com.gestion.hoteles.dominio.entidad.Erol;
import com.gestion.hoteles.dominio.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepositorio extends JpaRepository<Erol,Integer> {

}
