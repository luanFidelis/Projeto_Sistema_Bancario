package BANCO.PROJETO.Transacao.TipoDeTransacao.Pix.Repository;

import BANCO.PROJETO.Transacao.TipoDeTransacao.Pix.Entity.ChavePix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChavePixRepository extends JpaRepository<ChavePix, UUID> {

}
