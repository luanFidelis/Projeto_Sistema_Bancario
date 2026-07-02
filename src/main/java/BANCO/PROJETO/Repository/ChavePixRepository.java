package BANCO.PROJETO.Repository;

import BANCO.PROJETO.Model.ChavePix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChavePixRepository extends JpaRepository<ChavePix, UUID> {

boolean numeroProcessoExiste(String numeroProcesso);
}
