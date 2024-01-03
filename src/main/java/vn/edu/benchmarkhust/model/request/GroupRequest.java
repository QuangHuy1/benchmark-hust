package vn.edu.benchmarkhust.model.request;

import lombok.Data;
import vn.edu.benchmarkhust.common.GroupType;

@Data
public class GroupRequest {

    private String code;
    private String subject1;
    private String subject2;
    private String subject3;
    private GroupType groupType;

}
