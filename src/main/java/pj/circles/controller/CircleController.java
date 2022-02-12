package pj.circles.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pj.circles.domain.Circle;
import pj.circles.domain.CircleCategory;
import pj.circles.domain.CircleDivision;
import pj.circles.service.CircleService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static pj.circles.dto.CircleDTO.*;

@RestController
@RequiredArgsConstructor
public class CircleController {
    private final CircleService circleService;

    /**
     * 전체조회
     */
    @GetMapping("/circles")
    public Result circlesAll() {
        List<Circle> circles = circleService.findAll();
        List<CirclesDTO> collect = circles.stream()
                .map(o -> new CirclesDTO(o)).collect(Collectors.toList());
        return new Result(collect);
    }

    /**
     * 단건조회
     */
    @GetMapping("/circle/{id}")
    public Result circleOne(
            @PathVariable("id") Long id
    ) {
        Circle circle = circleService.findById(id);
        CircleOneDTO circleOneDTO = new CircleOneDTO(circle);
        return new Result(circleOneDTO);
    }

    /**
     * 카테고리조회
     */
    @GetMapping("/circles/category/{category}")
    public Result circleCategory(
            @PathVariable("category") CircleCategory category
    ) {
        List<Circle> circles = circleService.findByCircleCategory(category);
        List<CirclesDTO> collect = circles.stream()
                .map(o -> new CirclesDTO(o)).collect(Collectors.toList());
        return new Result(collect);
    }

    /**
     * 분류조회
     */
    @GetMapping("/circles/division/{division}")
    public Result circleDivision(
            @PathVariable("division") CircleDivision division
    ) {
        List<Circle> circles = circleService.findByCircleDivision(division);
        List<CirclesDTO> collect = circles.stream()
                .map(o -> new CirclesDTO(o)).collect(Collectors.toList());
        return new Result(collect);
    }
    /**
     * 카테고리분류조회
     */
    @GetMapping("/circles/category/{category}/division/{division}")
    public Result circleCategoryAndDivision(
            @PathVariable("category") CircleCategory category,
            @PathVariable("division") CircleDivision division
    ) {
        List<Circle> circles = circleService.findByCircleCategoryAndCircleDivision(category,division);
        List<CirclesDTO> collect = circles.stream()
                .map(o -> new CirclesDTO(o)).collect(Collectors.toList());
        return new Result(collect);
    }

    /**
     * 등록
     */
    @PostMapping("/circle")
    public ReturnCircleIdResponse saveCircle(@RequestBody @Valid CreateCircleRequest request){
        return new ReturnCircleIdResponse(
                circleService.join(request.getName(), request.getOneLineIntroduce(), request.getIntroduce(),
                        request.getCircleCategory(),request.getCircleDivision(),request.getRecruit()));
    }
    /**
     * 수정
     */
    @PatchMapping("/circle/{id}")
    public ReturnCircleIdResponse updateCircle(
            @PathVariable("id") Long id, @RequestBody @Valid UpdateCircleRequest request){
        circleService.findById(id).updateCircle(request.getOneLineIntroduce(), request.getIntroduce(), request.getInformation(),
                request.getCircleDivision(), request.getRecruit(), request.getRecruitStartDate(), request.getRecruitEndDate(),
                request.getLink(),request.getAddress(), request.getCafeLink(), request.getPhoneNumber());
        return new ReturnCircleIdResponse(id);

    }
    /**
     * 삭제
     */
    @DeleteMapping("/circle/{id}")
    public DeleteCircle deleteCircle(@PathVariable("id")Long id){
        circleService.deleteCircle(id);
        return new DeleteCircle(id);
    }
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
