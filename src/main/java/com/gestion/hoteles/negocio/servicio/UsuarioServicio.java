package com.gestion.hoteles.negocio.servicio;

import com.gestion.hoteles.dominio.entidad.Usuario;
import com.gestion.hoteles.excepciones.ExcepcionUsuario;
import com.gestion.hoteles.persistencia.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;


    public Usuario guardar(Usuario usuario){

        if(usuarioRepositorio.existsByUsernameBoolean(usuario.getUsername()))
            throw new ExcepcionUsuario("Ya existe  ese nombre de usuario");

        if(usuarioRepositorio.existsByEmail(usuario.getEmail()))
            throw new ExcepcionUsuario("Ya existe ese email");

        if(usuarioRepositorio.existsByDni(usuario.getDni()))
            throw new ExcepcionUsuario("Ya existe ese DNI");


        return usuarioRepositorio.save(usuario);

    }

    public List<Usuario> listaUsuario(){

        if(usuarioRepositorio.findAll().isEmpty())
            throw new ExcepcionUsuario("No hay usuarios en la lista");

        return usuarioRepositorio.findAll();
    }

    public Usuario buscaUsuarioPorId(int id){

        Usuario usuario =usuarioRepositorio.findById(id).orElse(null);

        if(usuario ==null)
            throw new ExcepcionUsuario("No existe un usuario con ese id");

        return usuario;
    }

    public Usuario buscaUsuarioPorDni(int dni){

        Usuario usuario = usuarioRepositorio.findByDni(dni).orElseThrow(() -> new ExcepcionUsuario("No existe un usuario con ese DNI"));


        return usuario;
    }



    public void eliminaUsuario(int id) {

        usuarioRepositorio.deleteById(id);

    }






}
