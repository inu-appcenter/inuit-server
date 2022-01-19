package pj.circles.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pj.circles.domain.Circle;
import pj.circles.domain.CircleCategory;
import pj.circles.domain.CircleDivision;
import pj.circles.service.CircleService;

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
    @GetMapping("/circle/category/{name}")
    public Result circleCategory(
            @PathVariable("name") CircleCategory category
    ) {
        List<Circle> circles = circleService.findByCircleCategory(category);
        List<CirclesDTO> collect = circles.stream()
                .map(o -> new CirclesDTO(o)).collect(Collectors.toList());
        return new Result(collect);
    }

    /**
     * 분류조회
     */
    @GetMapping("/circle/category/{name}")
    public Result circleDivision(
            @PathVariable("name") CircleDivision division
    ) {
        List<Circle> circles = circleService.findByCircleDivision(division);
        List<CirclesDTO> collect = circles.stream()
                .map(o -> new CirclesDTO(o)).collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
