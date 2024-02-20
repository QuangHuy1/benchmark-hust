package vn.edu.benchmarkhust.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.benchmarkhust.facade.BenchmarkFacade;
import vn.edu.benchmarkhust.model.request.BenchmarkRequest;
import vn.edu.benchmarkhust.model.request.search.BenchmarkSearchRequest;
import vn.edu.benchmarkhust.model.response.BenchmarkResponse;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("benchmark")
public class BenchmarkController {

    private final BenchmarkFacade facade;

    @GetMapping("{id}")
    public BenchmarkResponse getById(@PathVariable("id") Long id) {
        log.info("Get Benchmark by Id: {}", id);
        return facade.getById(id);
    }

    @GetMapping()
    public Page<BenchmarkResponse> search(@ModelAttribute BenchmarkSearchRequest searchRequest) {
        log.info("Search Benchmark with request: {}", searchRequest);
        return facade.search(searchRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody BenchmarkRequest request) {
        log.info("Create Benchmark by request: {}", request);
        facade.create(request);
    }

    @PutMapping("{id}")
    public BenchmarkResponse update(@PathVariable("id") Long id, @RequestBody BenchmarkRequest request) {
        log.info("Update Benchmark by id - request: {} - {}", id, request);
        return facade.update(id, request);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        log.info("Delete Benchmark by id: {}", id);
        facade.deleteById(id);
    }

    @DeleteMapping("/remove-group")
    public void removeGroupsFromBenchmark(@RequestParam List<String> groupCodes, @RequestParam Long benchmarkId) {
        log.info("Remove groupCodes {} from benchmarkId {}", groupCodes, benchmarkId);
        facade.removeGroupFromBenchmark(groupCodes, benchmarkId);
    }
}
