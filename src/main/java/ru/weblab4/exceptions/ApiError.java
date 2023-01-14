package ru.weblab4.exceptions;

import lombok.Data;

@Data
public class ApiError {
    private final String title;
    private final String message;

    public ApiError(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
