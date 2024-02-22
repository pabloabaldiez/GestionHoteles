package com.gestion.hoteles.dominio.excepciones;


public class ExcepcionUsuario extends RuntimeException{

    public ExcepcionUsuario(String message) {
        super(message);
    }
}
