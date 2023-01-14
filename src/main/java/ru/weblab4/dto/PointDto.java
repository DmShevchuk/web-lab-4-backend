package ru.weblab4.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PointDto {

    private UUID id;

    @NonNull
    @Min(value = -2)
    @Max(value = 2)
    private Float x;

    @NonNull
    @Min(value = -3)
    @Max(value = 3)
    private Float y;

    @NonNull
    @Min(value = 0)
    @Max(value = 2)
    private Float r;

    private Boolean isHit;
    private Long timePoint;
    private Long executionTime;
}
