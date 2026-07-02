package BANCO.PROJETO.Service;

import BANCO.PROJETO.Enum.TipoDeChave;
import BANCO.PROJETO.Exception.Excepitons.ContaNaoEncontradaExcepiton;
import BANCO.PROJETO.Model.ChavePix;
import BANCO.PROJETO.Model.Conta;
import BANCO.PROJETO.Model.Usuario;
import BANCO.PROJETO.Repository.ChavePixRepository;
import BANCO.PROJETO.Repository.ContaRepository;
import BANCO.PROJETO.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ContaService {


    private final UsuarioRepository usuarioRepository;
    private final ChavePixRepository chavePixRepository;

    public ContaService(ChavePixRepository chavePixRepository,  UsuarioRepository usuarioRepository) {
        this.chavePixRepository = chavePixRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Optional<Usuario> gerarChavePix(String cpf, TipoDeChave tipoDeChave) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByCpf(cpf);
        if (usuarioOptional.isEmpty()) return Optional.empty();

        Usuario usuario = usuarioOptional.get();

       for(int x = 0; x < usuario.getConta().getChavePix().size(); x++){
           if(usuario.getConta().getChavePix().get(x).getTipoDeChave().equals(tipoDeChave)){
               return Optional.empty();
           }
       }
        ChavePix chavePix = new ChavePix(tipoDeChave, cpf);
        chavePix.setConta(usuario.getConta());
        chavePixRepository.save(chavePix);

        return Optional.of(usuario);


    }
}
