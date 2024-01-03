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
        response.setId(group.getId());
        response.setCode(group.getCode());
        response.setSubject1(group.getSubject1());
        response.setSubject2(group.getSubject2());
        response.setSubject3(group.getSubject3());
        response.setGroupType(group.getGroupType());

        return response;
    }

    public Group fromRequest(GroupRequest request) {
        var group = new Group();
        group.setCode(request.getCode());
        group.setSubject1(request.getSubject1());
        group.setSubject2(request.getSubject2());
        group.setSubject3(request.getSubject3());
        group.setGroupType(request.getGroupType());

        return group;
    }

    public void setGroup(Group group, GroupRequest request) {
        Utils.copyPropertiesNotNull(request, group);
    }
}
