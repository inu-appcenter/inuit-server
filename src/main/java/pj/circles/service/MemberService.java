package pj.circles.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pj.circles.domain.Member;
import pj.circles.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;

    @Transactional
    public Long join(String nickName, String passWord, String email){
        Member member = new Member(nickName, passWord, email);
        memberRepository.save(member);
        return member.getId();
    }

    public Member findById(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(()->new NullPointerException("없는값입니다"));
    }

    @Transactional
    public void deleteMember(Long memberId){
        memberRepository.delete(findById(memberId));
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Optional<Member> findByNickName(String name){
        return memberRepository.findByNickName(name);
    }


    public Optional<Member> findByEmail(String email){
        return memberRepository.findByEmail(email);
    }
    @Transactional
    public void updateMember(Long memberId,String password,String nickName){
        Member member = memberRepository.findById(memberId).get();
        member.updatePassWord(password);
        member.updateNickName(nickName);

    }
}
