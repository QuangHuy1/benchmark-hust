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

    @Query(value = "select id from faculty where school_id in :schoolIds", nativeQuery = true)
    List<Long> findAllFacultyIdBySchoolIdIn(@Param("schoolIds") Set<Long> schoolIds);

    @Modifying
    @Transactional
    @Query(value = "update Faculty f set f.avgBenchmark = :avgBenchmark where f.id = :id")
    Integer updateAvgBenchmarkById(@Param("id") Long id, @Param("avgBenchmark") float avgBenchmark);

}
