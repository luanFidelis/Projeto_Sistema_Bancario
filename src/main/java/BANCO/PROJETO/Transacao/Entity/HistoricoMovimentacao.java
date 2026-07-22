package BANCO.PROJETO.Transacao.Entity;

import BANCO.PROJETO.Transacao.Enum.SituacaoTransacao;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "historico_movimetacao")
public class HistoricoMovimentacao {

    public HistoricoMovimentacao() {

    }

    public HistoricoMovimentacao(String contaOrigem,
                                 String contaDestino,
                                 LocalDateTime dataMovimentacao,
                                 SituacaoTransacao situacao,
                                 String numeroProcesso) {

        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.dataMovimentacao = dataMovimentacao;
        this.situacao = situacao;
        this.numeroProcesso = numeroProcesso;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    String contaOrigem;

    @Column(nullable = false)
    String contaDestino;

    @Column(nullable = false)
    LocalDateTime dataMovimentacao;

    @Column(nullable = false)
    SituacaoTransacao situacao;
    @Column(nullable = false)
    String numeroProcesso;
}
