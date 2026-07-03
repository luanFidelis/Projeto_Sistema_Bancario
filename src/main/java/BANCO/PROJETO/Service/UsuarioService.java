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

    public Usuario registrarConta( Usuario usuario ) {

        if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
            throw new UsuarioExistenteException("Cpf ja registrado!");
        }

        return usuarioRepository.save(usuario);
    }

    public Optional<> buscarContaPorCpf(String cpf) {}



}
