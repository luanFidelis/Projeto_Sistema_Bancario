package BANCO.PROJETO.Transacao.Entity;

import BANCO.PROJETO.Transacao.TipoDeTransacao.Pix.Enum.SituacaoPix;
import BANCO.PROJETO.Transacao.TipoDeTransacao.Enum.TipoTransacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "Transacao")

public class Transacao {

    public Transacao() {

    }
    public Transacao(String cpfOrigem,
                     String nomeOrigem,
                     String cpfDestino,
                     String nomeDestino,
                     BigDecimal valor,
                     LocalDateTime dataHora,
                     SituacaoPix situacaoPix,
                     TipoTransacao tipoTransacao) {


        this.cpfOrigem = cpfOrigem;
        this.nomeOrigem = nomeOrigem;
        this.cpfDestino = cpfDestino;
        this.nomeDestino = nomeDestino;
        this.valor = valor;
        this.dataHora = dataHora;
        this.situacaoPix = situacaoPix;
        this.tipoTransacao = tipoTransacao; }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "valor")
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipoTransacao;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false, length = 14)
    private String numeroContaOrigem;
    @Column(nullable = false, length = 14)
    private String numeroContaDestino;

    @Enumerated(EnumType.STRING)
    private SituacaoPix situacaoPix;

    @Column(nullable = false, length = 14)
   private String cpfOrigem;
    @Column(nullable = false, length = 14)
   private String nomeOrigem;
    @Column(nullable = false, length = 14)
   private String cpfDestino;
    @Column(nullable = false, length = 14)
   private String nomeDestino;
}
