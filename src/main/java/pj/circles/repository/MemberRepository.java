package pj.circles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.circles.domain.Member;
import java.util.Optional;

public interface MemberRepository extends BaseEntityRepository<Member> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickName(String nickName);
}
