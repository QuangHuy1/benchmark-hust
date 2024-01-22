package vn.edu.benchmarkhust.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.request.UserRequest;
import vn.edu.benchmarkhust.model.response.UserResponse;
import vn.edu.benchmarkhust.service.UserService;
import vn.edu.benchmarkhust.transfromer.UserTransformer;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService service;
    private final UserTransformer transformer;

    public UserResponse getById(Long id) {
        return transformer.toResponse(service.getOrElseThrow(id));
    }

    public UserResponse getByToken() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var response = transformer.toResponse(service.findByUsername(user.getUsername()));
        response.setRoles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return response;
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
