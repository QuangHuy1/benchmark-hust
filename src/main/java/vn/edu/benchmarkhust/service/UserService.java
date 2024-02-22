package vn.edu.benchmarkhust.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCodeException;
import vn.edu.benchmarkhust.model.entity.User;
import vn.edu.benchmarkhust.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<User, Long, UserRepository> implements UserDetailsService {

    protected UserService(UserRepository repo) {
        super(repo);
    }

    @Override
    protected ErrorCode<?> errorCodeNotFoundEntity() {
        return BenchmarkErrorCode.NOT_FOUND_ENTITY;
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username).orElseThrow(() -> new ErrorCodeException(BenchmarkErrorCode.NOT_FOUND_ENTITY));
    }

    public Optional<User> findByUsernameOpt(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword() != null ? user.getPassword() : "",
                Collections.emptyList());
    }
}
