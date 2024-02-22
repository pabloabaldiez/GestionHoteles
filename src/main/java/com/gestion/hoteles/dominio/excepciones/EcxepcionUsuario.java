package com.gestion.hoteles.dominio.excepciones;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EcxepcionUsuario extends RuntimeException{

    public EcxepcionUsuario(String message) {
        super(message);
    }
}
