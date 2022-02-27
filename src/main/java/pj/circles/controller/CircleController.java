package pj.circles.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pj.circles.domain.Circle;
import pj.circles.domain.CircleCategory;
import pj.circles.domain.CircleDivision;
import pj.circles.domain.Photo;
import pj.circles.file.FileStore;
import pj.circles.jwt.JwtTokenProvider;
import pj.circles.repository.PhotoRepository;
import pj.circles.service.CircleService;
import pj.circles.service.MemberService;
import pj.circles.service.PhotoService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static pj.circles.dto.CircleDTO.*;
//utf8mb4
@RestController
@RequiredArgsConstructor
public class CircleController {
    private final CircleService circleService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final PhotoService photoService;
    private final PhotoRepository photoRepository;
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
    @GetMapping("/circles/{id}")
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
     * 내용조회
     */
    @GetMapping("/circles/name/{name}")
    public Result circleNameOrIntroduce(
            @PathVariable("name") String name
    ) {
        List<Circle> circles = circleService.findByNameOrIntroduce(name,name,name);
        List<CirclesDTO> collect = circles.stream()
                .map(o -> new CirclesDTO(o)).collect(Collectors.toList());
        return new Result(collect);
    }

    /**
     * 등록
     */
    @PostMapping("/circle")
    public ReturnCircleIdResponse saveCircle(@RequestBody @Valid CreateCircleRequest request, HttpServletRequest request2){
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request2.getHeader("X-AUTH-TOKEN")));
        if(memberService.findById(userPk).getCircle()==null) {
            return new ReturnCircleIdResponse(
                    circleService.join(request.getName(), request.getOneLineIntroduce(), request.getIntroduce(),
                            request.getCircleCategory(), request.getCircleDivision(), request.getRecruit(), request.getOpenKakao()
                            , memberService.findById(userPk)));
        }
        else
            throw new IllegalArgumentException("이미 등록한 동아리가 있습니다");
    }
    /**
     * 수정
     */
    @PatchMapping("/circle/{id}")
    public ReturnCircleIdResponse updateCircle(
            @PathVariable("id")Long idParam,@RequestBody @Valid UpdateCircleRequest request, HttpServletRequest request2) throws AccessDeniedException {
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request2.getHeader("X-AUTH-TOKEN")));
        if(circleService.findById(idParam).getMember().equals(memberService.findById(userPk))) {
            Long id = memberService.findById(userPk).getCircle().getId();
            circleService.findById(id).updateCircle(request.getOneLineIntroduce(), request.getIntroduce(), request.getInformation(),
                    request.getCircleDivision(), request.getRecruit(), request.getRecruitStartDate(), request.getRecruitEndDate(),
                    request.getLink(), request.getAddress(), request.getCafeLink(), request.getPhoneNumber());
            return new ReturnCircleIdResponse(id);
        }
        else
            throw  new AccessDeniedException("403 return");
    }
    /**
     * 삭제
     */
    @DeleteMapping("/circle/{id}")
    public DeleteCircle deleteCircle(@PathVariable("id")Long idParam,HttpServletRequest request2) throws AccessDeniedException {
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request2.getHeader("X-AUTH-TOKEN")));
        if(circleService.findById(idParam).getMember().equals(memberService.findById(userPk))) {
            Long id = memberService.findById(userPk).getCircle().getId();
            circleService.deleteCircle(id);
            return new DeleteCircle(id);
        }
        else
            throw  new AccessDeniedException("403 return");
    }
    /**
     * 삭제(관리자)
     */
    @DeleteMapping("/admin/circle/{id}")
    public DeleteCircle deleteCircleRoot(@PathVariable("id")Long id){
        circleService.deleteCircle(id);
        return new DeleteCircle(id);
    }
    /**
     * 수정(관리자)
     */
    @PatchMapping("/admin/circle/{id}")
    public ReturnCircleIdResponse updateCircleRoot(
            @PathVariable("id") Long id, @RequestBody @Valid UpdateCircleRequest request){
        circleService.findById(id).updateCircle(request.getOneLineIntroduce(), request.getIntroduce(), request.getInformation(),
                request.getCircleDivision(), request.getRecruit(), request.getRecruitStartDate(), request.getRecruitEndDate(),
                request.getLink(),request.getAddress(), request.getCafeLink(), request.getPhoneNumber());
        return new ReturnCircleIdResponse(id);

    }
    /**
     * 사진등록
     */
    @PostMapping("/user/circle/{id}/photo")
    public ResponseEntity upload(@PathVariable("id") Long id,@RequestPart MultipartFile file,HttpServletRequest request2) throws IOException {
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request2.getHeader("X-AUTH-TOKEN")));
        if(circleService.findById(id).getMember().equals(memberService.findById(userPk))) {
            Long pid = photoService.join(file, circleService.findById(id));
            return new ResponseEntity(pid,HttpStatus.OK);
        }
        else
            throw  new AccessDeniedException("403 return");
    }
    /**
     * 사진조회
     */
    @GetMapping("/circles/view/photo/{id}")
    public ResponseEntity<Resource> showImage(@PathVariable("id") Long id){
        String imageRoot = photoService.findPhoto(id);
        Resource resource = new FileSystemResource(imageRoot);
        HttpHeaders headers = new HttpHeaders();
        Path filePath = null;
        try{
            filePath = Paths.get(imageRoot);
            headers.add("Content-Type", Files.probeContentType(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
    }
    /**
     * 사진삭제
     */
    @DeleteMapping("/user/circle/{circleId}/delete/photo/{photoId}")
    public ResponseEntity unload(@PathVariable("circleId") Long circleId,@PathVariable("photoId") Long photoId,HttpServletRequest request2) throws IOException {
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request2.getHeader("X-AUTH-TOKEN")));
        if(circleService.findById(circleId).getMember().equals(memberService.findById(userPk))) {
            photoService.deletePhoto(photoRepository.findById(photoId).get());
            return new ResponseEntity(HttpStatus.OK);
        }
        else
            throw  new AccessDeniedException("403 return");
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
