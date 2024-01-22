package vn.edu.benchmarkhust.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import vn.edu.benchmarkhust.model.entity.Faculty;
import vn.edu.benchmarkhust.model.request.search.FacultySearchRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FacultySpecification {
    public static Specification<Faculty> with(FacultySearchRequest searchRequest) {
        return Specification.where(withCode(searchRequest.getCode()))
                .and(withName(searchRequest.getName()))
                .and(withSchool(searchRequest.getSchoolId()));
    }

    public static Specification<Faculty> withCode(String code) {
        if (StringUtils.isEmpty(code)) return null;

        return (root, query, cb) -> cb.equal(root.get("code"), code);
    }

    public static Specification<Faculty> withName(String name) {
        if (StringUtils.isEmpty(name)) return null;

        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Faculty> withSchool(Long schoolId) {
        if (schoolId == null) return null;

        return (root, query, cb) -> cb.equal(root.get("school").get("id"), schoolId);
    }
}
