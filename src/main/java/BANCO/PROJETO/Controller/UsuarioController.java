package BANCO.PROJETO.Controller;

import BANCO.PROJETO.Dto.LoginDto;
import BANCO.PROJETO.Exception.Excepitons.UsuarioExistenteException;
import BANCO.PROJETO.Model.Usuario;
import BANCO.PROJETO.Service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;

    }

    @PostMapping
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario){

        try {

            usuarioService.registrarConta(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario Registrado");

        } catch (UsuarioExistenteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }
}
