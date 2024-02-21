package vn.edu.benchmarkhust.service;

import org.springframework.stereotype.Service;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.model.entity.School;
import vn.edu.benchmarkhust.repository.SchoolRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolService extends BaseService<School, Long, SchoolRepository> {

    protected SchoolService(SchoolRepository repo) {
        super(repo);
    }

    @Override
    protected ErrorCode<?> errorCodeNotFoundEntity() {
        return BenchmarkErrorCode.NOT_FOUND_ENTITY;
    }

    public List<School> findByVnNameOrEnNameOrAbbreviations(String vnName, String enName, String abbreviations) {
        return repo.findByVnNameOrEnNameOrAbbreviations(vnName, enName, abbreviations);
    }

    public Optional<School> findByAbbreviations(String abbreviations) {
        return repo.findByAbbreviations(abbreviations);
    }
}
