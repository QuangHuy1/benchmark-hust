package vn.edu.benchmarkhust.model.request;

import lombok.Data;

import java.util.Set;

@Data
public class FacultyRequest {

    private String code;
    private String name;
    private Long schoolId;
    private Set<String> groupCodes;

}
