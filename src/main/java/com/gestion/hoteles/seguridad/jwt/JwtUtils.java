package com.gestion.hoteles.seguridad.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;



    //METODO QUE GENERA EL TOKEN DE ACCESO

    public String generateAccesToken(String username){
        return Jwts.builder()
                .setSubject(username)  //Envio el sujeto, el usuario.
                .setIssuedAt(new Date(System.currentTimeMillis())) //Fecha de creacion del token
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256) //firma y algoritmo de encriptacion
                .compact();
           }

   //Obtener firma del Token

    public Key getSignatureKey(){

        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }


        //Valido el token, si suelta una excepcion es que la firma es incorecta
    public boolean isTokenValid(String token){

        try {

            Jwts.parserBuilder()//Decodifica el token. Lo lee.
                     .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

            return  true;

        }catch(Exception e){

            log.error("Token invalido, error: ".concat(e.getMessage()));
            return  false;
        }
    }


    //Recibo el Payload, obtengo todos los Claims
    public Claims extractAllClaims(String token){

            return  Jwts.parserBuilder()  //Decodifica el token. Lo lee.
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();


    }

    //Obtener un solo CLAIM, este metodo esta hecho para devolver un valor de cualquier tipo
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction){

            Claims claims = extractAllClaims(token);
            return claimsTFunction.apply(claims);

    }

    //Obtener el username del token

    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

}
