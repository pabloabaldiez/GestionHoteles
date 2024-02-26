package com.gestion.hoteles.dominio.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //implementa el patron de diseño builder para construir objetos de esa clase
@Entity(name= "usuario")
public class EntidadUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max=30)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;


    @Column(name = "nombre")
    private String nombre;


    @Column(name = "apellido")
    private String apellido;

    @Email
    @NotBlank
    @Size(max=80)
    @Column(name = "email")
    private String email;

    @Column(name = "dni")
    private int dni;

    @Column(name = "rol")
    private String rol;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = EntidadUsuario.class, cascade = CascadeType.PERSIST)
    private Set<EntidadRoles> roles;

}
