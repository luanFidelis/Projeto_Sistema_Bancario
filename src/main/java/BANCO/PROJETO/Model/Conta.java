package BANCO.PROJETO.Model;

import BANCO.PROJETO.Enum.TipoDeConta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "Conta")
public class Conta {



 @Id
 @GeneratedValue(strategy = GenerationType.UUID)
 private UUID id;

 @Column(nullable = false, unique = true, length = 14)
 private String numeroConta;

@Column(nullable = false)
private String agencia;

private BigDecimal saldo = BigDecimal.ZERO;

@Enumerated(EnumType.STRING)
private TipoDeConta tipoDeConta;


//mappedBy = "conta" serve para dizer quem é o dono das chaves, dentro de Chave pix foi adicionado Conta conta
//cascade = CascadeType.All serve para dizer que tudo o que acontecer com a conta acontece com ChavePix
//fetch = FetchType.LAZY serve para "isolar" a entity de chavePix e não ser puxada as chaves pix caso eu de um get em Conta
@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private List<ChavePix> chavePix =  new ArrayList<>();

@ManyToMany(fetch = FetchType.EAGER)
@JoinColumn(name = "usuario_id")
private Usuario usuario;

}
