package com.gestion.hoteles.persistencia;

import com.gestion.hoteles.dominio.entidad.EntidadRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepositorio extends JpaRepository<EntidadRoles,Integer> {

}
