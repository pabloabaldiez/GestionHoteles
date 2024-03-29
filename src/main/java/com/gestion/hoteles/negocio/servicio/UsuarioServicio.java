package com.gestion.hoteles.negocio.servicio;

import com.gestion.hoteles.dominio.entidad.Usuario;
import com.gestion.hoteles.excepciones.ExcepcionUsuario;
import com.gestion.hoteles.persistencia.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;


    public Usuario guardar(Usuario usuario){

        if(usuarioRepositorio.existsByUsername(usuario.getUsername()))
            throw new ExcepcionUsuario("Ya existe  ese nombre de usuario");

        if(usuarioRepositorio.existsByEmail(usuario.getEmail()))
            throw new ExcepcionUsuario("Ya existe ese email");

        return usuarioRepositorio.save(usuario);

    }



    public List<Usuario> listaUsuario(){

        if(usuarioRepositorio.findAll().isEmpty())
            throw new ExcepcionUsuario("No hay usuarios en la lista");

        return usuarioRepositorio.findAll();
    }


    public Usuario buscaUsuarioPorId(int id) {

        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);

        if (usuario == null)
            throw new ExcepcionUsuario("No existe un usuario con ese id");

        return usuario;}


    public void eliminaUsuario(int id){

        try {
            usuarioRepositorio.deleteById(id);
        } catch (EmptyResultDataAccessException e) {

            throw new ExcepcionUsuario("El usuario con id: " + id + " no fue encontrado");

        }
    }


    }


