package BANCO.PROJETO.Cartao.Service;

import BANCO.PROJETO.Cartao.Entity.Cartao;
import BANCO.PROJETO.Cartao.Repository.CartaoRepository;
import BANCO.PROJETO.Exception.Excepitons.ContaNaoEncontradaExcepiton;
import BANCO.PROJETO.Usuario.Entity.Usuario;
import BANCO.PROJETO.Usuario.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import BANCO.PROJETO.Configuration.BcryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CartaoSerivce {
    private CartaoRepository cartaoRepository;
    private UsuarioRepository usuarioRepository;

    public CartaoSerivce(CartaoRepository cartaoRepository, UsuarioRepository usuarioRepository) {
        this.cartaoRepository = cartaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Cartao criarCartao(String cpf, String senha) {
        LocalDate Data = LocalDate.now();
        LocalDate DataEscolhida = Data.plusYears(3);
        Usuario usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Cpf inexistente"));
        String numero;
        do {
            StringBuilder numeroCartao = new StringBuilder();

            for (int i = 0; i < 12; i++) {
                numeroCartao.append(ThreadLocalRandom.current().nextInt(10));
            }
             numero = numeroCartao.toString();
        } while(cartaoRepository.existsByNumeroCartao(numero));

        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvv.append(ThreadLocalRandom.current().nextInt(10));

        }

        String CVV = cvv.toString();


       String senhaEncriptada = BcryptPasswordEncoder.esconderDados(senha);



        Cartao cartao = new Cartao(
                usuario.getNome(),
                numero,
                BigDecimal.valueOf(1000),
                DataEscolhida,
                CVV,
                senhaEncriptada);

        cartaoRepository.save(cartao);

        return cartao;
    }


}
