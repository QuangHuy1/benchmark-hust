package vn.edu.benchmarkhust.model.request;

import lombok.Data;
import vn.edu.benchmarkhust.common.GroupType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@Data
public class BenchmarkRequest {

    @Positive(message = "Invalid year")
    private Integer year;

    @Positive(message = "Invalid score")
    private Float score;

    @NotNull(message = "Required groupType")
    private GroupType groupType;

    @Positive(message = "Invalid facultyId")
    private Long facultyId;

    @NotEmpty(message = "Required groupIds")
    private Set<Long> groupIds;

}
