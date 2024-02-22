package com.gestion.hoteles.negocio.servicio;

import com.gestion.hoteles.dominio.entidad.Usuario;
import com.gestion.hoteles.dominio.excepciones.EcxepcionUsuario;
import com.gestion.hoteles.persistencia.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;


    public Usuario guardar(Usuario usuario){

        if(usuarioRepositorio.existsByEmail(usuario.getEmail()))
            throw new EcxepcionUsuario("Ya existe ese email");

        if(usuarioRepositorio.existsByUsername(usuario.getUsername()))
            throw new EcxepcionUsuario("Ya existe  ese nombre de usuario");


        Usuario nuevoUsuario=usuarioRepositorio.save(usuario);

        return nuevoUsuario;

    }

    public List<Usuario> listaUsuario(){

        if(usuarioRepositorio.findAll().isEmpty())
            throw new EcxepcionUsuario("No hay usuarios en la lista");

        return usuarioRepositorio.findAll();
    }

    public Usuario buscaUsuarioPorId(int id){

        Usuario usuario=usuarioRepositorio.findById(id).orElse(null);

        if(usuario==null)
            throw new EcxepcionUsuario("No existe un usuario con ese id");

        return usuario;
    }





}
