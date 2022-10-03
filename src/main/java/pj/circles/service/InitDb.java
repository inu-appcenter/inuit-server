package pj.circles.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pj.circles.domain.*;
import pj.circles.domain.enumType.CircleCategory;
import pj.circles.domain.enumType.CircleDivision;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:login.properties")
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init(){
      //initService.dbInit();
      //initService.dbInit3();
      //initService.dbInit4();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Value("${id}")
        private String id;
        @Value("${ps}")
        private String ps;
        public void dbInit() {
            for (int num = 1; num < 31; num++){
                Member member = new Member("닉"+num, "비번"+num, "이메일"+num);
                //member.setId(999L);
                em.persist(member);
                Circle circle = new Circle("모집중-문화-가동아리"+num, "첫줄소개",
                    "소개", CircleCategory.문화, CircleDivision.가동아리, true, "http~", LocalDateTime.now(), null, null, null, null, null, null, member);
                em.persist(circle);
            }
        }

        public void dbInit3() {
            Member member = new Member("string",passwordEncoder.encode("string"),"string");
            Email email = new Email("string","1234");

            em.persist(member);
            em.persist(email);
            Circle circle = new Circle("모집중-체육-소모임","첫줄소개3",
                    "소개", CircleCategory.체육, CircleDivision.소모임,true,"x", LocalDateTime.now(),null,null,null,null,null,null,member);
            em.persist(circle);

        }
        public void dbInit4() {
            Member member = new Member(id,passwordEncoder.encode(ps),"root");
            member.getRoles().add("ROLE_ADMIN");

            em.persist(member);


        }
        //사람추가

        }

}
