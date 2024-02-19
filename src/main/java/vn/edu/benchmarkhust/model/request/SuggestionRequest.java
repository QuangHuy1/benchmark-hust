package vn.edu.benchmarkhust.model.request;

import lombok.Data;

@Data
public class SuggestionRequest {
    private String fieldName;
    private Float avgBenchmark;
    private String groupCode;
    private Long schoolId;

    private int priorityPoint;
}
