package BANCO.PROJETO.Transacao.Service;

import BANCO.PROJETO.Exception.Excepitons.ValorInvalidoException;
import BANCO.PROJETO.Transacao.Dto.TransacaoDebitoDto;
import BANCO.PROJETO.Transacao.Dto.TransacaoPixDto;
import BANCO.PROJETO.Exception.Excepitons.AcaoNaoRealizada;
import BANCO.PROJETO.Exception.Excepitons.ContaNaoEncontradaExcepiton;
import BANCO.PROJETO.Conta.Entity.Conta;


import BANCO.PROJETO.Conta.Repository.ContaRepository;
import BANCO.PROJETO.Transacao.Entity.HistoricoMovimentacao;
import BANCO.PROJETO.Transacao.Entity.Transacao;
import BANCO.PROJETO.Transacao.Enum.TipoTransacao;
import BANCO.PROJETO.Transacao.Enum.SituacaoTransacao;
import BANCO.PROJETO.Transacao.Repository.HistoricoMovimentacaoRepository;

import BANCO.PROJETO.Transacao.Repository.TransacaoRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class TransacaoService {


    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final HistoricoMovimentacaoRepository historicoMovimentacaoRepository;

    public TransacaoService(
            ContaRepository contaRepository,
            HistoricoMovimentacaoRepository historicoMovimentacaoRepository,
            TransacaoRepository transacaoRepository) {


        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
        this.historicoMovimentacaoRepository = historicoMovimentacaoRepository;
    }





    @Transactional
    public TransacaoPixDto TransacaoPix(TransacaoPixDto transacaoPixDto) {


        String numeroGerado = UUID.randomUUID().toString();

        Conta contaOrigem = contaRepository.findByNumeroConta(transacaoPixDto.numeroContaOrigem())
                .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Conta Origem não encontrada"));

        Conta contaDestino = contaRepository.findByNumeroConta(transacaoPixDto.numeroContaDestino())
                .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Conta Destino não encontrada"));

        if (contaOrigem.getNumeroConta().equals(contaDestino.getNumeroConta())) {
            throw new AcaoNaoRealizada("A conta de origem e destino são iguais.");
        }

        if (transacaoPixDto.valor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("Valor inválido");
        }

        if (contaOrigem.getSaldo().compareTo(transacaoPixDto.valor()) < 0) {
            throw new AcaoNaoRealizada("Saldo insuficiente");
        }

        LocalDateTime horaTransacao = LocalDateTime.now();

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(transacaoPixDto.valor()));
        contaDestino.setSaldo(contaDestino.getSaldo().add(transacaoPixDto.valor()));

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

         transacaoRepository.save(
                new Transacao(
                        contaOrigem.getUsuario().getCpf(),
                        contaOrigem.getUsuario().getNome(),
                        contaDestino.getUsuario().getCpf(),
                        contaDestino.getUsuario().getNome(),
                        transacaoPixDto.valor(),
                        horaTransacao,
                        SituacaoTransacao.APROVADO,
                        TipoTransacao.PIX
                )
        );

        HistoricoMovimentacao historico = new HistoricoMovimentacao(
                contaOrigem.getNumeroConta(),
                contaDestino.getNumeroConta(),
                horaTransacao,
                SituacaoTransacao.APROVADO,
                numeroGerado
        );

        historicoMovimentacaoRepository.save(historico);

        return new TransacaoPixDto(
                contaOrigem.getNumeroConta(),
                contaDestino.getNumeroConta(),
                transacaoPixDto.tipoTransacao(),
                transacaoPixDto.valor()
        );
    }



    @Transactional
public TransacaoDebitoDto transacaoDebito (TransacaoDebitoDto transacaoDebitoDto){

        String numeroGerado = UUID.randomUUID().toString();

    Conta contaOrigem = contaRepository.findByNumeroConta( transacaoDebitoDto.numeroContaOrigem())
            .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Conta Origem não encontrada"));

    Conta contaDestino = contaRepository.findByNumeroConta( transacaoDebitoDto.numeroContaDestino())
            .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Conta Destino não encontrada"));

        if (contaOrigem.getNumeroConta().equals(contaDestino.getNumeroConta())) {
            throw new AcaoNaoRealizada("A conta de origem e destino são iguais.");
        }

    if ( transacaoDebitoDto.valor().compareTo(BigDecimal.ZERO) <= 0) {
        throw new ValorInvalidoException("Valor inválido");
    }

    if (contaOrigem.getSaldo().compareTo( transacaoDebitoDto.valor()) < 0) {
        throw new AcaoNaoRealizada("Saldo insuficiente");
    }


    LocalDateTime horaTransacao = LocalDateTime.now();

    contaOrigem.setSaldo(contaOrigem.getSaldo().subtract( transacaoDebitoDto.valor()));
    contaDestino.setSaldo(contaDestino.getSaldo().add( transacaoDebitoDto.valor()));

    contaRepository.save(contaOrigem);
    contaRepository.save(contaDestino);

     transacaoRepository.save(
            new Transacao(
                    contaOrigem.getUsuario().getCpf(),
                    contaOrigem.getUsuario().getNome(),
                    contaDestino.getUsuario().getCpf(),
                    contaDestino.getUsuario().getNome(),
                    transacaoDebitoDto.valor(),
                    horaTransacao,
                    SituacaoTransacao.APROVADO,
                    TipoTransacao.DEBITO
            )
    );

    HistoricoMovimentacao historico = new HistoricoMovimentacao(
            contaOrigem.getNumeroConta(),
            contaDestino.getNumeroConta(),
            horaTransacao,
            SituacaoTransacao.APROVADO,
            numeroGerado
    );

    historicoMovimentacaoRepository.save(historico);

    return new TransacaoDebitoDto(
            contaOrigem.getNumeroConta(),
            contaDestino.getNumeroConta(),
            transacaoDebitoDto.valor()
    );

}





}
