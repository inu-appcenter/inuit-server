package pj.circles.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pj.circles.domain.Member;
import pj.circles.service.MemberService;

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

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
