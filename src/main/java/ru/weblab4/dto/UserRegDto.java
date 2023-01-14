package ru.weblab4.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserRegDto {
    @NonNull
    @Size(min = 5)
    private String login;

    @NonNull
    @Size(min = 1)
    private String firstName;

    @NonNull
    @Size(min = 1)
    private String lastName;

    @NonNull
    @Size(min = 5)
    private String password;
}
