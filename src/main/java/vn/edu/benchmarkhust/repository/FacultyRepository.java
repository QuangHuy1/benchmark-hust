package vn.edu.benchmarkhust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.benchmarkhust.model.entity.Faculty;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface FacultyRepository extends JpaRepository<Faculty, Long>, JpaSpecificationExecutor<Faculty> {

    Optional<Faculty> findByCode(String code);

    @Query(value = "select f.id as facultyId, f.name as facultyName, f.avg_benchmark as avgBenchmark, g.code as groupCode, s.id as schoolId, s.vn_name as schoolName " +
            "from benchmark b " +
            "left join benchmark_group bg on bg.group_id = b.id " +
            "left join `group` g on g.id = bg.group_id " +
            "left join faculty f on f.id = b.faculty_id " +
            "left join school s on s.id = f.school_id " +
            "where f.avg_benchmark between (:avgBenchmark - 1) and (:avgBenchmark + 1) or g.code in :groupCodes or s.id in :schoolIds",
            nativeQuery = true)
    List<Map<String, Object>> getListSuggest(@Param("avgBenchmark") Float avgBenchmark,
                                             @Param("groupCodes") Set<String> groupCodes,
                                             @Param("schoolIds") Set<Long> schoolIds);

    @Modifying
    @Transactional
    @Query(value = "update Faculty f set f.avgBenchmark = :avgBenchmark where f.id = :id")
    Integer updateAvgBenchmarkById(@Param("id") Long id, @Param("avgBenchmark") float avgBenchmark);

}
