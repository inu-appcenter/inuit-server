package pj.circles.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Slf4j
public class Member extends BaseEntity implements UserDetails {

    @NotBlank
    private String nickName;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    @OneToOne(mappedBy = "member")
    private Circle circle;

    @OneToMany(mappedBy = "member")
    private List<MemberLikeCircle> memberLikeCircles = new ArrayList<>();

    public Member(String nickName, String passWord, String email){
        this.nickName=nickName;
        this.password =passWord;
        this.email=email;
        this.roles.add("ROLE_USER");

    }

    public void updatePassWord(String passWord){
        this.password =passWord;
    }
    public void updateNickName(String nickName){
        this.nickName =nickName;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
