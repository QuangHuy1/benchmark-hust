package vn.edu.benchmarkhust.service;

import org.springframework.stereotype.Service;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCodeException;
import vn.edu.benchmarkhust.model.entity.Group;
import vn.edu.benchmarkhust.model.request.search.GroupSearchRequest;
import vn.edu.benchmarkhust.repository.GroupRepository;
import vn.edu.benchmarkhust.specification.GroupSpecification;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupService extends BaseService<Group, Long, GroupRepository> {

    protected GroupService(GroupRepository repo) {
        super(repo);
    }

    @Override
    protected ErrorCode<?> errorCodeNotFoundEntity() {
        return BenchmarkErrorCode.NOT_FOUND_ENTITY;
    }

    public Set<Group> getAllByCodes(Set<String> codes) {
        return repo.findAllByCodeIn(codes);
    }

    public Optional<Group> getByCode(String code) {
        return repo.getByCode(code);
    }

    public void validateDuplicateCode(String code) {
        if (repo.existsByCode(code)) {
            throw new ErrorCodeException(BenchmarkErrorCode.DUPLICATE_ENTITY_CODE, "Duplicate group code " + code);
        }
    }

    public List<Group> search(GroupSearchRequest searchRequest) {
        var spec = GroupSpecification.with(searchRequest);
        return repo.findAll(spec);
    }
}
