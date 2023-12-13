package vn.edu.benchmarkhust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.benchmarkhust.model.entity.Benchmark;

import java.util.Optional;

public interface BenchmarkRepository extends JpaRepository<Benchmark, Long>, JpaSpecificationExecutor<Benchmark> {

    @Query(value = "select * from benchmark where year_score = :yearScore and faculty_id = :facultyId", nativeQuery = true)
    Optional<Benchmark> findByYearAndFacultyId(@Param("yearScore") Integer yearScore, @Param("facultyId") Long facultyId);
}
