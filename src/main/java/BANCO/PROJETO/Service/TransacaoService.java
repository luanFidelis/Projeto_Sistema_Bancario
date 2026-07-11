package BANCO.PROJETO.Service;

import BANCO.PROJETO.Dto.TransacaoDto;
import BANCO.PROJETO.Enum.SituacaoPix;
import BANCO.PROJETO.Enum.TipoTransacao;
import BANCO.PROJETO.Exception.Excepitons.AcaoNaoRealizada;
import BANCO.PROJETO.Exception.Excepitons.ContaNaoEncontradaExcepiton;
import BANCO.PROJETO.Exception.Excepitons.SaldoInsuficienteException;
import BANCO.PROJETO.Exception.Excepitons.ValorInvalidoException;
import BANCO.PROJETO.Model.Conta;
import BANCO.PROJETO.Model.HistoricoMovimentacao;

import BANCO.PROJETO.Model.Transacao;
import BANCO.PROJETO.Repository.ChavePixRepository;
import BANCO.PROJETO.Repository.ContaRepository;
import BANCO.PROJETO.Repository.HistoricoMovimentacaoRepository;
import BANCO.PROJETO.Repository.TransacaoRepository;
import jakarta.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final ChavePixRepository chavePixRepository;
    private final HistoricoMovimentacaoRepository historicoMovimentacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository,
                            ContaRepository contaRepository,
                            ChavePixRepository chavePixRepository,
                            HistoricoMovimentacaoRepository historicoMovimentacaoRepository) {

        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
        this.chavePixRepository = chavePixRepository;
        this.historicoMovimentacaoRepository = historicoMovimentacaoRepository;
    }




    @Transactional
    public TransacaoDto fazerTransacao(TransacaoDto transacaoDto) {

        String numeroGerado = gerarNumeroProcesso();

        Conta contaQueVaiEnviar = contaRepository.findByNumeroConta(transacaoDto.numeroContaOrigem())
                .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Conta Origem não encontrada"));

        Conta contaQueVaiReceber = contaRepository.findByNumeroConta(transacaoDto.numeroContaDestino())
                .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Conta Destino não encontrada"));

        return switch (transacaoDto.tipoTransacao()) {

            case PIX -> fazerTransacaoPix(
                    contaQueVaiEnviar,
                    contaQueVaiReceber,
                    transacaoDto.valor(),
                    numeroGerado,
                    transacaoDto.tipoTransacao()
            );

            default -> throw new AcaoNaoRealizada("Não foi possível realizar a ação");
        };
    }



    protected TransacaoDto fazerTransacaoPix(Conta contaOrigem, Conta contaDestino, BigDecimal valor, String numeroGerado, TipoTransacao tipoTransacao) {


        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("Valor inválido");
        }

        if (contaOrigem.getSaldo().compareTo(valor) < 0) {
            throw new AcaoNaoRealizada("Saldo insuficiente");
        }

        LocalDateTime horaTransacao = LocalDateTime.now();

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        Transacao transacao = transacaoRepository.save(
                new Transacao(
                        contaOrigem.getUsuario().getCpf(),
                        contaOrigem.getUsuario().getNome(),
                        contaDestino.getUsuario().getCpf(),
                        contaDestino.getUsuario().getNome(),
                        valor,
                        horaTransacao,
                        SituacaoPix.APROVADO,
                        TipoTransacao.PIX
                )
        );

        HistoricoMovimentacao historico = new HistoricoMovimentacao(
                contaOrigem.getNumeroConta(),
                contaDestino.getNumeroConta(),
                horaTransacao,
                SituacaoPix.APROVADO,
                numeroGerado
        );

        historicoMovimentacaoRepository.save(historico);

        return new TransacaoDto(
                contaOrigem.getNumeroConta(),
                contaDestino.getNumeroConta(),
                tipoTransacao,
                valor


        );
    }


    private String gerarNumeroProcesso() {

        String numeroProcesso;
        do {
            numeroProcesso = "NP-" + System.currentTimeMillis();

        } while (historicoMovimentacaoRepository.existsByNumeroProcesso(numeroProcesso));


        return numeroProcesso;
    }

}
