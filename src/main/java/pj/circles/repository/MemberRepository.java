package pj.circles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.circles.domain.Member;

public interface MemberRepository extends JpaRepository<Member,Long> {

}
