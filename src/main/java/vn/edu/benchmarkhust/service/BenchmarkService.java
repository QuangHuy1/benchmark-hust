package vn.edu.benchmarkhust.service;

import org.springframework.stereotype.Service;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.model.entity.Benchmark;
import vn.edu.benchmarkhust.repository.BenchmarkRepository;

@Service
public class BenchmarkService extends BaseService<Benchmark, Long, BenchmarkRepository> {

    protected BenchmarkService(BenchmarkRepository repo) {
        super(repo);
    }

    @Override
    protected ErrorCode<?> errorCodeNotFoundEntity() {
        return BenchmarkErrorCode.NOT_FOUND_ENTITY;
    }
}
