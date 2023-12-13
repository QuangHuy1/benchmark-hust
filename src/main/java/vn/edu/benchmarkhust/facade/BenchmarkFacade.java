package vn.edu.benchmarkhust.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.exception.BenchmarkErrorCode;
import vn.edu.benchmarkhust.exception.ErrorCodeException;
import vn.edu.benchmarkhust.model.entity.Benchmark;
import vn.edu.benchmarkhust.model.entity.Faculty;
import vn.edu.benchmarkhust.model.entity.Group;
import vn.edu.benchmarkhust.model.request.BenchmarkRequest;
import vn.edu.benchmarkhust.model.request.search.BenchmarkSearchRequest;
import vn.edu.benchmarkhust.model.response.BenchmarkResponse;
import vn.edu.benchmarkhust.service.BenchmarkService;
import vn.edu.benchmarkhust.service.FacultyService;
import vn.edu.benchmarkhust.service.GroupService;
import vn.edu.benchmarkhust.transfromer.BenchmarkTransformer;
import vn.edu.benchmarkhust.transfromer.FacultyTransformer;
import vn.edu.benchmarkhust.transfromer.SchoolTransformer;

import java.util.Collection;
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

    public BenchmarkResponse getById(Long id) {
        return toCompleteResponse(benchmarkService.getOrElseThrow(id));
    }

    public Page<BenchmarkResponse> search(BenchmarkSearchRequest searchRequest) {
        buildSearchWithGroups(searchRequest);
        var benchmarkPage = benchmarkService.search(searchRequest);
        if (benchmarkPage == null || CollectionUtils.isEmpty(benchmarkPage.getContent())) return Page.empty();

        var benchmarkResponse = benchmarkPage.stream().map(this::toCompleteResponse).collect(Collectors.toList());
        return new PageImpl<>(benchmarkResponse, benchmarkPage.getPageable(), benchmarkPage.getTotalElements());
    }

    private BenchmarkResponse toCompleteResponse(Benchmark benchmark) {
        var response = benchmarkTransformer.toResponse(benchmark);
        if (benchmark.getFaculty() != null) {
            response.setFaculty(facultyTransformer.toResponse(benchmark.getFaculty()));
            response.setSchool(schoolTransformer.toResponse(benchmark.getFaculty().getSchool()));
        }
        return response;
    }

    public void buildSearchWithGroups(BenchmarkSearchRequest searchRequest) {
        if (CollectionUtils.isEmpty(searchRequest.getGroupCodes())) return;

        var groups = groupService.getAllByCodes(searchRequest.getGroupCodes());
        if (CollectionUtils.isEmpty(groups)) return;

        var groupIds = groups.stream().map(Group::getFaculties).flatMap(Collection::stream)
                .map(Faculty::getId).collect(Collectors.toSet());
        searchRequest.getFacultyIds().addAll(groupIds);
    }

    public void create(BenchmarkRequest request) {
        validateBenchmarkRequest(request);
        var benchmark = benchmarkTransformer.fromRequest(request);
        benchmark.setFaculty(facultyService.getOrElseThrow(request.getFacultyId()));
        benchmarkService.save(benchmark);
    }

    private void validateBenchmarkRequest(BenchmarkRequest request) {
        var benchmark = benchmarkService.findByYearAndFacultyId(request.getYearScore(), request.getFacultyId());
        if (benchmark.isPresent()) {
            throw new ErrorCodeException(BenchmarkErrorCode.EXISTED_VALUE, "Existed benchmark of year and faculty");
        }
    }

    public BenchmarkResponse update(Long id, BenchmarkRequest request) {
        var benchmark = benchmarkService.getOrElseThrow(id);
        benchmarkTransformer.setBenchmark(benchmark, request);
        return benchmarkTransformer.toResponse(benchmarkService.save(benchmark));
    }

    public void deleteById(Long id) {
        var benchmark = benchmarkService.getOrElseThrow(id);
        benchmarkService.delete(benchmark);
    }
}
