package pj.circles.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {//관리자

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    public Long id;

    public String nickName;
    public String passWord;
    public String email;

    @OneToOne
    @JoinColumn(name ="circle_id")
    public Circle circle;

    public Member(String nickName, String passWord, String email){
        this.nickName=nickName;
        this.passWord=passWord;
        this.email=email;
    }

}
