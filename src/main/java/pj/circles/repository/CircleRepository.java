package pj.circles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.circles.domain.Circle;
import pj.circles.domain.enumType.CircleCategory;
import pj.circles.domain.enumType.CircleDivision;

import java.util.List;

public interface CircleRepository extends JpaRepository<Circle,Long>, CircleRepositoryCustom {

    List<Circle> findByCircleCategory(CircleCategory circleCategory);
    List<Circle> findByCircleDivision(CircleDivision circleDivision);
    List<Circle> findByCircleCategoryAndCircleDivision(CircleCategory circleCategory,CircleDivision circleDivision);
    List<Circle> findByNameContainsOrIntroduceContainsOrOneLineIntroduceContains(String name, String introduce,String oneLineIntroduce);


}
