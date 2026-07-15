package BANCO.PROJETO.Usuario.Service;



import BANCO.PROJETO.Exception.Excepitons.UsuarioExistenteException;

import BANCO.PROJETO.Usuario.Entity.Usuario;

import BANCO.PROJETO.Usuario.Repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;

    }

    public Optional<Usuario> registrarConta( Usuario usuario ) {

        if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
            throw new UsuarioExistenteException("Cpf ja registrado!");
        }
        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
            throw new UsuarioExistenteException("Email ja registrado!");

        }

        return Optional.of(usuarioRepository.save(usuario));
    }




}
