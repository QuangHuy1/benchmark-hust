package vn.edu.benchmarkhust.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.request.UserRequest;
import vn.edu.benchmarkhust.model.response.UserResponse;
import vn.edu.benchmarkhust.service.UserService;
import vn.edu.benchmarkhust.transfromer.UserTransformer;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService service;
    private final UserTransformer transformer;


    public UserResponse getById(Long id) {
        return transformer.toResponse(service.getOrElseThrow(id));
    }

    public void create(UserRequest request) {
        var user = transformer.fromRequest(request);
        service.save(user);
    }

    public UserResponse update(Long id, UserRequest request) {
        var user = service.getOrElseThrow(id);
        transformer.setUser(user, request);
        return transformer.toResponse(service.save(user));
    }

    public void deleteById(Long id) {
        var user = service.getOrElseThrow(id);
        service.delete(user);
    }
}
