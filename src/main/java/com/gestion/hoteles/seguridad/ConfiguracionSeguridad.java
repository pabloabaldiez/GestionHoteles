/*package com.gestion.hoteles.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers("/usuario/lista").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin()
                    .successHandler(successHandler())
                    .permitAll()
                .and()
                .sessionManagement()//Configura comportamiento de las sesiones
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) //ALWAYS - IF_REQUIRED - NEVER -STATELESS
                    .invalidSessionUrl("/login")
                    .maximumSessions(1)
                    .expiredUrl("/login")
                .sessionRegistry(sessionRegistry())//se va a encargar de administrar todos los registros que estan en mi sesion, define un objeto
                .and()
                .sessionFixation()//
                    .migrateSession()
                .and()
                .httpBasic()
                .and()
                .build();
    }


    //Al iniciar sesion me redirige a este endpoint
    public AuthenticationSuccessHandler successHandler() {
        return ((req, response, authentication) -> {
            response.sendRedirect("/usuario/sesion");
        });
    }


    @Bean //ESTE METODO me ayuda a recuperar los datos de la sesion
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

}


*/