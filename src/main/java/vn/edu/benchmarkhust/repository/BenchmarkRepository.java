package vn.edu.benchmarkhust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.benchmarkhust.common.GroupType;
import vn.edu.benchmarkhust.model.entity.Benchmark;
import vn.edu.benchmarkhust.model.entity.Faculty;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BenchmarkRepository extends JpaRepository<Benchmark, Long>, JpaSpecificationExecutor<Benchmark> {

    @Query(value = "select count(1) from benchmark where `year` = :year and faculty_id = :facultyId and group_type = :groupType",
            nativeQuery = true)
    Integer existedByYearAndFacultyIdAndGroupType(@Param("year") Integer year,
                                                  @Param("facultyId") Long facultyId,
                                                  @Param("groupType") String groupType);

    @Modifying
    @Transactional
    @Query(value = "delete from benchmark_group where group_id in :groupIds and benchmark_id = :benchmarkId", nativeQuery = true)
    Integer removeGroupsFromBenchmark(@Param("groupIds") List<Long> groupIds, @Param("benchmarkId") Long benchmarkId);

    Optional<Benchmark> getByFacultyAndGroupTypeAndYear(Faculty faculty, GroupType groupType, Integer year);

    Integer countAllByYear(Integer year);

    @Query(value = "select distinct faculty_id from benchmark b join benchmark_group bg on b.id = bg.benchmark_id " +
            "where b.avg_benchmark between (:avgBenchmark - 1) and (:avgBenchmark + 1) and bg.group_id in :groupIds",
            nativeQuery = true)
    List<Long> findAllFacultyIdByAvgBenchmarkAndGroupIdsIn(@Param("avgBenchmark") Float avgBenchmark, @Param("groupIds") Set<Long> groupIds);
}
