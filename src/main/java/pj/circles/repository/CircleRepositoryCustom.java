package pj.circles.repository;

import pj.circles.domain.Circle;

import java.util.List;

public interface CircleRepositoryCustom {
    List<Circle> getList(int page);
}
