package com.gestion.hoteles.controlador;

import com.gestion.hoteles.dominio.entidad.Usuario;
import com.gestion.hoteles.negocio.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Usuario>> lista() {

        List<Usuario> lista = servicio.listaUsuario();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);
    }

    @GetMapping("/busqueda-id/{id}")
    public ResponseEntity<Usuario> busquedaPorId(@PathVariable("id")int id){

        Usuario usuario=servicio.buscaUsuarioPorId(id);

        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }





}
