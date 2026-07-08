package BANCO.PROJETO.Service;



import BANCO.PROJETO.Exception.Excepitons.ContaNaoEncontradaExcepiton;
import BANCO.PROJETO.Exception.Excepitons.UsuarioExistenteException;

import BANCO.PROJETO.Model.Usuario;

import BANCO.PROJETO.Repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    public Optional<Usuario> buscarContaPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }



}
