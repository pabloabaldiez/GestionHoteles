package com.gestion.hoteles.dominio.entidad;

import javax.persistence.*;
//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name= "usuario")
public class Usuario {

    @Id @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter@Setter
    @Column(name = "usuario")
    private String usuario;

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
    @Column(name = "rol")
    private String rol;

}
