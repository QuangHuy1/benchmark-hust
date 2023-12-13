package vn.edu.benchmarkhust.model.request.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;


@Setter
@Getter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BenchmarkSearchRequest extends CommonSearchRequest{

    private Float fromScore;
    private Float toScore;
    private Float year;
    private Set<Long> facultyIds;
    private Set<String> groupCodes;

}
