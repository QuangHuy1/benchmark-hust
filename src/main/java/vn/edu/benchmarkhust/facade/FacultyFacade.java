package vn.edu.benchmarkhust.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.entity.School;
import vn.edu.benchmarkhust.model.request.FacultyRequest;
import vn.edu.benchmarkhust.model.response.FacultyResponse;
import vn.edu.benchmarkhust.service.FacultyService;
import vn.edu.benchmarkhust.service.GroupService;
import vn.edu.benchmarkhust.service.SchoolService;
import vn.edu.benchmarkhust.transfromer.FacultyTransformer;

@Slf4j
@Component
@RequiredArgsConstructor
public class FacultyFacade {

    private final SchoolService schoolService;
    private final FacultyService facultyService;
    private final GroupService groupService;

    private final FacultyTransformer transformer;

    public FacultyResponse getById(Long id) {
        return transformer.toResponse(facultyService.getOrElseThrow(id));
    }

    public FacultyResponse create(FacultyRequest request) {
        var faculty = transformer.fromRequest(request);
        faculty.setSchool(schoolService.getOrElseThrow(request.getSchoolId()));
        faculty.setGroup(groupService.getAllByIds(request.getGroupIds()));

        return transformer.toResponse(facultyService.save(faculty));
    }

    public FacultyResponse update(Long id, FacultyRequest request) {
        var faculty = facultyService.getOrElseThrow(id);
        transformer.setFaculty(faculty, request);
        return transformer.toResponse(facultyService.save(faculty));
    }

    public void deleteById(Long id) {
        var faculty = facultyService.getOrElseThrow(id);
        facultyService.delete(faculty);
    }
}
