package com.gestion.hoteles.seguridad.filtros;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion.hoteles.dominio.entidad.Usuario;
import com.gestion.hoteles.seguridad.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FiltroAutenticacionJwt extends UsernamePasswordAuthenticationFilter {


    private JwtUtils jwtUtils;

    public FiltroAutenticacionJwt(JwtUtils jwtUtils){
        this.jwtUtils=jwtUtils;
    };

    //Cuando se intentan autenticar que se hace
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Usuario usuario = null;
        String username="";
        String password="";


        // Toma los parametros en formato Json y lo mappea a la
        // entidad Usuario, poblando el atributo indicado.


        try {
            usuario= new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            username= usuario.getUsername();
            password=usuario.getPassword();

        } catch(IOException e){

            throw  new RuntimeException(e);
        }


        //Con esto me autentico en la app
        UsernamePasswordAuthenticationToken tokenAutenticacion =
                                        new UsernamePasswordAuthenticationToken(username, password);


        return getAuthenticationManager().authenticate(tokenAutenticacion);
    }



    //Cuando se han autenticado correctamente que se hace//ACA GENERO EL TOKEN
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        //Este es el objeto user que viene con Spring Security
        //Aca obtenemos el objeto que tiene todos los detalles del usuario.
        User user= (User) authResult.getPrincipal();

        String token = jwtUtils.generarTokenAcceso(user.getUsername());

        //Respondo a la solicitud del token
        response.addHeader("Autorizacion", token);

        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);
        httpResponse.put("Mensaje", "Autenticacion correcta");
        httpResponse.put("Username", user.getUsername());

        //Transformo y envio como respuesta el Map anterior pero en Json
        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));

        //Status de la respuesta
        response.setStatus(HttpStatus.OK.value());

        //Cual va a ser el contenido de la respuesta
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        //Para garantizar que se escriba bientodo el contenido
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
