package com.gestion.hoteles;

import com.gestion.hoteles.dominio.entidad.EntidadRoles;
import com.gestion.hoteles.dominio.entidad.RolEnum;
import com.gestion.hoteles.dominio.entidad.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class HotelesApplication {

	public static void main(String[] args) {

		SpringApplication.run(HotelesApplication.class, args);}



	//CREACION DE USUARIO AL EJECUTAR APP
		@Autowired
		PasswordEncoder passwordEncoder;

		CommandLineRunner init(){
			return args ->{

				Usuario usuario= Usuario.builder()
						.email("pablo@gmail.com")
						.username("pablo")
						.password(passwordEncoder.encode("123"))
						.roles(Set.of(EntidadRoles.builder()
								.tiposRol(RolEnum.valueOf(RolEnum.ADMIN.name())).build()))
						.build();
			};
		}
	}


