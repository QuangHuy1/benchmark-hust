package vn.edu.benchmarkhust.transfromer;

import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.entity.Faculty;
import vn.edu.benchmarkhust.model.request.FacultyRequest;
import vn.edu.benchmarkhust.model.response.FacultyResponse;
import vn.edu.benchmarkhust.utils.Utils;

@Component
public class FacultyTransformer {

    public FacultyResponse toResponse(Faculty faculty) {
        var response = new FacultyResponse();
        response.setId(faculty.getId());
        response.setCode(faculty.getCode());
        response.setName(faculty.getName());

        return response;
    }

    public Faculty fromRequest(FacultyRequest request) {
        var benchmark = new Faculty();
        benchmark.setCode(request.getCode());
        benchmark.setName(request.getName());

        return benchmark;
    }

    public void setFaculty(Faculty faculty, FacultyRequest request) {
        Utils.copyPropertiesNotNull(request, faculty);
    }
}
