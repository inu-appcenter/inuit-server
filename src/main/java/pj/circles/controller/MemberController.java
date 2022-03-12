package pj.circles.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pj.circles.domain.Member;

import pj.circles.jwt.JwtTokenProvider;
import pj.circles.repository.EmailRepository;
import pj.circles.repository.MemberRepository;
import pj.circles.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

import static pj.circles.dto.MemberDTO.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final EmailRepository emailRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 맴버조회
     */
    @GetMapping("/member")
    public Result memberOne(
            HttpServletRequest request2
    ) {
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request2.getHeader("X-AUTH-TOKEN")));
        Member member = memberService.findById(userPk);
        MemberOneDTO memberOneDTO = new MemberOneDTO(member);
        return new Result(memberOneDTO);
    }
    /**
     * 맴버들조회(관리자)
     */
    @GetMapping("/admin/members")
    public Result members(

    ) {

        List<Member> members = memberRepository.findAll();
        List<MemberOneDTO> collect = members.stream()
                .map(o -> new MemberOneDTO(o)).collect(Collectors.toList());
        return new Result(collect);
    }

    /**
     * 맴버등록
     */
    @PostMapping("/register")
    public ReturnMemberIdResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {


        if (emailRepository.findByEmail(request.getEmail()).isEmpty()) {
            throw new NoSuchElementException();
        } else {
            if (emailRepository.findByEmail(request.getEmail()).get().isCheck() == true
            && emailRepository.findByEmail(request.getEmail()).get().isJoin()==false) {
                if(memberRepository.findByNickName(request.getNickName()).isEmpty()) {
                    emailRepository.findByEmail(request.getEmail()).get().isJoined();
                    return new ReturnMemberIdResponse(
                            memberService.join(request.getNickName(), passwordEncoder.encode(request.getPassword()), request.getEmail()));
                }
                else {
                    throw new IllegalArgumentException("이미있는 닉네임입니다");
                }
                } else

                throw new NoSuchElementException();
        }

    }

    /**
     * 맴버업데이트(비밀번호변경)(관리자)
     */
    @PatchMapping("/admin/member/{id}")
    public ReturnMemberIdResponse updateMemberRoot(
            @PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request) {
        memberService.updateMember(id,passwordEncoder.encode(request.getPassword()), request.getNickName());
        return new ReturnMemberIdResponse(id);
    }

    /**
     * 맴버삭제(관리자)
     */
    @DeleteMapping("/admin/member/{id}")
    public DeleteMember deleteMemberRoot(
            @PathVariable("id") Long id
    ) {
        String email = memberService.findById(id).getEmail();
        emailRepository.deleteById(emailRepository.findByEmail(email).get().getId());
        memberService.deleteMember(id);
        return new DeleteMember(id);
    }
    /**
     * 맴버업데이트(비밀번호변경)
     */
    @PatchMapping("/member")
    public ReturnMemberIdResponse updateMember(
            HttpServletRequest request2, @RequestBody @Valid UpdateMemberRequest request) {
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request2.getHeader("X-AUTH-TOKEN")));
        if(memberRepository.findByNickName(request.getNickName()).isEmpty()) {
            memberService.updateMember(userPk,passwordEncoder.encode(request.getPassword()), request.getNickName());
            return new ReturnMemberIdResponse(userPk);
        }
        else {
            throw new IllegalArgumentException("이미있는 닉네임입니다");
        }


    }

    /**
     * 맴버삭제
     */
    @DeleteMapping("/member")
    public DeleteMember deleteMember(
            HttpServletRequest request2
    ) {
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request2.getHeader("X-AUTH-TOKEN")));
        String email = memberService.findById(userPk).getEmail();
        emailRepository.deleteById(emailRepository.findByEmail(email).get().getId());
        memberService.deleteMember(userPk);
        return new DeleteMember(userPk);
    }
    /**
     * 로그인
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginMemberRequest member) {
        Member mem = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        Member mem1 = memberRepository.findByEmail(member.getEmail()).get();
        if (!passwordEncoder.matches(member.getPassword(), mem1.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");}
        Iterator<String> iter =mem1.getRoles().iterator();
        List<String> roles=new ArrayList<>();
        while (iter.hasNext()) {
            roles.add(iter.next());
        }
        return jwtTokenProvider.createToken(mem1.getUsername(), roles);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
