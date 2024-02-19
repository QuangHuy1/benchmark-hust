package vn.edu.benchmarkhust.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuggestionResponse {
    private Long facultyId;
    private String facultyName;
    private Float avgBenchmark;
    private String groupCode;
    private Long schoolId;
    private String schoolName;

    private float priorityPoint;
}
