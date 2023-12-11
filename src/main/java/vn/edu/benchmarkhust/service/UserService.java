package vn.edu.benchmarkhust.service;

import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.model.entity.User;
import vn.edu.benchmarkhust.model.request.UserRequest;
import vn.edu.benchmarkhust.model.response.UserResponse;
import vn.edu.benchmarkhust.repository.UserRepository;
import vn.edu.benchmarkhust.transfromer.UserTransformer;

public class UserService extends BaseService<User, Long, UserRepository> {

    private final UserTransformer transformer;

    protected UserService(UserRepository repo, UserTransformer transformer) {
        super(repo);
        this.transformer = transformer;
    }

    @Override
    protected ErrorCode<?> errorCodeNotFoundEntity() {
        return BenchmarkErrorCode.NOT_FOUND_ENTITY;
    }

    public UserResponse getById(Long id) {
        return transformer.toResponse(getOrElseThrow(id));
    }

    public UserResponse create(UserRequest request) {
        var user = transformer.fromRequest(request);
        return transformer.toResponse(save(user));
    }

    public UserResponse update(Long id, UserRequest request) {
        var user = getOrElseThrow(id);
        transformer.setUser(user, request);
        return transformer.toResponse(save(user));
    }
}
