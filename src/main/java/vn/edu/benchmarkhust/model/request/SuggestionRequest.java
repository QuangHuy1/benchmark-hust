package vn.edu.benchmarkhust.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuggestionRequest {
    private String fieldName;
    private Float avgBenchmark;
    private Set<String> groupCodes;
    private Set<Long> schoolIds;

    private int priorityPoint;
}
