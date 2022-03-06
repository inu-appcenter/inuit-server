package pj.circles.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pj.circles.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
}
