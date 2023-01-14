package ru.weblab4.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PointValidator {
    public boolean checkIsHit(Float x, Float y, Float r){
        return (x >= 0 && x <= r/2 && y <= 0 && y >= -r)
                || (x <= 0 && y <= 0 && (x * x) + (y * y) <= (r/2) * (r/2))
                || (x <= 0 && y >= 0 && (-x + y) <= r);
    }
}
