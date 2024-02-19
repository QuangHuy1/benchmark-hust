package vn.edu.benchmarkhust.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import vn.edu.benchmarkhust.common.GroupType;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BenchmarkResponse {

    private Long id;
    private Integer year;
    private Float score;
    private GroupType groupType;
    private String faculty;
    private SchoolResponse school;
    private List<String> groups;
    private List<Long> groupIds;

}
