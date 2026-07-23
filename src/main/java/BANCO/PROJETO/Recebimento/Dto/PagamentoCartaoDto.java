package BANCO.PROJETO.Recebimento.Dto;

import BANCO.PROJETO.Transacao.Enum.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Entrada de um pagamento de cartão que chega para o banco RECEBER.
 *
 * O pagador é externo (rede de cartão) — por isso aqui não existe conta de
 * origem interna. A validação/cobrança do cartão em si é feita na tua parte;
 * este DTO carrega só o que o recebedor precisa para creditar o beneficiário
 * e registrar a movimentação.
 *
 *  - numeroContaDestino: a conta que VAI RECEBER o dinheiro (lojista/beneficiário)
 *  - valor:              quanto foi pago
 *  - tipo:              DEBITO ou CREDITO (PIX não entra por aqui)
 *  - nomePagador:       nome de quem pagou (vai no comprovante/registro)
 *  - numeroCartao:      opcional — usado só para mascarar no registro (**** 1234)
 */
public record PagamentoCartaoDto(
        @NotBlank String numeroContaDestino,
        @NotNull BigDecimal valor,
        @NotNull TipoTransacao tipo,
        @NotBlank String nomePagador,
        String numeroCartao
) {
}
