package pj.circles.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pj.circles.domain.Circle;
import pj.circles.domain.CircleCategory;
import pj.circles.domain.CircleDivision;
import pj.circles.domain.Member;
import pj.circles.repository.CircleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CircleService {

    @Autowired
    CircleRepository circleRepository;

    @Transactional
    public Long join(String name, String oneLineIntroduce, String introduce,
                     CircleCategory circleCategory, CircleDivision circleDivision, Boolean recruit, String openKakao, Member member){

        Circle circle = new Circle(name,oneLineIntroduce,introduce,circleCategory,circleDivision,recruit,openKakao,member);
        circleRepository.save(circle);
        return circle.getId();
    }

    public Circle findById(Long circleId){
        return circleRepository.findById(circleId).get();
    }
    public List<Circle> findAll(){
        return circleRepository.findAll();
    }

    public List<Circle> findByCircleCategory(CircleCategory circleCategory){
        return circleRepository.findByCircleCategory(circleCategory);
    }
    public List<Circle> findByNameOrIntroduce(String name,String introduce,String oneLineIntroduce){
        return circleRepository.findByNameContainsOrIntroduceContainsOrOneLineIntroduceContains(name,introduce,oneLineIntroduce);
    }
    public List<Circle> findByCircleDivision(CircleDivision circleDivision){
        return circleRepository.findByCircleDivision(circleDivision);
    }
    public List<Circle> findByCircleCategoryAndCircleDivision(
            CircleCategory circleCategory,CircleDivision circleDivision){
        return circleRepository.findByCircleCategoryAndCircleDivision(circleCategory,circleDivision);
    }

    @Transactional
    public void deleteCircle(Long circleId){
        circleRepository.delete(findById(circleId));
    }
}
