package pj.circles.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pj.circles.domain.Circle;
import pj.circles.domain.Member;
import pj.circles.domain.MemberLikeCircle;
import pj.circles.jwt.JwtTokenProvider;
import pj.circles.service.CircleService;
import pj.circles.service.EmailService;
import pj.circles.service.MemberLikeCircleService;
import pj.circles.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.stream.Collectors;
import static pj.circles.dto.MemberDto.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CircleService circleService;
    private final MemberLikeCircleService memberLikeCircleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 맴버조회
     */
    @GetMapping("/member")
    public Result memberOne(
            HttpServletRequest request
    ) {
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("X-AUTH-TOKEN")));
        Member member = memberService.findById(userPk);
        MemberOneDto memberOneDTO = new MemberOneDto(member);
        return new Result(memberOneDTO);
    }
    /**
     * 맴버들조회(관리자)
     */
    @GetMapping("/admin/members")
    public Result members(

    ) {

        List<Member> members = memberService.findAll();
        List<MemberOneDto> collect = members.stream()
                .map(o -> new MemberOneDto(o)).collect(Collectors.toList());
        return new Result(collect);
    }

    /**
     * 맴버등록
     */
    @PostMapping("/register")
    public ReturnMemberIdResponse saveMember(@RequestBody @Valid CreateMemberRequest request, BindingResult result) {

        if(result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError fieldError = fieldErrors.get(0);
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            throw new IllegalArgumentException(field+":"+defaultMessage);
        }

        if (emailService.findByEmailOp(request.getEmail()).isEmpty()) {
            throw new NoSuchElementException();
        } else {
            if (emailService.findByEmail(request.getEmail()).isCheck() == true
            && emailService.findByEmail(request.getEmail()).isJoin()==false) {
                if(memberService.findByNickName(request.getNickName()).isEmpty()) {
                    emailService.findByEmail(request.getEmail()).isJoined();
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
        emailService.deleteById(emailService.findByEmail(email).getId());
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
        if(memberService.findByNickName(request.getNickName()).isEmpty()) {
            memberService.updateMember(userPk,passwordEncoder.encode(request.getPassword()), request.getNickName());
            return new ReturnMemberIdResponse(userPk);
        }
        else {
            if(memberService.findById(userPk).getNickName().equals(request.getNickName())) {
                memberService.updateMember(userPk,passwordEncoder.encode(request.getPassword()), request.getNickName());
                return new ReturnMemberIdResponse(userPk);
            }
            else {
                throw new IllegalArgumentException("이미있는 닉네임입니다");
            }
        }


    }

    /**
     * 맴버삭제
     */
    @DeleteMapping("/member")
    public DeleteMember deleteMember(
            HttpServletRequest request
    ) {
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("X-AUTH-TOKEN")));
        String email = memberService.findById(userPk).getEmail();
        emailService.deleteById(emailService.findByEmail(email).getId());
        memberService.deleteMember(userPk);
        return new DeleteMember(userPk);
    }
    /**
     * 로그인
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginMemberRequest member) {
        Member mem = memberService.findByEmail(member.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(member.getPassword(), mem.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");}
        Iterator<String> iter =mem.getRoles().iterator();
        List<String> roles=new ArrayList<>();
        while (iter.hasNext()) {
            roles.add(iter.next());
        }
        return jwtTokenProvider.createToken(mem.getUsername(), roles);
    }

    /**
     * 관심 동아리 등록
     */
    @PostMapping("/member/circle/{circleId}")
    public ReturnMemberIdResponse saveLike(HttpServletRequest request,@PathVariable("circleId") Long circleId){
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("X-AUTH-TOKEN")));
        Circle circle= circleService.findById(circleId);
        Member member= memberService.findById(userPk);
        memberLikeCircleService.join(member,circle);
        return new ReturnMemberIdResponse(userPk);
    }
    /**
     * 관심 동아리 삭제
     */
    @DeleteMapping("/memberLikeCircle/{memberLikeCircle}")
    public ReturnMemberIdResponse deleteLike(HttpServletRequest request,@PathVariable("memberLikeCircle") Long memberLikeCircle) throws AccessDeniedException {
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("X-AUTH-TOKEN")));

        if(memberLikeCircleService.findById(memberLikeCircle).getMember().getId()==userPk) memberLikeCircleService.deleteById(memberLikeCircle);
        else throw  new AccessDeniedException("403 return");

        return new ReturnMemberIdResponse(userPk);
    }
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
