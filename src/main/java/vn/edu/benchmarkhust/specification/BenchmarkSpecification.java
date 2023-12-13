package vn.edu.benchmarkhust.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import vn.edu.benchmarkhust.model.entity.Benchmark;
import vn.edu.benchmarkhust.model.request.search.BenchmarkSearchRequest;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BenchmarkSpecification {
    public static Specification<Benchmark> with(BenchmarkSearchRequest searchRequest) {
        return Specification.where(withFromScore(searchRequest.getFromScore()));
    }

    public static Specification<Benchmark> withFromScore(Float fromScore) {
        if (fromScore == null) return null;

        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("pointScore"), fromScore);
    }

    public static Specification<Benchmark> withToScore(Float toScore) {
        if (toScore == null) return null;

        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("pointScore"), toScore);
    }

    public static Specification<Benchmark> withYear(Integer year) {
        if (year == null) return null;

        return (root, query, cb) -> cb.equal(root.get("yearScore"), year);
    }

    public static Specification<Benchmark> withFaculties(Set<Long> facultyIds) {
        if (CollectionUtils.isEmpty(facultyIds)) return null;

        return (root, query, cb) -> root.get("faculty").get("id").in(facultyIds);
    }
}
