package pj.circles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pj.circles.domain.Circle;
import pj.circles.domain.CircleCategory;
import pj.circles.domain.CircleDivision;

import java.util.List;

public interface CircleRepository extends JpaRepository<Circle,Long> {

    List<Circle> findByCircleCategory(CircleCategory circleCategory);
    List<Circle> findByCircleDivision(CircleDivision circleDivision);
    List<Circle> findByCircleCategoryAndCircleDivision(CircleCategory circleCategory,CircleDivision circleDivision);

}
