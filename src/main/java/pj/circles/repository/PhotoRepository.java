package pj.circles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.circles.domain.Photo;

public interface PhotoRepository extends JpaRepository<Photo,Long> {
}
