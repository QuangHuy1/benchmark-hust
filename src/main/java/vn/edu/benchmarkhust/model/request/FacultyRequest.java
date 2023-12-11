package vn.edu.benchmarkhust.model.request;

import lombok.Data;
import vn.edu.benchmarkhust.model.entity.School;

import javax.persistence.ManyToOne;

@Data
public class FacultyRequest {

    private String code;
    private String name;
    private School school;
}
