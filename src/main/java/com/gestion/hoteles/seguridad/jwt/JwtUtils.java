package com.gestion.hoteles.seguridad.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    //Define la firma
    private String secretKey;

    //tiempo de expiracion
    private String timeExpiration;


}
