package BANCO.PROJETO.Transacao.Pix.Entity;


import BANCO.PROJETO.Conta.Entity.Conta;
import BANCO.PROJETO.Transacao.Pix.Enum.TipoDeChavePix;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "ChavePix")

public class ChavePix {

    public ChavePix() {

    }
    public ChavePix(TipoDeChavePix tipoDeChave, String chavePix) {
        this.tipoDeChave = tipoDeChave;
        this.chavePix = chavePix;
//
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 14)
    private String chavePix;
    private String numeroProcesso;
   @Enumerated(EnumType.STRING)
    private TipoDeChavePix tipoDeChave;

   private boolean situacaoChave = true;

   //@ManyToOne significa que Cada Chave Pix pertence a uma única Conta.
   // Essa relação é armazenada na coluna conta_id da tabela chave_pix
   //lazy serve para Conta não ser puxado junto caso de um get ChavePix, eu poderia usar o EAGER caso quisesse que tudo fosse puxado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id")
    private Conta conta;
}
