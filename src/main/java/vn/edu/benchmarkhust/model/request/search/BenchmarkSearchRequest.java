package vn.edu.benchmarkhust.model.request.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.edu.benchmarkhust.common.GroupType;

import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BenchmarkSearchRequest extends CommonSearchRequest {

    private Float fromScore;
    private Float toScore;
    private Integer year;
    private GroupType groupType;
    private Set<Long> facultyIds = new HashSet<>();
    private Set<String> groupCodes = new HashSet<>();

}
