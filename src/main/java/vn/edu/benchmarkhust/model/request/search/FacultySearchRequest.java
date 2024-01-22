package vn.edu.benchmarkhust.model.request.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacultySearchRequest extends CommonSearchRequest {

    private String code;
    private String name;
    private Long schoolId;

}
