package pj.circles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.circles.domain.Email;
import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email,Long> {
    Optional<Email> findByEmail(String email);
}
