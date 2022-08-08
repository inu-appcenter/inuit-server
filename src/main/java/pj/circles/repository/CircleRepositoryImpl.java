package pj.circles.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import pj.circles.domain.Circle;
import pj.circles.domain.QCircle;

import java.util.List;

@RequiredArgsConstructor
public class CircleRepositoryImpl implements CircleRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Circle> getList(int page) {
        return jpaQueryFactory.selectFrom(QCircle.circle)
                .limit(5)
                .offset((long) page*5)
                .fetch();
    }
}
