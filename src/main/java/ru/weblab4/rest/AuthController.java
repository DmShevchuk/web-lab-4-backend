package ru.weblab4.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.weblab4.dto.JwtResponse;
import ru.weblab4.dto.RefreshJwtRequest;
import ru.weblab4.dto.UserAuthDto;
import ru.weblab4.dto.UserRegDto;
import ru.weblab4.services.AuthService;
import ru.weblab4.services.UserService;

import javax.security.auth.message.AuthException;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserAuthDto authRequest) throws AuthException {
        JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/registration")
    public ResponseEntity<UUID> registration(@RequestBody UserRegDto userRegDto){
        return ResponseEntity.ok().body(userService.registration(userRegDto));
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

}