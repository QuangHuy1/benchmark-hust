package vn.edu.benchmarkhust.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import vn.edu.benchmarkhust.common.GroupType;
import vn.edu.benchmarkhust.model.entity.Group;
import vn.edu.benchmarkhust.model.request.search.GroupSearchRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupSpecification {
    public static Specification<Group> with(GroupSearchRequest searchRequest) {
        return Specification.where(withCode(searchRequest.getCode()))
                .and(withGroupType(searchRequest.getGroupType()));
    }

    public static Specification<Group> withCode(String code) {
        if (StringUtils.isEmpty(code)) return null;

        return (root, query, cb) -> cb.equal(root.get("code"), code);
    }

    public static Specification<Group> withGroupType(GroupType groupType) {
        if (groupType == null) return null;

        return (root, query, cb) -> cb.equal(root.get("groupType"), groupType);
    }
}
