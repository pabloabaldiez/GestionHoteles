package com.gestion.hoteles.controlador;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRolesControlador {

    @GetMapping("/accesoAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String accesoAdmin(){
        return"Accediste con rol de administrador";

    }

    @GetMapping("/accesoUsuario")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String accesoUsuario(){
        return"Accediste con rol de Usuario";

    }

    @GetMapping("/accesoInvi")
    @PreAuthorize("hasRole('INVITADO') or hasRole('ADMIN')")
    public String accesoInvi(){
        return"Accediste con rol de invitado";

    }
}
