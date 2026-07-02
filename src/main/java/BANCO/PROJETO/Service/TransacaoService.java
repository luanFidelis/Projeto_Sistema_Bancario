package BANCO.PROJETO.Service;

import BANCO.PROJETO.Dto.TransacaoDto;
import BANCO.PROJETO.Exception.Excepitons.ContaNaoEncontradaExcepiton;
import BANCO.PROJETO.Exception.Excepitons.SaldoInsuficienteException;
import BANCO.PROJETO.Exception.Excepitons.ValorInvalidoException;
import BANCO.PROJETO.Model.Conta;
import BANCO.PROJETO.Model.Transacao;
import BANCO.PROJETO.Repository.ChavePixRepository;
import BANCO.PROJETO.Repository.ContaRepository;
import BANCO.PROJETO.Repository.TransacaoRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final ChavePixRepository chavePixRepository;

    public TransacaoService(TransacaoRepository transacaoRepository,
                            ContaRepository contaRepository,
                            ChavePixRepository chavePixRepository) {

        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
        this.chavePixRepository = chavePixRepository;
    }

    @Transactional
    public void fazerTransacaoPix(TransacaoDto transacaoDto) {

        Conta contaQueVaiEnviarPix = contaRepository.findByNumeroConta(transacaoDto.numeroContaOrigem())
                .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Conta Origem não encontrada"));


        Conta contaQueVaiReceberPix = contaRepository.findByNumeroConta(transacaoDto.numeroContaDestino())
                .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Conta Destino não encontrada"));


        if(contaQueVaiEnviarPix.getSaldo().compareTo(transacaoDto.valor()) < 0)
            throw new SaldoInsuficienteException("Saldo insuficiente");

        if(transacaoDto.valor().compareTo(BigDecimal.ZERO) <= 0)
            throw new ValorInvalidoException("Valor insuficiente");

        switch (transacaoDto.tipoTransacao()) {
            case PIX -> fazerPix(contaQueVaiEnviarPix, contaQueVaiReceberPix, transacaoDto.valor());

        }
        String numeroProcesso;
        do {
             numeroProcesso = "NP-" + System.currentTimeMillis();

        } while (chavePixRepository.numeroProcessoExiste(numeroProcesso));



    }

    private void fazerPix(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {

        contaOrigem.setSaldo(
                contaOrigem.getSaldo().subtract(valor)
        );

        contaDestino.setSaldo(
                contaDestino.getSaldo().add(valor)
        );


        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);


    }

}
