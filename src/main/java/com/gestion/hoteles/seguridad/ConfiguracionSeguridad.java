package com.gestion.hoteles.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {


    //Retorna un security filter chain
    @Bean                                   //HttpSecurity es la clase que configura la seguridad
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        //csrf proteje nuestras peticiones para que no sean interceptadas(es enable por defecto)
        // authorizeHttpRequests() con esto configuro que urls estaran protegidas o no
        // requestMatchers() las peticiones que coincidan con los endpoints especificados

        return httpSecurity
                .authorizeHttpRequests()
                    .requestMatchers("/usuarios/lista").permitAll() //cualquiera puede entrar
                    .anyRequest().authenticated( )//cualquier otra necesita autorizacion
                .and()//Se usa para agregar mas configuraciones
                .formLogin().permitAll()//habilita el form de login para todos
                .and()
                .build();
    }
}
