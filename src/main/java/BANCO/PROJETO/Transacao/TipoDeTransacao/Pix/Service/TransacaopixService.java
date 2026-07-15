package BANCO.PROJETO.Transacao.TipoDeTransacao.Pix.Service;

import BANCO.PROJETO.Conta.Entity.Conta;
import BANCO.PROJETO.Conta.Repository.ContaRepository;
import BANCO.PROJETO.Exception.Excepitons.AcaoNaoRealizada;
import BANCO.PROJETO.Exception.Excepitons.ValorInvalidoException;
import BANCO.PROJETO.Transacao.Dto.TransacaoDto;
import BANCO.PROJETO.Transacao.Entity.HistoricoMovimentacao;
import BANCO.PROJETO.Transacao.Entity.Transacao;
import BANCO.PROJETO.Transacao.Repository.HistoricoMovimentacaoRepository;
import BANCO.PROJETO.Transacao.Repository.TransacaoRepository;
import BANCO.PROJETO.Transacao.TipoDeTransacao.Enum.TipoTransacao;
import BANCO.PROJETO.Transacao.TipoDeTransacao.Pix.Enum.SituacaoPix;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransacaopixService {
    private final TransacaoRepository transacaoRepository;
    private final HistoricoMovimentacaoRepository historicoMovimentacaoRepository;
    private final ContaRepository contaRepository;
    public TransacaopixService() {


        this.transacaoRepository = transacaoRepository;
        this.historicoMovimentacaoRepository = historicoMovimentacaoRepository;
        this.contaRepository = contaRepository;
    }

    public TransacaoDto fazerTransacaoPix(Conta contaOrigem, Conta contaDestino, BigDecimal valor, String numeroGerado, TipoTransacao tipoTransacao) {


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

}
