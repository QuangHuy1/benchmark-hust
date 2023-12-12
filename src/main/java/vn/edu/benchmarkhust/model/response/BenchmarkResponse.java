package vn.edu.benchmarkhust.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BenchmarkResponse {

    private Long id;
    private String year;
    private Float mark;
    private FacultyResponse faculty;

}
