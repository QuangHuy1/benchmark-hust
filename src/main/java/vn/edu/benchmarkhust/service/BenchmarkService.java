package vn.edu.benchmarkhust.service;

import org.springframework.stereotype.Service;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.model.entity.Benchmark;
import vn.edu.benchmarkhust.model.request.BenchmarkRequest;
import vn.edu.benchmarkhust.model.response.BenchmarkResponse;
import vn.edu.benchmarkhust.repository.BenchmarkRepository;
import vn.edu.benchmarkhust.transfromer.BenchmarkTransformer;

@Service
public class BenchmarkService extends BaseService<Benchmark, Long, BenchmarkRepository> {

    private final BenchmarkTransformer transformer;

    protected BenchmarkService(BenchmarkRepository repo, BenchmarkTransformer transformer) {
        super(repo);
        this.transformer = transformer;
    }

    @Override
    protected ErrorCode<?> errorCodeNotFoundEntity() {
        return BenchmarkErrorCode.NOT_FOUND_ENTITY;
    }

    public BenchmarkResponse getById(Long id) {
        return transformer.toResponse(getOrElseThrow(id));
    }

    public BenchmarkResponse create(BenchmarkRequest request) {
        var benchmark = transformer.fromRequest(request);
        return transformer.toResponse(save(benchmark));
    }

    public BenchmarkResponse update(Long id, BenchmarkRequest request) {
        var benchmark = getOrElseThrow(id);
        transformer.setBenchmark(benchmark, request);
        return transformer.toResponse(save(benchmark));
    }

}
