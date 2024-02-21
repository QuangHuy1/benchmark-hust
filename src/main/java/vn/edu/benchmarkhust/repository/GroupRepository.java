package vn.edu.benchmarkhust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.edu.benchmarkhust.model.entity.Group;

import java.util.Optional;
import java.util.Set;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    Set<Group> findAllByCodeIn(Set<String> codes);

    Optional<Group> getByCode(String code);

    boolean existsByCode(String code);
}
