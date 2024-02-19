package vn.edu.benchmarkhust.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.benchmarkhust.facade.FacultyFacade;
import vn.edu.benchmarkhust.model.request.FacultyRequest;
import vn.edu.benchmarkhust.model.request.SuggestionRequest;
import vn.edu.benchmarkhust.model.request.search.FacultySearchRequest;
import vn.edu.benchmarkhust.model.response.FacultyResponse;
import vn.edu.benchmarkhust.model.response.SuggestionResponse;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyFacade facade;

    @GetMapping("{id}")
    public FacultyResponse getById(@PathVariable("id") Long id) {
        log.info("Get Faculty by Id: {}", id);
        return facade.getById(id);
    }

    @GetMapping()
    public Page<FacultyResponse> getAll(@ModelAttribute FacultySearchRequest searchRequest) {
        log.info("Search faculty by request {}", searchRequest);
        return facade.search(searchRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody FacultyRequest request) {
        log.info("Create Faculty by request: {}", request);
        facade.create(request);
    }

    @PutMapping("{id}")
    public FacultyResponse update(@PathVariable("id") Long id, @RequestBody FacultyRequest request) {
        log.info("Update Faculty by id - request: {} - {}", id, request);
        return facade.update(id, request);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        log.info("Delete Faculty by id: {}", id);
        facade.deleteById(id);
    }

    @PostMapping("suggest")
    public List<SuggestionResponse> getListSuggest(@RequestBody List<SuggestionRequest> requests) {
        log.info("Get list suggest by request: {}", requests);
        return facade.getListSuggest(requests);
    }

}
