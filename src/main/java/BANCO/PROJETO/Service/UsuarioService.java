package BANCO.PROJETO.Service;


import BANCO.PROJETO.Enum.TipoDeChave;
import BANCO.PROJETO.Exception.Excepitons.ContaNaoEncontradaExcepiton;
import BANCO.PROJETO.Model.Conta;
import BANCO.PROJETO.Model.Usuario;
import BANCO.PROJETO.Repository.ContaRepository;
import BANCO.PROJETO.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
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
        return usuarioRepository.save(usuario);
    }



}
