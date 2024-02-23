package vn.edu.benchmarkhust.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.model.entity.Faculty;
import vn.edu.benchmarkhust.model.request.SuggestionRequest;
import vn.edu.benchmarkhust.model.request.search.FacultySearchRequest;
import vn.edu.benchmarkhust.repository.FacultyRepository;
import vn.edu.benchmarkhust.specification.FacultySpecification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Optional<Faculty> getByCode(String code) {
        return repo.findByCode(code);
    }

    public List<Map<String, Object>> getListSuggest(SuggestionRequest sugRequest) {
        return repo.getListSuggest(sugRequest.getAvgBenchmark(), sugRequest.getGroupCodes(), sugRequest.getSchoolIds());
    }

    public Integer updateAvgBenchmarkById(Long id, float avgBenchmark) {
        return repo.updateAvgBenchmarkById(id, avgBenchmark);
    }
}
