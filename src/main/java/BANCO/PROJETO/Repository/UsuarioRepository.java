package BANCO.PROJETO.Repository;

import BANCO.PROJETO.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByCpf(String cpf);
}
