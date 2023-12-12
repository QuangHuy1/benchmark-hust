package vn.edu.benchmarkhust.service;

import org.springframework.stereotype.Service;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCodeException;
import vn.edu.benchmarkhust.model.entity.Group;
import vn.edu.benchmarkhust.model.request.GroupRequest;
import vn.edu.benchmarkhust.repository.GroupRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService extends BaseService<Group, Long, GroupRepository> {

    protected GroupService(GroupRepository repo) {
        super(repo);
    }

    @Override
    protected ErrorCode<?> errorCodeNotFoundEntity() {
        return BenchmarkErrorCode.NOT_FOUND_ENTITY;
    }

    public Set<Group> getAllByIds(Set<Long> ids) {
        return repo.findAllByIdIn(ids);
    }

    public void validateDuplicateCode(String code) {
        if (repo.existsByCode(code)) {
            throw new ErrorCodeException(BenchmarkErrorCode.DUPLICATE_ENTITY_CODE, "Duplicate group code " + code);
        }
    }
}
