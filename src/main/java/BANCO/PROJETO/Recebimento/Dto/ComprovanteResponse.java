package BANCO.PROJETO.Recebimento.Dto;

import BANCO.PROJETO.Transacao.Enum.SituacaoTransacao;
import BANCO.PROJETO.Transacao.Enum.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Comprovante devolvido depois que o banco recebe o pagamento do cartão.
 * saldoAtual mostra o dinheiro já "guardado" na conta que recebeu.
 */
public record ComprovanteResponse(
        SituacaoTransacao situacao,
        TipoTransacao tipo,
        BigDecimal valor,
        String numeroContaDestino,
        String nomeRecebedor,
        String pagador,
        BigDecimal saldoAtual,
        String numeroProcesso,
        LocalDateTime dataHora,
        String mensagem
) {
}
