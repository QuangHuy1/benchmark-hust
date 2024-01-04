package vn.edu.benchmarkhust.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.request.SchoolRequest;
import vn.edu.benchmarkhust.model.response.SchoolResponse;
import vn.edu.benchmarkhust.service.SchoolService;
import vn.edu.benchmarkhust.transfromer.FacultyTransformer;
import vn.edu.benchmarkhust.transfromer.SchoolTransformer;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchoolFacade {


    private final SchoolService schoolService;
    private final SchoolTransformer transformer;
    private final FacultyTransformer facultyTransformer;

    public SchoolResponse getById(Long id) {
        var school = schoolService.getOrElseThrow(id);
        var response = transformer.toResponse(school);
        response.setFaculties(school.getFaculties().stream().map(facultyTransformer::toResponse).collect(Collectors.toList()));
        return response;
    }

    public List<SchoolResponse> getAll() {
        return schoolService.getAll().stream().map(transformer::toResponse).collect(Collectors.toList());
    }

    public void create(SchoolRequest request) {
        var school = transformer.fromRequest(request);
        schoolService.save(school);
    }

    public SchoolResponse update(Long id, SchoolRequest request) {
        var school = schoolService.getOrElseThrow(id);
        transformer.setSchool(school, request);
        return transformer.toResponse(schoolService.save(school));
    }

    public void deleteById(Long id) {
        var school = schoolService.getOrElseThrow(id);
        schoolService.delete(school);
    }
}
