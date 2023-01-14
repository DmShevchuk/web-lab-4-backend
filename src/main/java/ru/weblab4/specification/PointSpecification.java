package ru.weblab4.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.weblab4.domain.Point;
import ru.weblab4.domain.User;

import javax.persistence.criteria.*;

@Component
public class PointSpecification {
    public static Specification<Point> getPointSpecification(User user) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Point, User> owners = root.join("user");
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("timePoint")));
            return criteriaBuilder.equal(owners.get("id"), user.getId());
        };
    }
}
