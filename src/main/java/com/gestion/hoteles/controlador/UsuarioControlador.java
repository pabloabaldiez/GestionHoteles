package com.gestion.hoteles.controlador;

import com.gestion.hoteles.dominio.dto.UsuarioDTO;
import com.gestion.hoteles.dominio.entidad.EntidadRoles;
import com.gestion.hoteles.dominio.entidad.RolEnum;
import com.gestion.hoteles.dominio.entidad.Usuario;
import com.gestion.hoteles.negocio.servicio.UsuarioServicio;
import com.gestion.hoteles.persistencia.UsuarioRepositorio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.session.SessionInformation;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {


    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    @Autowired
    UsuarioServicio servicio;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/guardar")
    public ResponseEntity<Usuario> guardarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO){

        Set<EntidadRoles> rolesUsuario = usuarioDTO.getRoles().stream()
                .map(rol -> EntidadRoles.builder()
                        .tiposRol(RolEnum.valueOf(rol))
                        .build())
                .collect(Collectors.toSet());


        Usuario usuario =  Usuario.builder()
                .username(usuarioDTO.getUsername())
                .password(passwordEncoder.encode(usuarioDTO.getPassword()))
                .email(usuarioDTO.getEmail())
                .nombre(usuarioDTO.getNombre())
                .apellido(usuarioDTO.getApellido())
                .dni(usuarioDTO.getDni())
                .roles(rolesUsuario)
                .build();

        servicio.guardar(usuario);

        return ResponseEntity.ok(usuario);
    }


    @DeleteMapping("/eliminar")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String eliminarUsuario(@RequestParam String id){

        servicio.eliminaUsuario(Integer.parseInt(id));

        return "Se ha eliminado el usuario con id ".concat(id);

    }


   @GetMapping("/lista")
    public ResponseEntity<List<Usuario>> lista() {

        List<Usuario> lista = servicio.listaUsuario();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);
    }



    //TESTEOS DE SEGURIDAD
    /*@Autowired
>>>>>>> estructura-y-bbdd
    private SessionRegistry sessionRegistry;

    @GetMapping("/sesion")
    public ResponseEntity<?> detallesSesion(){

        String sesionId="";
        User user=null;             // User viene definido en Spring y representa a un usuario

        List<Object> sesiones=sessionRegistry.getAllPrincipals();

        for(Object sesion:sesiones){
            if(sesion instanceof User){
                user= (User)sesion;
            }

          List<SessionInformation> sesionInfo=sessionRegistry.getAllSessions(sesion, false);

            for(SessionInformation sesionInformacion: sesionInfo)
                sesionId= sesionInformacion.getSessionId();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("response", "Hello World");
        response.put("sesionId", sesionId);
        response.put("user", user);

        return ResponseEntity.ok(response);
    }*/

}
