package vn.edu.benchmarkhust.service;

import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.model.entity.School;
import vn.edu.benchmarkhust.model.request.SchoolRequest;
import vn.edu.benchmarkhust.model.response.SchoolResponse;
import vn.edu.benchmarkhust.repository.SchoolRepository;
import vn.edu.benchmarkhust.transfromer.SchoolTransformer;

public class SchoolService extends BaseService<School, Long, SchoolRepository> {

    private final SchoolTransformer transformer;

    protected SchoolService(SchoolRepository repo, SchoolTransformer transformer) {
        super(repo);
        this.transformer = transformer;
    }

    @Override
    protected ErrorCode<?> errorCodeNotFoundEntity() {
        return BenchmarkErrorCode.NOT_FOUND_ENTITY;
    }

    public SchoolResponse getById(Long id) {
        return transformer.toResponse(getOrElseThrow(id));
    }

    public SchoolResponse create(SchoolRequest request) {
        var school = transformer.fromRequest(request);
        return transformer.toResponse(save(school));
    }

    public SchoolResponse update(Long id, SchoolRequest request) {
        var school = getOrElseThrow(id);
        transformer.setSchool(school, request);
        return transformer.toResponse(save(school));
    }
}
