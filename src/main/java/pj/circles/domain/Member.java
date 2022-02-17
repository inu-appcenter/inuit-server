package pj.circles.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {//관리자

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String nickName;
    private String password;
    private String email;
    //@ElementCollection(fetch = FetchType.EAGER)
    //private List<String> roles = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();
    @OneToOne(mappedBy = "member")
    private Circle circle;

    public Member(String nickName, String passWord, String email){
        this.nickName=nickName;
        this.password =passWord;
        this.email=email;
        this.roles.add("ROLE_USER");

    }

    public void updatePassWord(String passWord){
        this.password =passWord;
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
