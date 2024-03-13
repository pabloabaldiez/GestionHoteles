package com.gestion.hoteles.seguridad.filtros;

import com.gestion.hoteles.negocio.servicio.UsuarioDetallesServImpl;
import com.gestion.hoteles.seguridad.jwt.JwtUtils;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Puedo definirlo como component porque no
// debo enviar ningun parametro adicional para su funcionamiento.
@Component
public class FiltroAutorizacionJwt extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsuarioDetallesServImpl usuarioDetallesServ;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {


        String tokenHeader=request.getHeader("Authorization");

        if(tokenHeader !=null && tokenHeader.startsWith("Bearer")){

            String token = tokenHeader.substring(7, tokenHeader.length());

            if(jwtUtils.isTokenValid(token)){
                String username=jwtUtils.getUsernameFromToken(token);

                UserDetails userDetails=usuarioDetallesServ.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }


        }
     //   Si el token es null o no empieza con bearer
    //  con esta declaracion continua con el filtro de validacion.
        filterChain.doFilter(request, response);

    }
}
