package vn.edu.benchmarkhust.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuggestionRequest {
    private String fieldName;
    private Float avgBenchmark;
    private Set<Long> groupIds;
    private Set<Long> schoolIds;

    private int priorityPoint;
}
