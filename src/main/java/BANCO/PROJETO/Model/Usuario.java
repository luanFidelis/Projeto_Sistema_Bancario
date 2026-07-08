package BANCO.PROJETO.Model;

import BANCO.PROJETO.Enum.Ativo;
import BANCO.PROJETO.Enum.TipoDeConta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "usuario")

public class Usuario {


public Usuario(){

}

public Usuario(String nome, String email, String cpf, Ativo ativo, TipoDeConta tipoDeconta, String numeroCelular  ) {
 this.nome = nome;
 this.email = email;
this.cpf = cpf;
 this.ativo = ativo;
 this.tipoDeconta = tipoDeconta;
 this.numeroCelular = numeroCelular;
}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Ativo ativo;

    @Enumerated(EnumType.STRING)
    private TipoDeConta tipoDeconta;

    @Column(nullable = false, unique = true, length = 14)
    private String numeroCelular;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Conta> conta;





}
