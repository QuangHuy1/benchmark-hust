package vn.edu.benchmarkhust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.benchmarkhust.model.entity.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long>, JpaSpecificationExecutor<Faculty> {

    @Query(value = "delete from faculty_group where group_id = :groupId and faculty_id = :facultyId", nativeQuery = true)
    Integer removeGroup(@Param("groupId") Long groupId, @Param("facultyId") Long facultyId);
}
