package pj.circles.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pj.circles.domain.*;
import pj.circles.domain.enumType.CircleCategory;
import pj.circles.domain.enumType.CircleDivision;
import pj.circles.domain.enumType.PhotoType;
import pj.circles.jwt.JwtTokenProvider;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pj.circles.dto.CircleDto.*;
//utf8mb4
@RestController
@RequiredArgsConstructor
public class CircleController {
    private final CircleService circleService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final PhotoService photoService;

    /**
     * 전체조회
     */
    @GetMapping("/circles")
    public Result circlesAll() {
        List<Circle> circles = circleService.findAll();
        List<CirclesDto> collect = circles.stream()
                .map(o -> new CirclesDto(o)).collect(Collectors.toList());
        return new Result(collect);
    }
    @GetMapping("/circlePage")
    public Result circlePaging(@PageableDefault(size = 5) Pageable pageable){
        List<CirclesDto> collect = circleService.findAllPage(pageable).stream()
                .map(o -> new CirclesDto(o)).collect(Collectors.toList());
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
        CircleOneDto circleOneDTO = new CircleOneDto(circle);
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
        List<CirclesDto> collect = circles.stream()
                .map(o -> new CirclesDto(o)).collect(Collectors.toList());
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
        List<CirclesDto> collect = circles.stream()
                .map(o -> new CirclesDto(o)).collect(Collectors.toList());
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
        List<CirclesDto> collect = circles.stream()
                .map(o -> new CirclesDto(o)).collect(Collectors.toList());
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
        List<CirclesDto> collect = circles.stream()
                .map(o -> new CirclesDto(o)).collect(Collectors.toList());
        return new Result(collect);
    }

    /**
     * 등록
     */
    @PostMapping("/circle")
    public ReturnCircleIdResponse saveCircle(@RequestBody @Valid CreateCircleRequest request, HttpServletRequest request2){

        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request2.getHeader("X-AUTH-TOKEN")));
        if(memberService.findById(userPk).getCircle()==null) {
            LocalDateTime start = null;
            LocalDateTime end = null;
            if(request.getRecruitStartDate()==null) {

            }
            else {
                start = LocalDateTime.parse(request.getRecruitStartDate().get());
                end = LocalDateTime.parse(request.getRecruitEndDate().get());
            }

            return new ReturnCircleIdResponse(
                    circleService.join(request.getName(), request.getOneLineIntroduce(), request.getIntroduce(),
                            request.getCircleCategory(), request.getCircleDivision(), request.getRecruit(), request.getOpenKakao()
                            ,start,end,request.getLink()
                            ,request.getAddress(),request.getCafeLink(),request.getPhoneNumber(),request.getInformation(), memberService.findById(userPk)));
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
            LocalDateTime start = null;
            LocalDateTime end = null;
            if(request.getRecruitStartDate()==null) {

            }
            else {
                start = LocalDateTime.parse(request.getRecruitStartDate().get());
                end = LocalDateTime.parse(request.getRecruitEndDate().get());
            }
            circleService.update(circleService.findById(id),request.getName(), request.getOneLineIntroduce(), request.getIntroduce(), request.getInformation(),
                    request.getCircleDivision(), request.getCircleCategory(),request.getRecruit(),start,end,
                    request.getLink(), request.getAddress(), request.getCafeLink(), request.getPhoneNumber(), request.getOpenKakaoLink());

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
            List<Photo> photos = memberService.findById(userPk).getCircle().getPhotos();
            List<Long> ids = photos.stream().map(o->o.getId()).collect(Collectors.toList());
            for(Long did:ids){
                photoService.deletePhoto(photoService.findById(did));
            }
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
        LocalDateTime start = null;
        LocalDateTime end = null;
        if(request.getRecruitStartDate()==null) {

        }
        else {
            start = LocalDateTime.parse(request.getRecruitStartDate().get());
            end = LocalDateTime.parse(request.getRecruitEndDate().get());
        }
        circleService.update(circleService.findById(id),request.getName(), request.getOneLineIntroduce(), request.getIntroduce(), request.getInformation(),
                request.getCircleDivision(), request.getCircleCategory(),request.getRecruit(),start,end,
                request.getLink(), request.getAddress(), request.getCafeLink(), request.getPhoneNumber(), request.getOpenKakaoLink());
        return new ReturnCircleIdResponse(id);

    }
    /**
     * 사진등록
     */
    @PostMapping("/circle/{id}/photos")
    public ResponseEntity upload2(@PathVariable("id") Long id,@RequestPart List<MultipartFile> files,HttpServletRequest request2) throws IOException {
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request2.getHeader("X-AUTH-TOKEN")));
        List<Long> list = new ArrayList<>();
        if(circleService.findById(id).getMember().equals(memberService.findById(userPk))) {
            for (MultipartFile file : files){
                list.add(photoService.join(file, circleService.findById(id)));
            }

            return new ResponseEntity(list,HttpStatus.OK);
        }
        else
            throw  new AccessDeniedException("403 return");
    }
    /**
     * 메인사진설정
     */
    @PostMapping("/circle/{circleId}/photo/{photoId}")
    public ResponseEntity mainLoad(@PathVariable("circleId") Long id, @PathVariable("photoId") Long photoId, HttpServletRequest request2) throws IOException {
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request2.getHeader("X-AUTH-TOKEN")));
        if(circleService.findById(id).getMember().equals(memberService.findById(userPk))) {
            Optional<Photo> photo1 = circleService.findById(id).getPhotos().stream()
                    .filter(photo -> photo.getPhotoType().equals(PhotoType.메인))
                    .findFirst();
            if(photo1.isEmpty()){
            }
            else{
                photo1.get().setSub();
            }
            photoService.setMain(photoId);
            return new ResponseEntity(photoId,HttpStatus.OK);
        }
        else
            throw  new AccessDeniedException("403 return");
    }
    /**
     * 사진조회
     */
    @GetMapping("/circles/photo/{id}")
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
    @DeleteMapping("/circle/{circleId}/photos/{photoIds}")
    public ResponseEntity unloads(@PathVariable("circleId") Long circleId,@PathVariable List<Long> photoIds,HttpServletRequest request2) throws IOException {
        long userPk = Long.parseLong(jwtTokenProvider.getUserPk(request2.getHeader("X-AUTH-TOKEN")));
        if(circleService.findById(circleId).getMember().equals(memberService.findById(userPk))) {
            for (Long photoId : photoIds) {
                photoService.deletePhoto(photoService.findById(photoId));
            }
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
