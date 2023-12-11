package vn.edu.benchmarkhust.model.response;

import lombok.Data;
import vn.edu.benchmarkhust.model.entity.School;

@Data
public class FacultyResponse {

    private Long id;
    private String code;
    private String name;
    private School school;
}
