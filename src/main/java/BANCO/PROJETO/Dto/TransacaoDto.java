package BANCO.PROJETO.Dto;

import BANCO.PROJETO.Enum.TipoDeChave;
import BANCO.PROJETO.Enum.TipoTransacao;

import java.math.BigDecimal;

public record TransacaoDto (
        String numeroContaOrigem,
        TipoDeChave tipoDeChave,
        String numeroContaDestino,
        TipoTransacao tipoTransacao,
        BigDecimal valor

        ) {
}
