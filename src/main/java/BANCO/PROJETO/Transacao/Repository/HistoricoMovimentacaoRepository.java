package BANCO.PROJETO.Transacao.Repository;

import BANCO.PROJETO.Transacao.Entity.HistoricoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoricoMovimentacaoRepository extends JpaRepository<HistoricoMovimentacao, UUID> {

    boolean existsByNumeroProcesso(String numeroProcesso);
}
