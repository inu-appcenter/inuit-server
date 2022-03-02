package pj.circles.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pj.circles.domain.*;

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
      initService.dbInit();
      initService.dbInit2();
      initService.dbInit3();
      initService.dbInit4();
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
            Member member = new Member("닉1","비번1","이메일1");
            //member.setId(999L);
            em.persist(member);
            Circle circle = new Circle("모집중-문화-가동아리","첫줄소개",
                    "소개", CircleCategory.문화, CircleDivision.가동아리,true,"http~",LocalDateTime.now(),null,null,null,null,null,null,member);
            em.persist(circle);
            em.flush();
        }
        public void dbInit2() {
            Member member = new Member("닉2","비번2","이메일2");

            em.persist(member);
            Circle circle = new Circle("모집안함-학술-중앙동아리","첫줄소개2",
                    "소개", CircleCategory.학술, CircleDivision.중앙동아리,false,"http~",LocalDateTime.now(),null,null,null,null,null,null,member);
            em.persist(circle);
            em.flush();
        }
        public void dbInit3() {
            Member member = new Member("string",passwordEncoder.encode("string"),"string");
            Email email = new Email("string","1234");

            em.persist(member);
            em.persist(email);
            Circle circle = new Circle("모집중-체육-소모임","첫줄소개3",
                    "소개", CircleCategory.체육, CircleDivision.소모임,true,"x", LocalDateTime.now(),null,null,null,null,null,null,member);
            em.persist(circle);
            em.flush();
        }
        public void dbInit4() {
            Member member = new Member(id,passwordEncoder.encode(ps),"root");
            member.getRoles().add("ROLE_ADMIN");

            em.persist(member);

            em.flush();
        }
        //사람추가

        }

}
