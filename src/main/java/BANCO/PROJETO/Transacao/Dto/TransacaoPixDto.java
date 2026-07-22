package BANCO.PROJETO.Transacao.Dto;


import BANCO.PROJETO.Transacao.Enum.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransacaoPixDto(

        @NotBlank
        String numeroContaOrigem,
        @NotBlank
        String numeroContaDestino,
        @NotBlank
        TipoTransacao tipoTransacao,
        @NotNull
        BigDecimal valor




        ) {
}
