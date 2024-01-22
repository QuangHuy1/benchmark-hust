package vn.edu.benchmarkhust.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.benchmarkhust.facade.UserFacade;
import vn.edu.benchmarkhust.model.request.UserRequest;
import vn.edu.benchmarkhust.model.response.UserResponse;

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

    @GetMapping("token")
    public UserResponse getByToken() {
        return facade.getByToken();
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

}
