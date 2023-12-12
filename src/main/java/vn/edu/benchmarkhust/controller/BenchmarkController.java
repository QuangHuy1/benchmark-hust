package vn.edu.benchmarkhust.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.edu.benchmarkhust.facade.BenchmarkFacade;
import vn.edu.benchmarkhust.model.request.BenchmarkRequest;
import vn.edu.benchmarkhust.model.response.BenchmarkResponse;

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

    @PostMapping
    public BenchmarkResponse create(@RequestBody BenchmarkRequest request) {
        log.info("Create Benchmark by request: {}", request);
        return facade.create(request);
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
}
