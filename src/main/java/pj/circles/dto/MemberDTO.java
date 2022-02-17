package pj.circles.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import pj.circles.domain.Circle;
import pj.circles.domain.Member;

import javax.validation.constraints.NotEmpty;

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

    @Data
    public static class ReturnMemberIdResponse {
        private Long id;

        public ReturnMemberIdResponse(Long id) {
            this.id = id;
        }
    }
    @Data
    public static class UpdateMemberRequest {
        private String password;

        public UpdateMemberRequest(String password) {
            this.password = password;
        }
    }
    @Data
    public static class LoginMemberRequest {
        private String email;
        private String password;
    }
    @Data
    public static class CreateMemberRequest {
        @NotEmpty
        private String nickName;
        @NotEmpty
        private String password;
        @NotEmpty
        private String email;
    }
    @Data
    @AllArgsConstructor
    public static class DeleteMember{
        private Long id;

    }
}
