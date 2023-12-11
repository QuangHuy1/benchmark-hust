package vn.edu.benchmarkhust.model.response;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String username;
    private String fullName;
    private String password;

}
