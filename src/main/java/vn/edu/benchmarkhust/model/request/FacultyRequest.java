package vn.edu.benchmarkhust.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Set;

@Data
public class FacultyRequest {

    @NotBlank(message = "Required code")
    private String code;

    @NotBlank(message = "Required name")
    private String name;

    @Positive(message = "Invalid schoolId")
    private Long schoolId;

    @NotEmpty(message = "Required groupCodes")
    private Set<String> groupCodes;

}
