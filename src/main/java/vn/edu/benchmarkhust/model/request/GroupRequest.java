package vn.edu.benchmarkhust.model.request;

import lombok.Data;
import vn.edu.benchmarkhust.common.GroupType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GroupRequest {

    @NotBlank(message = "Required code")
    private String code;

    private String subject1;

    private String subject2;

    private String subject3;

    @NotNull(message = "Required groupType")
    private GroupType groupType;

}
