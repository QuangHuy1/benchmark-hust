package vn.edu.benchmarkhust.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCodeException;
import vn.edu.benchmarkhust.model.entity.Benchmark;
import vn.edu.benchmarkhust.model.entity.Group;
import vn.edu.benchmarkhust.model.request.BenchmarkRequest;
import vn.edu.benchmarkhust.model.request.search.BenchmarkSearchRequest;
import vn.edu.benchmarkhust.model.response.BenchmarkResponse;
import vn.edu.benchmarkhust.service.BenchmarkService;
import vn.edu.benchmarkhust.service.FacultyService;
import vn.edu.benchmarkhust.service.GroupService;
import vn.edu.benchmarkhust.transfromer.BenchmarkTransformer;
import vn.edu.benchmarkhust.transfromer.FacultyTransformer;
import vn.edu.benchmarkhust.transfromer.GroupTransformer;
import vn.edu.benchmarkhust.transfromer.SchoolTransformer;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class BenchmarkFacade {

    private final BenchmarkService benchmarkService;
    private final FacultyService facultyService;
    private final GroupService groupService;

    private final BenchmarkTransformer benchmarkTransformer;
    private final FacultyTransformer facultyTransformer;
    private final SchoolTransformer schoolTransformer;
    private final GroupTransformer groupTransformer;

    public BenchmarkResponse getById(Long id) {
        return toCompleteResponse(benchmarkService.getOrElseThrow(id));
    }

    public Page<BenchmarkResponse> search(BenchmarkSearchRequest searchRequest) {
        var benchmarkPage = benchmarkService.search(searchRequest);
        if (benchmarkPage == null || CollectionUtils.isEmpty(benchmarkPage.getContent())) return Page.empty();

        var benchmarkResponse = benchmarkPage.stream().map(this::toCompleteResponse).collect(Collectors.toList());
        return new PageImpl<>(benchmarkResponse, benchmarkPage.getPageable(), benchmarkPage.getTotalElements());
    }

    private BenchmarkResponse toCompleteResponse(Benchmark benchmark) {
        var response = benchmarkTransformer.toResponse(benchmark);

        if (benchmark.getGroups() != null) {
            response.setGroups(benchmark.getGroups().stream().map(Group::getCode).collect(Collectors.toList()));
        }

        if (benchmark.getFaculty() != null) {
            response.setFaculty(benchmark.getFaculty().getName());
            response.setSchool(benchmark.getFaculty().getSchool().getVnName());
        }
        return response;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void create(BenchmarkRequest request) {
        var existed = benchmarkService.existedByYearAndFacultyIdAndGroupType(request.getYear(), request.getFacultyId(), request.getGroupType());
        if (existed != 0) {
            throw new ErrorCodeException(BenchmarkErrorCode.EXISTED_VALUE, "Existed benchmark of year at groupType");
        }

        var groups = request.getGroupIds().stream().map(gr -> {
            var group = groupService.getOrElseThrow(gr);
            if (group.getGroupType() != request.getGroupType()) {
                throw new ErrorCodeException(BenchmarkErrorCode.INVALID_GROUP_TYPE, "GroupType not match  with group " + group.getCode());
            }
            return group;
        }).collect(Collectors.toSet());

        var benchmark = benchmarkTransformer.fromRequest(request);
        benchmark.setFaculty(facultyService.getOrElseThrow(request.getFacultyId()));
        benchmark.setGroups(groups);

        benchmarkService.save(benchmark);
    }

    @Transactional(rollbackFor = Throwable.class)
    public BenchmarkResponse update(Long id, BenchmarkRequest request) {
        var benchmark = benchmarkService.getOrElseThrow(id);
        benchmarkTransformer.setBenchmark(benchmark, request);
        return benchmarkTransformer.toResponse(benchmarkService.save(benchmark));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deleteById(Long id) {
        var benchmark = benchmarkService.getOrElseThrow(id);
        benchmarkService.delete(benchmark);
    }
}
