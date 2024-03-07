package com.gestion.hoteles.seguridad;

import com.gestion.hoteles.negocio.servicio.UsuarioDetallesServImpl;
import com.gestion.hoteles.seguridad.filtros.FiltroAutenticacionJwt;
import com.gestion.hoteles.seguridad.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {

    @Autowired
    UsuarioDetallesServImpl usuarioDetallesServ;

    @Autowired
    JwtUtils jwtUtils;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
       /* return httpSecurity
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
                .build();*/




        FiltroAutenticacionJwt filtroAutenticacionJwt=new FiltroAutenticacionJwt(jwtUtils);
        filtroAutenticacionJwt.setAuthenticationManager(authenticationManager);
        //filtroAutenticacionJwt.setFilterProcessesUrl("/login");


                            //COMPORTAMIENTO DE ACESO A ENDPOINTS y Autenticacion
        return httpSecurity
                .csrf(config -> config.disable())//.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login").permitAll(); //endpoint publico
                    auth.anyRequest().authenticated(); //cualquier otro sera autenticado
                })
                .sessionManagement(sesion -> {
                    sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(filtroAutenticacionJwt)//Reemplazo el httpBasic()
                .build();

    }




   /* //Al iniciar sesion me redirige a este endpoint
    public AuthenticationSuccessHandler successHandler() {
        return ((req, response, authentication) -> {
            response.sendRedirect("/usuario/sesion");
        });
    }


    @Bean //ESTE METODO me ayuda a recuperar los datos de la sesion
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }*/


    //Usuario de acceso con permisos para la aplicacion
    /*@Bean
    UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager =new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("pablo")
                .password("123")
                .roles()
                .build());

        return  manager;

    }*/



    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //ADMINISTRA LA AUTENTICACION Y EXIJE UN PASSWORD ENCODER
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                PasswordEncoder passwordEncoder) throws Exception {

        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(usuarioDetallesServ)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }


    public  static  void main(String[] args){

        System.out.println(new BCryptPasswordEncoder().encode("123"));
    }


}


