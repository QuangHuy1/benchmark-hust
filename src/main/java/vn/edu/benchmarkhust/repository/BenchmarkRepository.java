package vn.edu.benchmarkhust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.benchmarkhust.model.entity.Benchmark;

import java.util.List;

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
}
