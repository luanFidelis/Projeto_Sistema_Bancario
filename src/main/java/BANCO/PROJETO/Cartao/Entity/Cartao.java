package BANCO.PROJETO.Cartao.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Email
@Table(name = "cartao")
public class Cartao {

    public Cartao() {

    }

    public Cartao(String nomeTitular, String numeroCartao, BigDecimal limite, LocalDate validade, String codigoSeguranca, String senha) {
    this.nomeTitular = nomeTitular;
    this.numeroCartao = numeroCartao;
    this.limite = limite;
    this.validade = validade;
    this.codigoSeguranca = codigoSeguranca;
    this.senha = senha;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nomeTitular;

    @Column(nullable = false, unique = true, length = 16)
    private String numeroCartao;

    private BigDecimal limite;

    private LocalDate validade;

    private String codigoSeguranca;

    @Column(nullable = false,  length = 6, unique = true)
    private String senha;
}



