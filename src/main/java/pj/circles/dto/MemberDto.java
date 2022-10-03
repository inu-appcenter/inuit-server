package pj.circles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pj.circles.domain.Circle;
import pj.circles.domain.Member;
import pj.circles.domain.MemberLikeCircle;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemberDto {

    @Data
    public static class MemberOneDto {
        private Long id;
        private String nickName;
        private String email;
        private Long circleId;
        private String circleName;
        private List<MemberLikeCirclesDto> memberLikeCircles;
        public MemberOneDto(Member member){
            id= member.getId();;
            nickName= member.getNickName();
            email= member.getEmail();;
            Optional<Circle> circle=Optional.ofNullable(member.getCircle());
            if (!circle.isEmpty()) {
                circleId = circle.get().getId();
                circleName = circle.get().getName();
            }
            memberLikeCircles= member.getMemberLikeCircles().stream()
                    .map(memberLikeCircle->new MemberLikeCirclesDto(memberLikeCircle)).collect(Collectors.toList());
        }
    }
    @Data
    public static class MemberLikeCirclesDto{
        Long id;
        Long circleId;
        String circleName;
        public MemberLikeCirclesDto(MemberLikeCircle memberLikeCircle) {
            Circle circle=memberLikeCircle.getCircle();
            this.id=memberLikeCircle.getId();
            this.circleId = circle.getId();
            this.circleName=circle.getName();
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
        private String nickName;


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
