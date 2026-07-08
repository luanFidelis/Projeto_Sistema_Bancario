package BANCO.PROJETO.Dto;

import BANCO.PROJETO.Enum.TipoDeChave;
import BANCO.PROJETO.Enum.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransacaoDto (

        @NotBlank
        String numeroContaOrigem,
        @NotBlank
        TipoDeChave tipoDeChave,
        @NotBlank
        String numeroContaDestino,
        @NotBlank
        TipoTransacao tipoTransacao,
        @NotNull
        BigDecimal valor

        ) {
}
