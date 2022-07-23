package pj.circles.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Circle {
    @Id
    @GeneratedValue
    @Column(name = "circle_id")
    private Long id;

    private String name;//동아리이름*
    private String oneLineIntroduce;//한줄소개*
    @Column(columnDefinition = "TEXT")
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

    private String address;//동호수
    private String cafeLink;//동아리카페링크
    private String openKakaoLink;//카카오 오픈쳇링크
    private String phoneNumber;//전화번호

    @OneToMany(mappedBy = "circle")
    private List<Photo> photos = new ArrayList<>();
    //동아리로고
    //동아리모집포스터

    //수정기능
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "circle")
    private List<MemberLikeCircle> memberLikeCircles = new ArrayList<>();

    public Circle(String name, String oneLineIntroduce, String introduce,
                  CircleCategory circleCategory, CircleDivision circleDivision, Boolean recruit, String openKakaoLink,
                  LocalDateTime recruitStartDate, LocalDateTime recruitEndDate, String link, String address, String cafeLink, String phoneNumber, String information, Member member) {
        this.name = name;
        this.oneLineIntroduce = oneLineIntroduce;
        this.introduce = introduce;
        this.circleCategory = circleCategory;
        this.circleDivision = circleDivision;
        this.recruit = recruit;
        this.openKakaoLink = openKakaoLink;
        this.recruitStartDate = recruitStartDate;
        this.recruitEndDate = recruitEndDate;
        this.link = link;
        this.address = address;
        this.cafeLink = cafeLink;
        this.phoneNumber = phoneNumber;
        this.information = information;
        this.member = member;
    }

    public void updateCircle(String name,String oneLineIntroduce, String introduce, String information, CircleDivision circleDivision
            , CircleCategory circleCategory, Boolean recruit, LocalDateTime recruitStartDate, LocalDateTime recruitEndDate,
                             String link, String address, String cafeLink, String phoneNumber, String openKakaoLink) {
        this.name=name;
        this.oneLineIntroduce = oneLineIntroduce;
        this.introduce = introduce;
        this.information = information;
        this.circleDivision = circleDivision;
        this.circleCategory = circleCategory;
        this.recruit = recruit;
        this.recruitStartDate = recruitStartDate;
        this.recruitEndDate = recruitEndDate;
        this.link = link;
        this.address = address;
        this.cafeLink = cafeLink;
        this.phoneNumber = phoneNumber;
        this.openKakaoLink = openKakaoLink;

    }
}
