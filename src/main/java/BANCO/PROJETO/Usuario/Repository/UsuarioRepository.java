package BANCO.PROJETO.Usuario.Repository;

import BANCO.PROJETO.Usuario.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByEmail(String email);
}
