package vn.edu.benchmarkhust.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.benchmarkhust.facade.GroupFacade;
import vn.edu.benchmarkhust.model.request.GroupRequest;
import vn.edu.benchmarkhust.model.request.search.GroupSearchRequest;
import vn.edu.benchmarkhust.model.response.GroupResponse;

import javax.validation.Valid;
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

    @GetMapping()
    public List<GroupResponse> search(@ModelAttribute GroupSearchRequest searchRequest) {
        log.info("Search Group by request: {}", searchRequest);
        return facade.search(searchRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody GroupRequest request) {
        log.info("Create Group by request: {}", request);
        facade.create(request);
    }

    @PostMapping("save-all")
    public List<GroupResponse> saveAll(@Valid @RequestBody List<GroupRequest> requests) {
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
