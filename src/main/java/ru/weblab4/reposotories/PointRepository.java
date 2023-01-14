package ru.weblab4.reposotories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.weblab4.domain.Point;

import java.util.UUID;

@Repository
public interface PointRepository extends JpaRepository<Point, UUID>, JpaSpecificationExecutor<Point> {
}
