package pj.circles.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pj.circles.domain.Member;
import pj.circles.service.MemberService;

import javax.validation.Valid;

import static pj.circles.dto.MemberDTO.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

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
        return new CreateMemberResponse(
                memberService.join(request.getNickName(), request.getPassword(), request.getEmail()));
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
