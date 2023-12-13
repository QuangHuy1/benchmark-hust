package vn.edu.benchmarkhust.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCodeException;
import vn.edu.benchmarkhust.model.entity.Faculty;
import vn.edu.benchmarkhust.model.request.FacultyRequest;
import vn.edu.benchmarkhust.model.response.FacultyResponse;
import vn.edu.benchmarkhust.service.FacultyService;
import vn.edu.benchmarkhust.service.GroupService;
import vn.edu.benchmarkhust.service.SchoolService;
import vn.edu.benchmarkhust.transfromer.FacultyTransformer;
import vn.edu.benchmarkhust.transfromer.GroupTransformer;
import vn.edu.benchmarkhust.transfromer.SchoolTransformer;

import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FacultyFacade {

    private final SchoolService schoolService;
    private final FacultyService facultyService;
    private final GroupService groupService;

    private final FacultyTransformer facultyTransformer;
    private final SchoolTransformer schoolTransformer;
    private final GroupTransformer groupTransformer;

    public FacultyResponse getById(Long id) {
        return facultyTransformer.toResponse(facultyService.getOrElseThrow(id));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void create(FacultyRequest request) {
        var faculty = facultyTransformer.fromRequest(request);
        faculty.setSchool(schoolService.getOrElseThrow(request.getSchoolId()));
        faculty.setGroups(groupService.getAllByCodes(request.getGroupCodes()));
        facultyService.save(faculty);
    }

    @Transactional(rollbackFor = Throwable.class)
    public FacultyResponse update(Long id, FacultyRequest request) {
        var faculty = facultyService.getOrElseThrow(id);
        facultyTransformer.setFaculty(faculty, request);
        setFacultySchool(faculty, request);
        setGroupsSchool(faculty, request);

        var saved = facultyService.save(faculty);
        var facultyResponse = facultyTransformer.toResponse(saved);
        facultyResponse.setSchool(schoolTransformer.toResponse(saved.getSchool()));
        facultyResponse.setGroups(saved.getGroups().stream().map(groupTransformer::toResponse).collect(Collectors.toList()));
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
        faculty.setGroups(groupService.getAllByCodes(request.getGroupCodes()));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deleteById(Long id) {
        var faculty = facultyService.getOrElseThrow(id);
        facultyService.delete(faculty);
    }

    public void removeGroup(Long groupId, Long facultyId) {
        var deleted = facultyService.removeGroup(groupId, facultyId);
        if (deleted == 0) {
            throw new ErrorCodeException(BenchmarkErrorCode.NOT_FOUND_ENTITY);
        }
    }
}
