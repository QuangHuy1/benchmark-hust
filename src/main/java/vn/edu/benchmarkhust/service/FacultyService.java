package vn.edu.benchmarkhust.service;

import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.model.entity.Faculty;
import vn.edu.benchmarkhust.model.request.FacultyRequest;
import vn.edu.benchmarkhust.model.response.FacultyResponse;
import vn.edu.benchmarkhust.repository.FacultyRepository;
import vn.edu.benchmarkhust.transfromer.FacultyTransformer;

public class FacultyService extends BaseService<Faculty, Long, FacultyRepository> {

    private final FacultyTransformer transformer;

    protected FacultyService(FacultyRepository repo, FacultyTransformer transformer) {
        super(repo);
        this.transformer = transformer;
    }

    @Override
    protected ErrorCode<?> errorCodeNotFoundEntity() {
        return BenchmarkErrorCode.NOT_FOUND_ENTITY;
    }

    public FacultyResponse getById(Long id) {
        return transformer.toResponse(getOrElseThrow(id));
    }

    public FacultyResponse create(FacultyRequest request) {
        var benchmark = transformer.fromRequest(request);
        return transformer.toResponse(save(benchmark));
    }

    public FacultyResponse update(Long id, FacultyRequest request) {
        var faculty = getOrElseThrow(id);
        transformer.setFaculty(faculty, request);
        return transformer.toResponse(save(faculty));
    }
}
