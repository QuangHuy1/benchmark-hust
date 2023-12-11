package vn.edu.benchmarkhust.transfromer;

import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.entity.User;
import vn.edu.benchmarkhust.model.request.UserRequest;
import vn.edu.benchmarkhust.model.response.UserResponse;
import vn.edu.benchmarkhust.utils.Utils;

@Component
public class UserTransformer {

    public UserResponse toResponse(User user) {
        var response = new UserResponse();
        Utils.copyPropertiesNotNull(user, response);
        return response;
    }

    public User fromRequest(UserRequest request) {
        var user = new User();
        Utils.copyPropertiesNotNull(request, user);
        return user;
    }

    public void setUser(User user, UserRequest request) {
        Utils.copyPropertiesNotNull(request, user);
    }
}
