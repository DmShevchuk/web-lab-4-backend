package ru.weblab4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequest {
    public String refreshToken;
}