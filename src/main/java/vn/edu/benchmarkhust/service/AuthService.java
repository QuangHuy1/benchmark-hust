package vn.edu.benchmarkhust.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.benchmarkhust.config.security.JwtToken;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCodeException;
import vn.edu.benchmarkhust.model.request.login.ChangePasswordRequest;
import vn.edu.benchmarkhust.model.request.login.LoginRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(ChangePasswordRequest request) {
        var user = userService.findByUsername(request.getUsername());
        authenticate(user.getUsername(), request.getOldPassword());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.save(user);
    }

    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return jwtToken.generateToken(((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername(), roles);
    }

    private Authentication authenticate(String username, String password) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new ErrorCodeException(BenchmarkErrorCode.ACCESS_DENIED);
        }
    }
}
