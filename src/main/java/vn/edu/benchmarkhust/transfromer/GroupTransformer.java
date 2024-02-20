package vn.edu.benchmarkhust.transfromer;

import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.entity.Group;
import vn.edu.benchmarkhust.model.request.GroupRequest;
import vn.edu.benchmarkhust.model.response.GroupResponse;

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
        if (request.getSubject1() != null) {
            group.setSubject1(request.getSubject1());
        }

        if (request.getSubject2() != null) {
            group.setSubject2(request.getSubject2());
        }

        if (request.getSubject3() != null) {
            group.setSubject3(request.getSubject3());
        }

        if (request.getGroupType() != null) {
            group.setGroupType(request.getGroupType());
        }
    }
}
