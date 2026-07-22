package BANCO.PROJETO.Cartao.Repository;

import BANCO.PROJETO.Cartao.Entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface CartaoRepository extends JpaRepository<Cartao, UUID> {

    boolean existsByNumeroCartao(String numeroCartao);
}
