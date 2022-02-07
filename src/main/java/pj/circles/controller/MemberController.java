package pj.circles.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pj.circles.domain.Member;
import pj.circles.repository.EmailRepository;
import pj.circles.service.MemberService;

import javax.validation.Valid;

import java.time.LocalDateTime;

import static pj.circles.dto.MemberDTO.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final EmailRepository emailRepository;
    /**
     * 맴버조회
     */
    @GetMapping("/member/{id}")
    public Result memberOne(
            @PathVariable("id") Long id
    ) {
        Member member = memberService.findById(id);
        MemberOneDTO memberOneDTO = new MemberOneDTO(member);
        return new Result(memberOneDTO);
    }

    /**
     *맴버등록
     */
    @PostMapping("/member")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request){

        if(emailRepository.findByEmail(request.getEmail()).isEmpty()){
            return new CreateMemberResponse(-1L);
        }
        else {
            if (emailRepository.findByEmail(request.getEmail()).get().isCheck() == true) {
                return new CreateMemberResponse(
                        memberService.join(request.getNickName(), request.getPassword(), request.getEmail()));
            } else
                return new CreateMemberResponse(-1L);
        }
    }
    /**
     *맴버삭제
     */
    @DeleteMapping("/member/{id}")
    public DeleteMember deleteMember(
            @PathVariable("id")Long id
    ){
        memberService.deleteMember(id);
        return new DeleteMember(id);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
