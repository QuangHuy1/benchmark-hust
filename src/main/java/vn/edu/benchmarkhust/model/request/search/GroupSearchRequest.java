package vn.edu.benchmarkhust.model.request.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.edu.benchmarkhust.common.GroupType;

@Setter
@Getter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupSearchRequest extends CommonSearchRequest {

    private String code;
    private GroupType groupType;

}
