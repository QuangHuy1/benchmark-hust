package vn.edu.benchmarkhust.transfromer;

import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.entity.Group;
import vn.edu.benchmarkhust.model.request.GroupRequest;
import vn.edu.benchmarkhust.model.response.GroupResponse;
import vn.edu.benchmarkhust.utils.Utils;

@Component
public class GroupTransformer {

    public GroupResponse toResponse(Group group) {
        var response = new GroupResponse();
        Utils.copyPropertiesNotNull(group, response);
        return response;
    }

    public Group fromRequest(GroupRequest request) {
        var group = new Group();
        Utils.copyPropertiesNotNull(request, group);
        return group;
    }

    public void setGroup(Group group, GroupRequest request) {
        Utils.copyPropertiesNotNull(request, group);
    }
}
