package com.gestion.hoteles.controlador;

import com.gestion.hoteles.dominio.entidad.EntidadUsuario;
import com.gestion.hoteles.negocio.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio servicio;

    @PostMapping("/guardar")
    public ResponseEntity<EntidadUsuario> guardarUsuario(@RequestBody EntidadUsuario entidadUsuario){

        EntidadUsuario entidadUsuarioNuevo =  servicio.guardar(entidadUsuario);

        return new ResponseEntity<EntidadUsuario>(entidadUsuario, HttpStatus.OK);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<EntidadUsuario>> lista() {

        List<EntidadUsuario> lista = servicio.listaUsuario();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<List<EntidadUsuario>>(lista, HttpStatus.OK);
    }

    @GetMapping("/busqueda-id/{id}")
    public ResponseEntity<EntidadUsuario> busquedaPorId(@PathVariable("id")int id){

        EntidadUsuario entidadUsuario =servicio.buscaUsuarioPorId(id);

        return new ResponseEntity<EntidadUsuario>(entidadUsuario, HttpStatus.OK);
    }

    @GetMapping("/busqueda-dni/{dni}")
    public ResponseEntity<EntidadUsuario> busquedaPorDni(@PathVariable("dni")int dni){

        EntidadUsuario entidadUsuario =servicio.buscaUsuarioPorDni(dni);

        return new ResponseEntity<EntidadUsuario>(entidadUsuario, HttpStatus.OK);
    }

    //TESTEOS DE SEGURIDAD
    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/sesion")
    public ResponseEntity<?> detallesSesion(){

        String sesionId="";
        User user=null;             //User viene definido en Spring y representa a un usuario

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
    }

}
