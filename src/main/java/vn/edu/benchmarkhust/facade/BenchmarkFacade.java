package vn.edu.benchmarkhust.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.request.BenchmarkRequest;
import vn.edu.benchmarkhust.model.response.BenchmarkResponse;
import vn.edu.benchmarkhust.service.BenchmarkService;
import vn.edu.benchmarkhust.transfromer.BenchmarkTransformer;

@Slf4j
@Component
@RequiredArgsConstructor
public class BenchmarkFacade {

    private final BenchmarkService service;
    private final BenchmarkTransformer transformer;

    public BenchmarkResponse getById(Long id) {
        return transformer.toResponse(service.getOrElseThrow(id));
    }

    public BenchmarkResponse create(BenchmarkRequest request) {
        var benchmark = transformer.fromRequest(request);
        return transformer.toResponse(service.save(benchmark));
    }

    public BenchmarkResponse update(Long id, BenchmarkRequest request) {
        var benchmark = service.getOrElseThrow(id);
        transformer.setBenchmark(benchmark, request);
        return transformer.toResponse(service.save(benchmark));
    }

    public void deleteById(Long id) {
        var benchmark = service.getOrElseThrow(id);
        service.delete(benchmark);
    }
}
