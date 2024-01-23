package vn.edu.benchmarkhust.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCodeException;
import vn.edu.benchmarkhust.model.entity.Faculty;
import vn.edu.benchmarkhust.model.request.FacultyRequest;
import vn.edu.benchmarkhust.model.request.search.FacultySearchRequest;
import vn.edu.benchmarkhust.model.response.FacultyResponse;
import vn.edu.benchmarkhust.service.FacultyService;
import vn.edu.benchmarkhust.service.SchoolService;
import vn.edu.benchmarkhust.transfromer.FacultyTransformer;
import vn.edu.benchmarkhust.transfromer.SchoolTransformer;

import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FacultyFacade {

    private final SchoolService schoolService;
    private final FacultyService facultyService;

    private final FacultyTransformer facultyTransformer;
    private final SchoolTransformer schoolTransformer;

    public FacultyResponse getById(Long id) {
        return facultyTransformer.toResponse(facultyService.getOrElseThrow(id));
    }

    public Page<FacultyResponse> search(FacultySearchRequest searchRequest) {
        var facultyPage = facultyService.search(searchRequest);
        if (facultyPage == null || CollectionUtils.isEmpty(facultyPage.getContent())) return Page.empty();

        var facultyResponses = facultyPage.stream().map(fa -> {
            var response = facultyTransformer.toResponse(fa);
            response.setSchool(schoolTransformer.toResponse(fa.getSchool()));
            return response;
        }).collect(Collectors.toList());
        return new PageImpl<>(facultyResponses, facultyPage.getPageable(), facultyPage.getTotalElements());
    }

    @Transactional(rollbackFor = Throwable.class)
    public void create(FacultyRequest request) {
        var faculty = facultyTransformer.fromRequest(request);
        facultyService.getByCode(request.getCode()).orElseThrow(
                () -> new ErrorCodeException(BenchmarkErrorCode.EXISTED_VALUE, "Existed faculty code"));
        faculty.setSchool(schoolService.getOrElseThrow(request.getSchoolId()));
        facultyService.save(faculty);
    }

    @Transactional(rollbackFor = Throwable.class)
    public FacultyResponse update(Long id, FacultyRequest request) {
        var faculty = facultyService.getOrElseThrow(id);
        var existed = facultyService.getByCode(request.getCode()).orElse(null);
        if (existed != null && !Objects.equals(existed.getId(), id)) {
            throw new ErrorCodeException(BenchmarkErrorCode.EXISTED_VALUE, "Existed faculty code");
        }

        facultyTransformer.setFaculty(faculty, request);
        setFacultySchool(faculty, request);
        setGroupsSchool(faculty, request);

        var saved = facultyService.save(faculty);
        var facultyResponse = facultyTransformer.toResponse(saved);
        facultyResponse.setSchool(schoolTransformer.toResponse(saved.getSchool()));
        return facultyResponse;
    }

    private void setFacultySchool(Faculty faculty, FacultyRequest request) {
        if (request.getSchoolId() == null) return;
        if (faculty.getSchool() != null && Objects.equals(faculty.getSchool().getId(), request.getSchoolId())) return;

        log.info("Set faculty school by schoolId: {}", request.getSchoolId());
        faculty.setSchool(schoolService.getOrElseThrow(request.getSchoolId()));
    }

    private void setGroupsSchool(Faculty faculty, FacultyRequest request) {
        if (CollectionUtils.isEmpty(request.getGroupCodes())) return;

        log.info("Set faculty groups by groupCodes: {}", request.getGroupCodes());
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deleteById(Long id) {
        var faculty = facultyService.getOrElseThrow(id);
        facultyService.delete(faculty);
    }

}
