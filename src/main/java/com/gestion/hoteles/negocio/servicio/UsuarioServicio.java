package com.gestion.hoteles.negocio.servicio;

import com.gestion.hoteles.dominio.entidad.Usuario;
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

        Usuario nuevoUsuario=usuarioRepositorio.save(usuario);

        return nuevoUsuario;

    }

    public List<Usuario> listaUsuario(){

        return usuarioRepositorio.findAll();
    }

    public Usuario buscaUsuarioPorId(int id){

        Usuario usuario=usuarioRepositorio.findById(id).orElse(null);

        return usuario;
    }





}
