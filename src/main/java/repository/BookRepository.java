package repository;

import model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Usuario,Long> {
}
