package vn.edu.benchmarkhust.model.request.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "username is required!")
    private String username;
    @NotBlank(message = "password is required!")
    private String password;
}
