package pj.circles.dto;


import lombok.Data;
import pj.circles.domain.Circle;
import pj.circles.domain.Member;

public class MemberDTO {

    @Data
    public static class MemberOneDTO{
        private Long id;
        private String nickName;
        private String email;
        private Long circleId;
        private String circleName;
        public MemberOneDTO(Member member){
            id= member.getId();;
            nickName= member.getNickName();
            email= member.getEmail();;
            circleId=member.getCircle().getId();
            circleName=member.getCircle().getName();
        }
    }
}
