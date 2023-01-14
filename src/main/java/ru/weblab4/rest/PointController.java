package ru.weblab4.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.weblab4.dto.PointDto;
import ru.weblab4.dto.PointSaveDto;
import ru.weblab4.exceptions.InvalidDataException;
import ru.weblab4.services.PointService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;


    @GetMapping
    public Page<PointDto> findAll(@PageableDefault Pageable pageable){
        return pointService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<List<UUID>> save(@RequestBody PointSaveDto pointSaveDto) throws InvalidDataException {
        return ResponseEntity.ok().body(pointService.save(pointSaveDto));
    }
}
