package vn.edu.benchmarkhust.service;

import org.springframework.stereotype.Service;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.model.entity.Faculty;
import vn.edu.benchmarkhust.repository.FacultyRepository;

@Service
public class FacultyService extends BaseService<Faculty, Long, FacultyRepository> {

    protected FacultyService(FacultyRepository repo) {
        super(repo);
    }

    @Override
    protected ErrorCode<?> errorCodeNotFoundEntity() {
        return BenchmarkErrorCode.NOT_FOUND_ENTITY;
    }

}
