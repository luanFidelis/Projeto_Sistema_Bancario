package BANCO.PROJETO.Model;

import BANCO.PROJETO.Enum.SituacaoPix;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "historico_movimetacao")
public class HistoricoMovimentação {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    String contaOrigem;

    String contaDestino;

    LocalDateTime dataMovimentacao;

    SituacaoPix situacao;

    String numeroProcesso;
}
