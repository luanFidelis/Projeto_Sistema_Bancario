package BANCO.PROJETO.Transacao.Entity;

import BANCO.PROJETO.Transacao.Pix.Enum.SituacaoPix;
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
                                 SituacaoPix situacao,
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
    SituacaoPix situacao;
    @Column(nullable = false)
    String numeroProcesso;
}
