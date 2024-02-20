package vn.edu.benchmarkhust.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacultyRequest {

    @NotBlank(message = "Required code")
    private String code;

    @NotBlank(message = "Required name")
    private String name;

    @Positive(message = "Invalid schoolId")
    private Long schoolId;

}
