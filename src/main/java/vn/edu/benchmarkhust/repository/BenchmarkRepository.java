package vn.edu.benchmarkhust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.benchmarkhust.model.entity.Benchmark;

public interface BenchmarkRepository extends JpaRepository<Benchmark, Long>, JpaSpecificationExecutor<Benchmark> {

    @Query(value = "select count(1) from benchmark where `year` = :year and faculty_id = :facultyId and group_type = :groupType",
            nativeQuery = true)
    Integer existedByYearAndFacultyIdAndGroupType(@Param("year") Integer year,
                                                  @Param("facultyId") Long facultyId,
                                                  @Param("groupType") String groupType);
}
