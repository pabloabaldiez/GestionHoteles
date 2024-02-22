package com.gestion.hoteles.dominio.entidad;

import javax.persistence.*;
//import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name= "usuario")
public class Usuario {

    @Id @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter@Setter
    @Column(name = "username")
    private String username;

    @Getter@Setter
    @Column(name = "password")
    private String password;

    @Getter@Setter
    @Column(name = "nombre")
    private String nombre;

    @Getter@Setter
    @Column(name = "apellido")
    private String apellido;

    @Getter@Setter
    @Column(name = "email")
    private String email;

    @Getter@Setter
    @Column(name = "dni")
    private int dni;

    @Getter@Setter
    @Column(name = "rol")
    private String rol;

}
