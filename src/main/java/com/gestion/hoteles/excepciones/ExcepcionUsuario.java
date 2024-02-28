package com.gestion.hoteles.excepciones;


public class ExcepcionUsuario extends RuntimeException{

    public ExcepcionUsuario(String message) {
        super(message);
    }
}
