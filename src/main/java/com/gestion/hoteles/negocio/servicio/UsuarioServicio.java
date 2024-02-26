package com.gestion.hoteles.negocio.servicio;

import com.gestion.hoteles.dominio.entidad.EntidadUsuario;
import com.gestion.hoteles.dominio.excepciones.ExcepcionUsuario;
import com.gestion.hoteles.persistencia.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;


    public EntidadUsuario guardar(EntidadUsuario entidadUsuario){

        if(usuarioRepositorio.existsByUsername(entidadUsuario.getUsername()))
            throw new ExcepcionUsuario("Ya existe  ese nombre de usuario");

        if(usuarioRepositorio.existsByEmail(entidadUsuario.getEmail()))
            throw new ExcepcionUsuario("Ya existe ese email");

        if(usuarioRepositorio.existsByDni(entidadUsuario.getDni()))
            throw new ExcepcionUsuario("Ya existe ese DNI");


        return usuarioRepositorio.save(entidadUsuario);

    }

    public List<EntidadUsuario> listaUsuario(){

        if(usuarioRepositorio.findAll().isEmpty())
            throw new ExcepcionUsuario("No hay usuarios en la lista");

        return usuarioRepositorio.findAll();
    }

    public EntidadUsuario buscaUsuarioPorId(int id){

        EntidadUsuario entidadUsuario =usuarioRepositorio.findById(id).orElse(null);

        if(entidadUsuario ==null)
            throw new ExcepcionUsuario("No existe un usuario con ese id");

        return entidadUsuario;
    }

    public EntidadUsuario buscaUsuarioPorDni(int dni){

        EntidadUsuario entidadUsuario = usuarioRepositorio.findByDni(dni).orElseThrow(() -> new ExcepcionUsuario("No existe un usuario con ese DNI"));


        return entidadUsuario;
    }





}
