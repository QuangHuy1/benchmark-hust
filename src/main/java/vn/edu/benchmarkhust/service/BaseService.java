package vn.edu.benchmarkhust.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCodeException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public abstract class BaseService<E, ID, R extends JpaRepository<E, ID>> {
    protected final R repo;

    protected BaseService(R repo) {
        this.repo = repo;
    }

    public static Pageable getPageable(int pageIndex, int pageSize, String sortBy) {
        try {
            String direction = sortBy.split(":")[1];
            String sortProperty = sortBy.split(":")[0];
            return PageRequest.of(Math.max(pageIndex - 1, 0), pageSize, Sort.by(Sort.Direction.fromString(direction), sortProperty));
        } catch (Exception ex) {
            throw new ErrorCodeException(BenchmarkErrorCode.INVALID_SORT_PARAM);
        }
    }

    public static Pageable getPageable(int pageIndex, int pageSize) {
        return PageRequest.of(Math.max(pageIndex - 1, 0), pageSize);
    }

    public E save(E entity) {
        return this.repo.save(entity);
    }

    public List<E> saveAll(List<E> entities) {
        return this.repo.saveAll(entities);
    }

    public Optional<E> get(ID id) {
        return this.repo.findById(id);
    }

    public E getOrElseThrow(ID id) {
        return this.get(id).orElseThrow(() -> new ErrorCodeException(this.errorCodeNotFoundEntity()));
    }

    public E delete(E entity) {
        this.repo.delete(entity);
        return entity;
    }

    public List<E> getAll() {
        return this.repo.findAll();
    }

    public E updateOnField(ID id, Consumer<E> fieldConsumer) {
        E entity = this.getOrElseThrow(id);
        return this.update(entity, fieldConsumer);
    }

    public E update(E entity, Consumer<E> fieldConsumer) {
        fieldConsumer.accept(entity);
        this.save(entity);
        return entity;
    }

    public Page<E> query(int pageSize, int pageIndex) {
        int offset = (pageIndex - 1) * pageSize;
        PageRequest pageable = PageRequest.of(Math.max(offset, 0), pageSize);
        return this.repo.findAll(pageable);
    }

    public Page<E> query(Pageable pageable) {
        return this.repo.findAll(pageable);
    }

    public List<E> findAll(Pageable pageable) {
        List<E> results = new LinkedList<>();

        Pageable nextPageable;
        for (Page<E> page = this.query(pageable); page.hasContent(); page = this.query(nextPageable)) {
            results.addAll(page.getContent());
            nextPageable = page.getPageable().next();
        }

        return results;
    }

    public boolean existById(ID id) {
        return this.repo.existsById(id);
    }

    public void existByIdOrElseThrow(ID id) {
        boolean exist = this.repo.existsById(id);
        if (!exist) {
            throw new ErrorCodeException(this.errorCodeNotFoundEntity());
        }
    }

    protected abstract ErrorCode<?> errorCodeNotFoundEntity();

}
