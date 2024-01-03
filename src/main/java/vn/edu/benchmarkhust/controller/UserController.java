package vn.edu.benchmarkhust.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.benchmarkhust.facade.UserFacade;
import vn.edu.benchmarkhust.model.request.UserRequest;
import vn.edu.benchmarkhust.model.request.login.ChangePasswordRequest;
import vn.edu.benchmarkhust.model.request.login.LoginRequest;
import vn.edu.benchmarkhust.model.response.UserResponse;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserFacade facade;

    @GetMapping("{id}")
    public UserResponse getById(@PathVariable("id") Long id) {
        log.info("Get User by Id: {}", id);
        return facade.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserRequest request) {
        log.info("Create User by request: {}", request);
        facade.create(request);
    }

    @PutMapping("{id}")
    public UserResponse update(@PathVariable("id") Long id, @RequestBody UserRequest request) {
        log.info("Update User by id - request: {} - {}", id, request);
        return facade.update(id, request);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        log.info("Delete User by id: {}", id);
        facade.deleteById(id);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody @Valid LoginRequest request) {
        return Collections.singletonMap("token", facade.login(request));
    }

    @PostMapping("/change-pass")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        facade.changePassword(request);
    }
}
