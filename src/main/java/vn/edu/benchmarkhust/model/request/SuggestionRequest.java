package vn.edu.benchmarkhust.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuggestionRequest {
    private String fieldName;
    private Float avgBenchmark;
    private String groupCode;
    private Long schoolId;

    private int priorityPoint;
}
