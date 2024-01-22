package vn.edu.benchmarkhust.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.model.entity.Faculty;
import vn.edu.benchmarkhust.model.request.search.FacultySearchRequest;
import vn.edu.benchmarkhust.repository.FacultyRepository;
import vn.edu.benchmarkhust.specification.FacultySpecification;

@Service
public class FacultyService extends BaseService<Faculty, Long, FacultyRepository> {

    protected FacultyService(FacultyRepository repo) {
        super(repo);
    }

    @Override
    protected ErrorCode<?> errorCodeNotFoundEntity() {
        return BenchmarkErrorCode.NOT_FOUND_ENTITY;
    }

    public Page<Faculty> search(FacultySearchRequest searchRequest) {
        var spec = FacultySpecification.with(searchRequest);
        return repo.findAll(spec, getPageable(searchRequest.getPageIndex(), searchRequest.getPageSize(), searchRequest.getSortBy()));
    }
}
