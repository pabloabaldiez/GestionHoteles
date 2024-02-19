package com.gestion.hoteles.dominio.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {

    private Long id;
    private String username;
    private String nombre;
    private String apellido;
    private String rol;

}
