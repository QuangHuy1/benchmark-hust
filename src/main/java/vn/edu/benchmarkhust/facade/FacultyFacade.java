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
import vn.edu.benchmarkhust.model.entity.Faculty;
import vn.edu.benchmarkhust.model.entity.Group;
import vn.edu.benchmarkhust.model.request.FacultyRequest;
import vn.edu.benchmarkhust.model.request.SuggestionRequest;
import vn.edu.benchmarkhust.model.request.search.FacultySearchRequest;
import vn.edu.benchmarkhust.model.response.FacultyResponse;
import vn.edu.benchmarkhust.model.response.SuggestionResponse;
import vn.edu.benchmarkhust.service.BenchmarkService;
import vn.edu.benchmarkhust.service.FacultyService;
import vn.edu.benchmarkhust.service.SchoolService;
import vn.edu.benchmarkhust.transfromer.FacultyTransformer;
import vn.edu.benchmarkhust.transfromer.SchoolTransformer;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FacultyFacade {

    private final SchoolService schoolService;
    private final FacultyService facultyService;
    private final BenchmarkService benchmarkService;

    private final FacultyTransformer facultyTransformer;
    private final SchoolTransformer schoolTransformer;

    public FacultyResponse getById(Long id) {
        return facultyTransformer.toResponse(facultyService.getOrElseThrow(id));
    }

    public Page<FacultyResponse> search(FacultySearchRequest searchRequest) {
        var facultyPage = facultyService.search(searchRequest);
        if (facultyPage == null || CollectionUtils.isEmpty(facultyPage.getContent())) return Page.empty();

        var facultyResponses = facultyPage.stream().map(fa -> {
            var response = facultyTransformer.toResponse(fa);
            response.setSchool(schoolTransformer.toResponse(fa.getSchool()));
            return response;
        }).collect(Collectors.toList());
        return new PageImpl<>(facultyResponses, facultyPage.getPageable(), facultyPage.getTotalElements());
    }

    @Transactional(rollbackFor = Throwable.class)
    public void create(FacultyRequest request) {
        var faculty = facultyTransformer.fromRequest(request);
        facultyService.getByCode(request.getCode()).ifPresent(a -> {
            throw new ErrorCodeException(BenchmarkErrorCode.EXISTED_VALUE, "Existed faculty code " + a.getCode());
        });
        faculty.setSchool(schoolService.getOrElseThrow(request.getSchoolId()));
        facultyService.save(faculty);
    }

    @Transactional(rollbackFor = Throwable.class)
    public FacultyResponse update(Long id, FacultyRequest request) {
        var faculty = facultyService.getOrElseThrow(id);
        var existed = facultyService.getByCode(request.getCode()).orElse(null);
        if (existed != null && !Objects.equals(existed.getId(), id)) {
            throw new ErrorCodeException(BenchmarkErrorCode.EXISTED_VALUE, "Existed faculty code");
        }

        facultyTransformer.setFaculty(faculty, request);
        setFacultySchool(faculty, request);

        var saved = facultyService.save(faculty);
        var facultyResponse = facultyTransformer.toResponse(saved);
        facultyResponse.setSchool(schoolTransformer.toResponse(saved.getSchool()));
        return facultyResponse;
    }

    private void setFacultySchool(Faculty faculty, FacultyRequest request) {
        if (request.getSchoolId() == null) return;
        if (faculty.getSchool() != null && Objects.equals(faculty.getSchool().getId(), request.getSchoolId())) return;

        log.info("Set faculty school by schoolId: {}", request.getSchoolId());
        faculty.setSchool(schoolService.getOrElseThrow(request.getSchoolId()));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deleteById(Long id) {
        var faculty = facultyService.getOrElseThrow(id);
        facultyService.delete(faculty);
    }

    public List<SuggestionResponse> getListSuggest(List<SuggestionRequest> suggestionRequests) {
        var sugRequest = summarizeRequest(suggestionRequests);
        var allFacultyIdByGroupIdsIn = benchmarkService.findAllFacultyIdByGroupIdsIn(sugRequest.getGroupIds());
        var allFacultyIdBySchoolIdIn = facultyService.findAllFacultyIdBySchoolIdIn(sugRequest.getSchoolIds());

        Set<Long> allFacultyIds = new HashSet<>();
        allFacultyIds.addAll(allFacultyIdByGroupIdsIn);
        allFacultyIds.addAll(allFacultyIdBySchoolIdIn);

        var suggestionResponses = allFacultyIds.stream().map(fId -> {
            var faculty = facultyService.getOrElseThrow(fId);

            SuggestionResponse sugResponse = new SuggestionResponse();
            sugResponse.setFacultyId(faculty.getId());
            sugResponse.setFacultyName(faculty.getName());
            sugResponse.setAvgBenchmark(faculty.getAvgBenchmark());
            sugResponse.setSchoolId(faculty.getSchool().getId());
            sugResponse.setSchoolName(faculty.getSchool().getVnName());

            var groups = faculty.getBenchmarks().stream().map(Benchmark::getGroups).flatMap(Set::stream).collect(Collectors.toSet());
            sugResponse.setGroupIds(groups.stream().map(Group::getId).collect(Collectors.toSet()));
            sugResponse.setGroupCodes(groups.stream().map(Group::getCode).collect(Collectors.toSet()));

            return sugResponse;
        }).collect(Collectors.toList());

        computePriority(suggestionRequests, suggestionResponses);
        return suggestionResponses.stream().limit(10).collect(Collectors.toList());
    }

    private SuggestionRequest summarizeRequest(List<SuggestionRequest> suggestionRequests) {
        SuggestionRequest sugRequest = new SuggestionRequest();
        for (var sug : suggestionRequests) {
            if ("avgBenchmark".equals(sug.getFieldName())) {
                sugRequest.setAvgBenchmark(sug.getAvgBenchmark());
                continue;
            }

            if ("groupIds".equals(sug.getFieldName())) {
                sugRequest.setGroupIds(sug.getGroupIds());
                continue;
            }

            if ("schoolIds".equals(sug.getFieldName())) {
                sugRequest.setSchoolIds(sug.getSchoolIds());
            }
        }
        return sugRequest;
    }

    private void computePriority(List<SuggestionRequest> suggestionRequests, List<SuggestionResponse> suggestionResponses) {
        for (var response : suggestionResponses) {
            float priorityPoint = 0;
            for (var request : suggestionRequests) {
                if (request.getAvgBenchmark() != null
                        && response.getAvgBenchmark() >= request.getAvgBenchmark() - 1
                        && response.getAvgBenchmark() <= request.getAvgBenchmark() + 1) {
                    priorityPoint += (request.getPriorityPoint() * 2 - Math.abs(response.getAvgBenchmark() - request.getAvgBenchmark()));
                    continue;
                }

                if (CollectionUtils.isNotEmpty(request.getGroupIds())
                        && request.getGroupIds().stream().anyMatch(gr -> response.getGroupIds().contains(gr))) {
                    priorityPoint += request.getPriorityPoint() * 2;
                    continue;
                }

                if (request.getSchoolIds() != null
                        && request.getSchoolIds().contains(response.getSchoolId())) {
                    priorityPoint += request.getPriorityPoint() * 2;
                }
            }
            response.setPriorityPoint(priorityPoint);
        }
        suggestionResponses.sort(Comparator.comparing(SuggestionResponse::getPriorityPoint).reversed());
    }
}