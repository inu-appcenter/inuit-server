package pj.circles.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pj.circles.domain.Member;
import pj.circles.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Transactional
    public Long join(String nickName, String passWord, String email){
        Member member = new Member(nickName, passWord, email);
        memberRepository.save(member);
        return member.getId();
    }

    public Member findById(Long memberId){
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void deleteMember(Long memberId){
        memberRepository.delete(findById(memberId));
    }

    @Transactional
    public void updateMember(Long memberId,String password){
        Member member = memberRepository.findById(memberId).get();
        member.updatePassWord(password);

    }
}
