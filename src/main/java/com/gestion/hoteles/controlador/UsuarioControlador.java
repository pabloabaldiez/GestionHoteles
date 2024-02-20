package com.gestion.hoteles.controlador;

import com.gestion.hoteles.dominio.entidad.Usuario;
import com.gestion.hoteles.negocio.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio servicio;
    @PostMapping("/guardar")
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){

        Usuario usuarioNuevo=  servicio.guardar(usuario);

       return ResponseEntity.ok(usuario);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Usuario>> lista() {

        List<Usuario> lista = servicio.listaUsuario();

        if (lista.isEmpty()) {

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);

    }


}
