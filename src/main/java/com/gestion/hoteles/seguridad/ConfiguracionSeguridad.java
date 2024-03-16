package com.gestion.hoteles.seguridad;

import com.gestion.hoteles.excepciones.CustomAccesDeniedHandler;
import com.gestion.hoteles.excepciones.CustomExceptionHandler;
import com.gestion.hoteles.negocio.servicio.UsuarioDetallesServImpl;
import com.gestion.hoteles.seguridad.filtros.FiltroAutenticacionJwt;
import com.gestion.hoteles.seguridad.filtros.FiltroAutorizacionJwt;
import com.gestion.hoteles.seguridad.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true)
public class ConfiguracionSeguridad  {

    @Autowired
    private UsuarioDetallesServImpl usuarioDetallesServ;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private FiltroAutorizacionJwt filtroAutorizacionJwt;

    @Autowired
    private CustomAccesDeniedHandler customAccesDeniedHandler;




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {


        FiltroAutenticacionJwt filtroAutenticacionJwt=new FiltroAutenticacionJwt(jwtUtils);
        filtroAutenticacionJwt.setAuthenticationManager(authenticationManager);
        filtroAutenticacionJwt.setFilterProcessesUrl("/login");


                            //COMPORTAMIENTO DE ACESO A ENDPOINTS y Autenticacion
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/usuario/guardar").permitAll();
                    auth.requestMatchers("/usuario/login").permitAll();//endpoint publico
                    auth.requestMatchers("/accesAdmin", "/sesion").hasRole("ADMIN");
                    auth.anyRequest().authenticated(); //cualquier otro sera autenticado
                })

                .formLogin().successHandler(successHandler())

                .and()

                .userDetailsService(usuarioDetallesServ)

                .exceptionHandling(e->e.accessDeniedHandler(customAccesDeniedHandler)
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))

                .sessionManagement(sesion -> {
                    sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    sesion.invalidSessionUrl("/login")
                            .maximumSessions(1)
                            .expiredUrl("/login")
                            .sessionRegistry(sessionRegistry())
                            .and()
                            .sessionFixation()
                            .migrateSession();
                })
                .addFilter(filtroAutenticacionJwt)//Reemplazo el .httpBasic().and()
                .addFilterBefore(filtroAutorizacionJwt, UsernamePasswordAuthenticationFilter.class)

                .build();

    }





    public AuthenticationSuccessHandler successHandler() {
        return ((req, response, authentication) -> {
            response.sendRedirect("/usuario/lista");
        });
    }


    @Bean //ESTE METODO me ayuda a recuperar los datos de la sesion
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }


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
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(usuarioDetallesServ)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }


}




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