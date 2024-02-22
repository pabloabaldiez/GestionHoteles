package com.gestion.hoteles.dominio.excepciones;

public class EcxepcionUsuario extends RuntimeException{
    public EcxepcionUsuario(String message) {
        super(message);
    }
}
