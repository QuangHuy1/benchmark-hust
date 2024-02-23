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
import vn.edu.benchmarkhust.model.entity.Faculty;
import vn.edu.benchmarkhust.model.request.FacultyRequest;
import vn.edu.benchmarkhust.model.request.SuggestionRequest;
import vn.edu.benchmarkhust.model.request.search.FacultySearchRequest;
import vn.edu.benchmarkhust.model.response.FacultyResponse;
import vn.edu.benchmarkhust.model.response.SuggestionResponse;
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
        var listSuggestion = facultyService.getListSuggest(sugRequest);
        var groupByFacultyId = listSuggestion.stream().filter(f -> !Objects.isNull(f.get("facultyId")))
                .collect(Collectors.groupingBy(f -> Long.parseLong(f.get("facultyId").toString())));

        List<SuggestionResponse> suggestionResponses = new ArrayList<>();
        groupByFacultyId.forEach((k, v) -> {
            SuggestionResponse sug = new SuggestionResponse();
            sug.setFacultyId(k);
            Optional.ofNullable(v.get(0).get("facultyName")).ifPresent(opt -> sug.setFacultyName(opt.toString()));
            Optional.ofNullable(v.get(0).get("avgBenchmark")).ifPresent(opt -> sug.setAvgBenchmark(Float.parseFloat(opt.toString())));
            sug.setGroupCodes(v.stream().filter(gr -> gr.get("groupCode") != null).map(gr -> gr.get("groupCode").toString()).collect(Collectors.toSet()));
            Optional.ofNullable(v.get(0).get("schoolId")).ifPresent(opt -> sug.setSchoolId(Long.parseLong(opt.toString())));
            Optional.ofNullable(v.get(0).get("schoolName")).ifPresent(opt -> sug.setSchoolName(opt.toString()));
            suggestionResponses.add(sug);
        });
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

            if ("groupCodes".equals(sug.getFieldName())) {
                sugRequest.setGroupCodes(sug.getGroupCodes());
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
            float priorityPoint = response.getPriorityPoint();
            for (var request : suggestionRequests) {
                if (request.getAvgBenchmark() != null
                        && response.getAvgBenchmark() >= request.getAvgBenchmark() - 1
                        && response.getAvgBenchmark() <= request.getAvgBenchmark() + 1) {
                    priorityPoint += (request.getPriorityPoint() * 2 - Math.abs(response.getAvgBenchmark() - request.getAvgBenchmark()));
                    continue;
                }

                if (CollectionUtils.isNotEmpty(request.getGroupCodes())
                        && request.getGroupCodes().stream().anyMatch(gr -> response.getGroupCodes().contains(gr))) {
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