package BANCO.PROJETO.Model;

import BANCO.PROJETO.Enum.TipoTransacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "Transacao")

public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "valor")
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipoTransacao;

    @Column(nullable = false)
    private LocalDate dataHora;

    @Column(nullable = false, length = 14)
    private String NumeroContaOrigem;
    @Column(nullable = false, length = 14)
    private String NumeroContaDestino;
}
