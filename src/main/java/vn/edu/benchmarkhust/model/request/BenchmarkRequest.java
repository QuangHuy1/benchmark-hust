package vn.edu.benchmarkhust.model.request;

import lombok.Data;

import java.util.List;

@Data
public class BenchmarkRequest {

    private String year;
    private Float mark;
    private Long facultyIds;
    private List<Long> groupIds;

}
