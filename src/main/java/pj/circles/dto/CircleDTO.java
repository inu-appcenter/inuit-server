package pj.circles.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import pj.circles.domain.Circle;
import pj.circles.domain.CircleCategory;
import pj.circles.domain.CircleDivision;
import java.time.LocalDateTime;


public class CircleDTO {

    @Data
    public static class CirclesDTO {
        private Long id;
        private String name;
        private String oneLineIntroduce;
        public Boolean recruit;
        public LocalDateTime recruitEndDate;

        public CirclesDTO(Circle circle) {
            id = circle.getId();
            name = circle.getName();
            oneLineIntroduce = circle.getOneLineIntroduce();
            recruit = circle.getRecruit();
            recruitEndDate = circle.getRecruitEndDate();
        }
    }

    @Data
    public static class CircleOneDTO {
        private Long id;
        private String name;
        private String introduce;
        private String information;
        private CircleCategory circleCategory;
        private CircleDivision circleDivision;
        private Boolean recruit;
        private LocalDateTime recruitStartDate;
        private LocalDateTime recruitEndDate;

        public CircleOneDTO(Circle circle) {
            id = circle.getId();
            name = circle.getName();
            introduce = circle.getIntroduce();
            information = circle.getInformation();
            circleCategory = circle.getCircleCategory();
            circleDivision = circle.getCircleDivision();
            recruit = circle.getRecruit();
            recruitEndDate = circle.getRecruitEndDate();
            recruitStartDate = circle.getRecruitStartDate();
        }
    }
    @Data
    public static class CreateCircleResponse{
        private Long id;
        public CreateCircleResponse(Long id) {
            this.id = id;
        }
    }
    @Data
    public static class CreateCircleRequest {
        private String name;//동아리이름
        private String oneLineIntroduce;//한줄소개
        private String introduce;//소개
        private CircleCategory circleCategory;//분류
        private CircleDivision circleDivision;//중앙동아리,가동아리,소모임
        private Boolean recruit;//모집여부*
    }
    @Data
    @AllArgsConstructor
    public static class DeleteCircle{
        private Long id;
    }

}
