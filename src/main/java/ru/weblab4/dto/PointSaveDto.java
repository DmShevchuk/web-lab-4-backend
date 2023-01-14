package ru.weblab4.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PointSaveDto {
    private List<Float> x;
    private List<Float> y;
    private List<Float> r;
}
