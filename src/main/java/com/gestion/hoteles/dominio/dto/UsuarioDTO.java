package com.gestion.hoteles.dominio.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {

    final Long id;
    final String username;
    final String nombre;
    final String apellido;
    final String rol;

}
