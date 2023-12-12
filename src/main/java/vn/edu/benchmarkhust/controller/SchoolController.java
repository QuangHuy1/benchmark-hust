package vn.edu.benchmarkhust.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.edu.benchmarkhust.facade.SchoolFacade;
import vn.edu.benchmarkhust.model.request.SchoolRequest;
import vn.edu.benchmarkhust.model.response.SchoolResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("school")
public class SchoolController {

    private final SchoolFacade facade;

    @GetMapping("{id}")
    public SchoolResponse getById(@PathVariable("id") Long id) {
        log.info("Get School by Id: {}", id);
        return facade.getById(id);
    }

    @PostMapping
    public SchoolResponse create(@RequestBody SchoolRequest request) {
        log.info("Create School by request: {}", request);
        return facade.create(request);
    }

    @PutMapping("{id}")
    public SchoolResponse update(@PathVariable("id") Long id, @RequestBody SchoolRequest request) {
        log.info("Update School by id - request: {} - {}", id, request);
        return facade.update(id, request);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        log.info("Delete School by id: {}", id);
        facade.deleteById(id);
    }
}
