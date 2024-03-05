package com.gestion.hoteles.negocio.servicio;

import com.gestion.hoteles.dominio.entidad.Usuario;
import com.gestion.hoteles.persistencia.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UsuarioDetallesServImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    //Spring consulta a este metodo por debajo,
    // para asegurarse cual va a ser el usuario a consultar, de donde los saca
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario= usuarioRepositorio.existsByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario ".concat(username)+ "no existe."));

        Collection<? extends GrantedAuthority> authorities=usuario.getRoles()
                .stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_".concat(rol.getTiposRol().name())))
                .collect(Collectors.toSet());



        return new User(usuario.getUsername(), usuario.getPassword(),
                                                true,
                                        true,
                                      true,
                                        true,
                                                        authorities);
                                    }
}
