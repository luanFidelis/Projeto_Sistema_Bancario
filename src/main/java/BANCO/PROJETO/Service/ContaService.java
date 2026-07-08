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
    private final ContaRepository contaRepository;

    public ContaService(ChavePixRepository chavePixRepository,  UsuarioRepository usuarioRepository, ContaRepository contaRepository) {
        this.chavePixRepository = chavePixRepository;
        this.usuarioRepository = usuarioRepository;
        this.contaRepository = contaRepository;
    }

    @Transactional
    public Optional<Usuario> gerarChavePix(String cpf, UUID id, TipoDeChave tipoDeChave) {


        String numeroChavePix = "Gerar Chave Pix";
        Optional<Usuario> usuarioOptional = usuarioRepository.findByCpf(cpf);
        if (usuarioOptional.isEmpty()) return Optional.empty();

        Usuario usuario = usuarioOptional.get();

        Conta contaEncontrada = null;

        for (int y = 0; y < usuario.getConta().size(); y++) {
            Conta conta = usuario.getConta().get(y);

            if (conta.getId().equals(id)) {
                contaEncontrada = conta;
                break;
            }
        }

        if (contaEncontrada == null) {
            return Optional.empty();
        }



        for (int x = 0; x < contaEncontrada.getChavePix().size(); x++) {
            ChavePix chaveExistente = contaEncontrada.getChavePix().get(x);

            if (chaveExistente.getTipoDeChave().equals(tipoDeChave)
                    && chaveExistente.isSituacaoChave()) {
                return Optional.empty();
            }
        }



        if(tipoDeChave.equals(TipoDeChave.ALEATORIA)){

            numeroChavePix = "CG" + System.currentTimeMillis();

        }
        else if(tipoDeChave.equals(TipoDeChave.CPF)){

            numeroChavePix = usuario.getCpf();
        }

        else if(tipoDeChave.equals(TipoDeChave.CELULAR)){

            numeroChavePix = usuario.getNumeroCelular();
        }

        else if(tipoDeChave.equals(TipoDeChave.EMAIL)){

            numeroChavePix = usuario.getEmail();
        }


        ChavePix chavePix = new ChavePix(tipoDeChave, numeroChavePix);
        chavePix.setConta(contaEncontrada);
        chavePix.setSituacaoChave(true);

        chavePixRepository.save(chavePix);

        return Optional.of(usuario);
    }
}
