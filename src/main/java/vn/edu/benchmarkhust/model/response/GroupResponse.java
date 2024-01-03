package vn.edu.benchmarkhust.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import vn.edu.benchmarkhust.common.GroupType;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupResponse {

    private Long id;
    private String code;
    private String subject1;
    private String subject2;
    private String subject3;
    private GroupType groupType;

}
