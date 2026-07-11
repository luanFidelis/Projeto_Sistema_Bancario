package BANCO.PROJETO.Repository;

import BANCO.PROJETO.Model.HistoricoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoricoMovimentacaoRepository extends JpaRepository<HistoricoMovimentacao, UUID> {

    boolean existsByNumeroProcesso(String numeroProcesso);
}
