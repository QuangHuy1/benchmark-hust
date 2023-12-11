package vn.edu.benchmarkhust.service;

import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.model.entity.Group;
import vn.edu.benchmarkhust.model.request.GroupRequest;
import vn.edu.benchmarkhust.model.response.GroupResponse;
import vn.edu.benchmarkhust.repository.GroupRepository;
import vn.edu.benchmarkhust.transfromer.GroupTransformer;

public class GroupService extends BaseService<Group, Long, GroupRepository> {

    private final GroupTransformer transformer;

    protected GroupService(GroupRepository repo, GroupTransformer transformer) {
        super(repo);
        this.transformer = transformer;
    }

    @Override
    protected ErrorCode<?> errorCodeNotFoundEntity() {
        return BenchmarkErrorCode.NOT_FOUND_ENTITY;
    }

    public GroupResponse getById(Long id) {
        return transformer.toResponse(getOrElseThrow(id));
    }

    public GroupResponse create(GroupRequest request) {
        var group = transformer.fromRequest(request);
        return transformer.toResponse(save(group));
    }

    public GroupResponse update(Long id, GroupRequest request) {
        var group = getOrElseThrow(id);
        transformer.setGroup(group, request);
        return transformer.toResponse(save(group));
    }
}
