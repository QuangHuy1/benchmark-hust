package vn.edu.benchmarkhust.transfromer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.entity.User;
import vn.edu.benchmarkhust.model.request.UserRequest;
import vn.edu.benchmarkhust.model.response.UserResponse;
import vn.edu.benchmarkhust.utils.Utils;

@Component
@RequiredArgsConstructor
public class UserTransformer {
    private final PasswordEncoder passwordEncoder;

    public UserResponse toResponse(User user) {
        var response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFullName(user.getFullName());

        return response;
    }

    public User fromRequest(UserRequest request) {
        var user = new User();
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return user;
    }

    public void setUser(User user, UserRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Utils.copyPropertiesNotNull(request, user);
    }
}
