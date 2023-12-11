package vn.edu.benchmarkhust.transfromer;

import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.entity.School;
import vn.edu.benchmarkhust.model.request.SchoolRequest;
import vn.edu.benchmarkhust.model.response.SchoolResponse;
import vn.edu.benchmarkhust.utils.Utils;

@Component
public class SchoolTransformer {

    public SchoolResponse toResponse(School school) {
        var response = new SchoolResponse();
        Utils.copyPropertiesNotNull(school, response);
        return response;
    }

    public School fromRequest(SchoolRequest request) {
        var school = new School();
        Utils.copyPropertiesNotNull(request, school);
        return school;
    }

    public void setSchool(School school, SchoolRequest request) {
        Utils.copyPropertiesNotNull(request, school);
    }
}
