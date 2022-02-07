package pj.circles.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pj.circles.domain.Circle;
import pj.circles.domain.CircleCategory;
import pj.circles.domain.CircleDivision;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;
    @PostConstruct
    public void init(){
        initService.dbInit();
        initService.dbInit2();
        initService.dbInit3();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit() {
            Circle circle = new Circle("모집중-문화-가동아리","첫줄소개",
                    "소개", CircleCategory.문화, CircleDivision.가동아리,true);
            em.persist(circle);
            em.flush();
        }
        public void dbInit2() {
            Circle circle = new Circle("모집안함-학술-중앙동아리","첫줄소개2",
                    "소개", CircleCategory.학술, CircleDivision.중앙동아리,false);
            em.persist(circle);
            em.flush();
        }
        public void dbInit3() {
            Circle circle = new Circle("모집중-체육-소모임","첫줄소개3",
                    "소개", CircleCategory.체육, CircleDivision.소모임,true);
            em.persist(circle);
            em.flush();
        }
        //사람추가
        }
}
