package vn.edu.benchmarkhust.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.benchmarkhust.model.request.login.ChangePasswordRequest;
import vn.edu.benchmarkhust.model.request.login.LoginRequest;
import vn.edu.benchmarkhust.service.AuthService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody @Valid LoginRequest request) {
        return Collections.singletonMap("token", authService.login(request));
    }

    @PostMapping("/change-pass")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        authService.changePassword(request);
    }
}
