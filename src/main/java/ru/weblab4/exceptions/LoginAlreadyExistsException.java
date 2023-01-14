package ru.weblab4.exceptions;

public class LoginAlreadyExistsException extends RuntimeException {
    public LoginAlreadyExistsException(String login) {
        super("User with login '" + login + "' doesn't exist!");
    }
}
