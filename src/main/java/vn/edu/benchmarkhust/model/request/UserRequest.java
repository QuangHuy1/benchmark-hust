package vn.edu.benchmarkhust.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserRequest {

    @Pattern(regexp = "^[a-z0-9]([_.][a-z0-9]){6,30}$", message = "Invalid username")
    private String username;

    @NotBlank(message = "Required fullName")
    private String fullName;

    @Pattern(regexp = "[A-Za-z0-9!@#$%&'*/=?^`{|}~]{6,30}$", message = "Invalid password")
    private String password;

}
