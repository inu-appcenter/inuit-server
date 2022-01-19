package pj.circles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pj.circles.domain.Circle;
import pj.circles.domain.CircleCategory;
import pj.circles.domain.CircleDivision;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;


public class CircleDTO {

    @Data
    public static class CirclesDTO {
        private Long id;
        private String name;
        private String introduce;
        public Boolean recruit;
        public LocalDateTime recruitEndDate;

        public CirclesDTO(Circle circle) {
            id = circle.getId();
            name = circle.getName();
            introduce = circle.getIntroduce();
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
}
