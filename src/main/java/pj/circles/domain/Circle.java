package pj.circles.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Circle {
    @Id
    @GeneratedValue
    @Column(name = "circle_id")
    private Long id;

    private String name;//동아리이름*
    private String oneLineIntroduce;//한줄소개*
    private String introduce;//소개*
    private String information;//지원정보
    @Enumerated(EnumType.STRING)
    private CircleCategory circleCategory;//분류*
    @Enumerated(EnumType.STRING)
    private CircleDivision circleDivision;//중앙동아리,가동아리,소모임*
    private Boolean recruit;//모집여부*
    private LocalDateTime recruitStartDate;//시작기간
    private LocalDateTime recruitEndDate;//마감기간
    private String link;//지원링크

    //동호수
    //동아리카페링크
    //전화번호

    //동아리로고
    //동아리모집포스터

    //수정기능

    @OneToOne(mappedBy = "circle")
    private Member member;

    public Circle(String name, String oneLineIntroduce, String introduce,
                  CircleCategory circleCategory, CircleDivision circleDivision, Boolean recruit) {
        this.name = name;
        this.oneLineIntroduce=oneLineIntroduce;
        this.introduce = introduce;
        this.circleCategory = circleCategory;
        this.circleDivision = circleDivision;
        this.recruit=recruit;
    }
}
