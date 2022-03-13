package pj.circles.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import pj.circles.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class CircleDTO {

    @Data
    public static class CirclesDTO {
        private Long id;
        private String name;
        private String oneLineIntroduce;
        private Boolean recruit;
        private LocalDateTime recruitStartDate;
        private LocalDateTime recruitEndDate;
        private Long userId;
        private Long photoId;
        private String introduce;
        private String address;
        private String information;
        private String phone;
        private String nickname;
        private CircleCategory circleCategory;
        private CircleDivision circleDivision;

        public CirclesDTO(Circle circle) {
            id = circle.getId();
            name = circle.getName();
            oneLineIntroduce = circle.getOneLineIntroduce();
            if(LocalDateTime.now().isBefore(circle.getRecruitEndDate())&&LocalDateTime.now().isAfter(circle.getRecruitStartDate())){
                recruit = true;
            }
            else{
                recruit = false;
            }
            recruitEndDate = circle.getRecruitEndDate();
            userId = circle.getMember().getId();
            introduce = circle.getIntroduce();
            address = circle.getAddress();
            information = circle.getInformation();
            phone = circle.getPhoneNumber();
            nickname = circle.getMember().getNickName();
            circleCategory = circle.getCircleCategory();
            circleDivision = circle.getCircleDivision();
            Optional<Photo> photo1 = circle.getPhotos().stream()
                    .filter(photo -> photo.getPhotoType().equals(PhotoType.메인))
                    .findFirst();
            if (photo1.isEmpty()) {

            } else {
                photoId = photo1.get().getId();
            }

        }
    }

    @Data
    public static class CircleOneDTO {
        private Long id;
        private String name;
        private String nickname;
        private String oneLineIntroduce;
        private String introduce;
        private String information;
        private CircleCategory circleCategory;
        private CircleDivision circleDivision;
        private Boolean recruit;
        private LocalDateTime recruitStartDate;
        private LocalDateTime recruitEndDate;
        private String address;
        private String cafeLink;
        private String link;
        private String openKakaoLink;
        private String phoneNumber;
        private List<PhotoListDto> photos;

        public CircleOneDTO(Circle circle) {
            id = circle.getId();
            name = circle.getName();
            nickname = circle.getMember().getNickName();
            introduce = circle.getIntroduce();
            oneLineIntroduce = circle.getOneLineIntroduce();
            information = circle.getInformation();
            circleCategory = circle.getCircleCategory();
            circleDivision = circle.getCircleDivision();
            if(LocalDateTime.now().isBefore(circle.getRecruitEndDate())&&LocalDateTime.now().isAfter(circle.getRecruitStartDate())){
                recruit = true;
            }
            else{
                recruit = false;
            }
            recruitEndDate = circle.getRecruitEndDate();
            recruitStartDate = circle.getRecruitStartDate();
            link = circle.getLink();
            address = circle.getAddress();
            cafeLink = circle.getCafeLink();
            openKakaoLink = circle.getOpenKakaoLink();
            phoneNumber = circle.getPhoneNumber();
            photos = circle.getPhotos().stream()
                    .map(photo -> new PhotoListDto(photo)).collect(Collectors.toList());
        }
    }

    @Data
    static class PhotoListDto {
        private Long id;
        private PhotoType photoType;

        public PhotoListDto(Photo photo) {
            id = photo.getId();
            photoType = photo.getPhotoType();
        }
    }

    @Data
    public static class ReturnCircleIdResponse {
        private Long id;

        public ReturnCircleIdResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    public static class UpdateCircleRequest {

        private String name;
        private String oneLineIntroduce;//한줄소개*
        private String introduce;//소개*
        private String information;//지원정보
        private CircleDivision circleDivision;//중앙동아리,가동아리,소모임*
        private CircleCategory circleCategory;
        private Boolean recruit;//모집여부*
        private Optional<String> recruitStartDate;//시작기간
        private Optional<String> recruitEndDate;//마감기간
        private String link;//지원링크
        private String address;//동호수
        private String cafeLink;//동아리카페링크
        private String phoneNumber;//전화번호
        private String openKakaoLink;

    }

    @Data
    public static class CreateCircleRequest {
        private String name;//동아리이름
        private String oneLineIntroduce;//한줄소개
        private String introduce;//소개
        private CircleCategory circleCategory;//분류
        private CircleDivision circleDivision;//중앙동아리,가동아리,소모임
        private Boolean recruit;//모집여부*
        private String openKakao;
        private Optional<String> recruitStartDate;//시작기간
        private Optional<String> recruitEndDate;//마감기간
        private String link;//지원링크
        private String address;//동호수
        private String cafeLink;//동아리카페링크
        private String phoneNumber;//전화번호
        private String information;//지원정보
    }

    @Data
    @AllArgsConstructor
    public static class DeleteCircle {
        private Long id;
    }

}
