package vn.edu.benchmarkhust.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.model.entity.Benchmark;
import vn.edu.benchmarkhust.model.request.search.BenchmarkSearchRequest;
import vn.edu.benchmarkhust.repository.BenchmarkRepository;
import vn.edu.benchmarkhust.specification.BenchmarkSpecification;

import java.util.Optional;

@Service
public class BenchmarkService extends BaseService<Benchmark, Long, BenchmarkRepository> {

    protected BenchmarkService(BenchmarkRepository repo) {
        super(repo);
    }

    @Override
    protected ErrorCode<?> errorCodeNotFoundEntity() {
        return BenchmarkErrorCode.NOT_FOUND_ENTITY;
    }

    public Optional<Benchmark> findByYearAndFacultyId(Integer year, Long facultyId) {
        return repo.findByYearAndFacultyId(year, facultyId);
    }

    public Page<Benchmark> search(BenchmarkSearchRequest searchRequest) {
        var spec = BenchmarkSpecification.with(searchRequest);
        return repo.findAll(spec, getPageable(searchRequest.getPageIndex(), searchRequest.getPageSize(), searchRequest.getSortBy()));
    }
}
