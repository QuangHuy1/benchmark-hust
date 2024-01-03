package vn.edu.benchmarkhust.model.request;

import lombok.Data;
import vn.edu.benchmarkhust.common.GroupType;

import java.util.Set;

@Data
public class BenchmarkRequest {

    private Integer year;
    private Float score;
    private GroupType groupType;
    private Long facultyId;
    private Set<Long> groupIds;

}
