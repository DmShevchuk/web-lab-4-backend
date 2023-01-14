package ru.weblab4.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.weblab4.domain.Point;
import ru.weblab4.dto.PointDto;
import ru.weblab4.dto.PointSaveDto;
import ru.weblab4.exceptions.InvalidDataException;
import ru.weblab4.reposotories.PointRepository;
import ru.weblab4.utils.PointValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ru.weblab4.specification.PointSpecification.getPointSpecification;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final UserService userService;
    private final PointValidator pointValidator;
    private final ModelMapper modelMapper;

    public List<UUID> save(PointSaveDto pointDto) throws InvalidDataException {
        if(pointDto.getX().size() == 0 || pointDto.getY().size() == 0 || pointDto.getR().size() == 0){
            throw new InvalidDataException("All values should be specified!");
        }

        List<UUID> idsOfSavedPoints = new ArrayList<>();
        long startTime = System.nanoTime();

        Float x = pointDto.getX().get(0);
        Float y = pointDto.getY().get(0);

        for (Float r: pointDto.getR()){
            Point point = new Point();
            point.setX(x);
            point.setY(y);
            point.setR(r);
            point.setIsHit(pointValidator.checkIsHit(x, y, r));
            point.setExecutionTime(System.nanoTime() - startTime);
            point.setTimePoint(System.currentTimeMillis());
            point.setUser(userService.getCurrentUser());

            idsOfSavedPoints.add(pointRepository.save(point).getId());
        }

        return idsOfSavedPoints;
    }

    public Page<PointDto> findAll(Pageable pageable) {
        Page<Point> pointPage = pointRepository.findAll(getPointSpecification(userService.getCurrentUser()), pageable);
        return pointPage.map(point -> modelMapper.map(point, PointDto.class));
    }
}
