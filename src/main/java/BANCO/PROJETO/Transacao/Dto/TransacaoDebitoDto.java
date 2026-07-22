package BANCO.PROJETO.Transacao.Dto;

import BANCO.PROJETO.Transacao.Enum.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransacaoDebitoDto(@NotBlank
                                 String numeroContaOrigem,
                                 @NotBlank
                                 String numeroContaDestino,
                                 @NotNull
                                 BigDecimal valor) {
}
