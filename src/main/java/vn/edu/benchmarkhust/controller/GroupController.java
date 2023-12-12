package vn.edu.benchmarkhust.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.edu.benchmarkhust.facade.GroupFacade;
import vn.edu.benchmarkhust.model.request.GroupRequest;
import vn.edu.benchmarkhust.model.response.GroupResponse;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("group")
public class GroupController {

    private final GroupFacade facade;

    @GetMapping("{id}")
    public GroupResponse getById(@PathVariable("id") Long id) {
        log.info("Get Group by Id: {}", id);
        return facade.getById(id);
    }

    @PostMapping
    public GroupResponse create(@RequestBody GroupRequest request) {
        log.info("Create Group by request: {}", request);
        return facade.create(request);
    }

    @PostMapping("save-all")
    public List<GroupResponse> saveAll(@RequestBody List<GroupRequest> requests) {
        log.info("Save all Groups by requests: {}", requests);
        return facade.saveAll(requests);
    }

    @PutMapping("{id}")
    public GroupResponse update(@PathVariable("id") Long id, @RequestBody GroupRequest request) {
        log.info("Update Group by id - request: {} - {}", id, request);
        return facade.update(id, request);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        log.info("Delete Group by id: {}", id);
        facade.deleteById(id);
    }
}
