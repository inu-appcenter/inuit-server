package pj.circles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.circles.domain.MemberLikeCircle;

public interface MemberLikeCircleRepository extends JpaRepository<MemberLikeCircle,Long> {

}
