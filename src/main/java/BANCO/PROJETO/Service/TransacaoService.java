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
import BANCO.PROJETO.Repository.TransacaoRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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

        Conta contaQueVaiEnviar = contaRepository.findByNumeroConta(transacaoDto.numeroContaOrigem())
                .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Conta Origem não encontrada"));


        Conta contaQueVaiReceber = contaRepository.findByNumeroConta(transacaoDto.numeroContaDestino())
                .orElseThrow(() -> new ContaNaoEncontradaExcepiton("Conta Destino não encontrada"));


        String numeroGerado = gerarNumeroProcesso();

        if(contaQueVaiEnviar.getSaldo().compareTo(transacaoDto.valor()) < 0)
            throw new SaldoInsuficienteException("Saldo insuficiente");

        if(transacaoDto.valor().compareTo(BigDecimal.ZERO) <= 0)
            throw new ValorInvalidoException("Valor insuficiente");

        boolean retorno = false;
        LocalDateTime horaTransacao = LocalDateTime.now();
        switch (transacaoDto.tipoTransacao()) {
            case PIX : retorno = fazerPix(contaQueVaiEnviar, contaQueVaiReceber, transacaoDto.valor());
            break;
            case CREDITO:
            break;
            case DEBITO:
                break;

            default: throw new AcaoNaoRealizada("Metodo pix não encontrado, entre em contato com o suporte");
        }

        if(!retorno){
            throw new AcaoNaoRealizada("Ação nao realizada, entre em contado com o suporte");
        }


        Transacao transacao = new Transacao(
                contaQueVaiEnviar.getUsuario().getCpf(),
                contaQueVaiEnviar.getUsuario().getNome(),
                contaQueVaiReceber.getUsuario().getCpf(),
                contaQueVaiReceber.getUsuario().getNome(),
                transacaoDto.valor(),
                horaTransacao,
                SituacaoPix.APROVADO,
                transacaoDto.tipoTransacao()

                );
        transacaoRepository.save(transacao);

        HistoricoMovimentacao historicoMovimentacao = new HistoricoMovimentacao(contaQueVaiEnviar.getNumeroConta(), contaQueVaiReceber.getNumeroConta(),horaTransacao, SituacaoPix.APROVADO, numeroGerado);


    }

    private boolean fazerPix(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));


        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);
        return true;

    }

    private String gerarNumeroProcesso() {

        String numeroProcesso;
        do {
            numeroProcesso = "NP-" + System.currentTimeMillis();

        } while (chavePixRepository.existsByNumeroProcesso(numeroProcesso));


        return numeroProcesso;
    }

}
