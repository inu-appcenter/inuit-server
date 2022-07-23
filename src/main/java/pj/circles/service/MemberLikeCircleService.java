package pj.circles.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pj.circles.domain.Circle;
import pj.circles.domain.Member;
import pj.circles.domain.MemberLikeCircle;
import pj.circles.repository.MemberLikeCircleRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberLikeCircleService {

    @Autowired
    MemberLikeCircleRepository memberLikeCircleRepository;

    @Transactional
    public Long join(Member member,Circle circle){
        MemberLikeCircle memberLikeCircle = new MemberLikeCircle(member,circle);
        memberLikeCircleRepository.save(memberLikeCircle);
        return memberLikeCircle.getId();
    }

    @Transactional
    public void deleteById(Long id){
        memberLikeCircleRepository.deleteById(id);
    }

    public MemberLikeCircle findById(Long id){
        return memberLikeCircleRepository.findById(id).orElseThrow(()->new NullPointerException("없는 좋아요입니다"));
    }



}
