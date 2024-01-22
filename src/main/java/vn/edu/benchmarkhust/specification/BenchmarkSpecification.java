package vn.edu.benchmarkhust.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import vn.edu.benchmarkhust.common.GroupType;
import vn.edu.benchmarkhust.model.entity.Benchmark;
import vn.edu.benchmarkhust.model.entity.Group;
import vn.edu.benchmarkhust.model.request.search.BenchmarkSearchRequest;

import javax.persistence.criteria.Join;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BenchmarkSpecification {
    public static Specification<Benchmark> with(BenchmarkSearchRequest searchRequest) {
        return Specification.where(withFromScore(searchRequest.getFromScore()))
                .and(withToScore(searchRequest.getToScore()))
                .and(withYears(searchRequest.getYears()))
                .and(withGroupType(searchRequest.getGroupType()))
                .and(withFaculties(searchRequest.getFacultyIds()))
                .and(withGroups(searchRequest.getGroupCodes()));
    }

    public static Specification<Benchmark> withFromScore(Float fromScore) {
        if (fromScore == null) return null;

        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("score"), fromScore);
    }

    public static Specification<Benchmark> withToScore(Float toScore) {
        if (toScore == null) return null;

        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("score"), toScore);
    }

    public static Specification<Benchmark> withYears(List<Integer> years) {
        if (CollectionUtils.isEmpty(years)) return null;

        return (root, query, cb) -> root.get("year").in(years);
    }

    private static Specification<Benchmark> withGroupType(GroupType groupType) {
        if (groupType == null) return null;

        return (root, query, cb) -> cb.equal(root.get("groupType"), groupType);
    }

    public static Specification<Benchmark> withFaculties(Set<Long> facultyIds) {
        if (CollectionUtils.isEmpty(facultyIds)) return null;

        return (root, query, cb) -> root.get("faculty").get("id").in(facultyIds);
    }

    public static Specification<Benchmark> withGroups(Set<String> groupCodes) {
        if (CollectionUtils.isEmpty(groupCodes)) return null;

        return (root, query, cb) -> {
            Join<Benchmark, Group> groupsJoin = root.join("groups");
            return groupsJoin.get("code").in(groupCodes);
        };
    }
}
