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

    private String name;//동아리이름
    private String introduce;//소개
    private String information;//지원정보
    @Enumerated(EnumType.ORDINAL)
    private CircleCategory circleCategory;//분류
    @Enumerated(EnumType.ORDINAL)
    private CircleDivision circleDivision;//중앙동아리,가동아리,소모임
    private Boolean recruit;//모집여부
    private LocalDateTime recruitStartDate;//시작기간
    private LocalDateTime recruitEndDate;//마감기간

    @OneToOne(mappedBy = "circle")
    private Member member;

    public Circle(String name, String introduce, String information,
                  CircleCategory circleCategory, CircleDivision circleDivision) {
        this.name = name;
        this.introduce = introduce;
        this.information = information;
        this.circleCategory = circleCategory;
        this.circleDivision = circleDivision;
    }
}
