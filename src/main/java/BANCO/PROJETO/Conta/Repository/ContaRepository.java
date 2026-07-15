package BANCO.PROJETO.Conta.Repository;

import BANCO.PROJETO.Conta.Entity.Conta;
import BANCO.PROJETO.Enum.TipoDeChave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContaRepository extends JpaRepository<Conta, UUID> {

    Optional<Conta> findByNumeroConta(String numeroConta);

}
