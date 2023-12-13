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
        response.setId(school.getId());
        response.setVnName(school.getVnName());
        response.setEnName(school.getEnName());
        response.setAbbreviations(school.getAbbreviations());

        return response;
    }

    public School fromRequest(SchoolRequest request) {
        var school = new School();
        school.setVnName(request.getVnName());
        school.setEnName(request.getEnName());
        school.setAbbreviations(request.getAbbreviations());

        return school;
    }

    public void setSchool(School school, SchoolRequest request) {
        Utils.copyPropertiesNotNull(request, school);
    }
}
