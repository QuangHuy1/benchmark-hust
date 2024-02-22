package vn.edu.benchmarkhust.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import vn.edu.benchmarkhust.common.GroupType;
import vn.edu.benchmarkhust.facade.BenchmarkFacade;
import vn.edu.benchmarkhust.facade.FacultyFacade;
import vn.edu.benchmarkhust.facade.GroupFacade;
import vn.edu.benchmarkhust.facade.SchoolFacade;
import vn.edu.benchmarkhust.model.entity.Benchmark;
import vn.edu.benchmarkhust.model.entity.Faculty;
import vn.edu.benchmarkhust.model.entity.Group;
import vn.edu.benchmarkhust.model.entity.School;
import vn.edu.benchmarkhust.model.request.BenchmarkRequest;
import vn.edu.benchmarkhust.model.request.FacultyRequest;
import vn.edu.benchmarkhust.model.request.GroupRequest;
import vn.edu.benchmarkhust.model.request.SchoolRequest;
import vn.edu.benchmarkhust.model.response.TsHustResponse;
import vn.edu.benchmarkhust.service.connector.TsHustClient;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrawlService {
    private final ObjectMapper mapper;
    private final SchoolService schoolService;
    private final SchoolFacade schoolFacade;
    private final FacultyService facultyService;
    private final FacultyFacade facultyFacade;
    private final GroupService groupService;
    private final GroupFacade groupFacade;
    private final BenchmarkService benchmarkService;
    private final BenchmarkFacade benchmarkFacade;
    private List<FacultyRequest> facultyRequests;
    private final TsHustClient tsHustClient;

    public CrawlService(ObjectMapper mapper,
                        SchoolService schoolService,
                        SchoolFacade schoolFacade,
                        FacultyService facultyService,
                        FacultyFacade facultyFacade,
                        GroupService groupService,
                        GroupFacade groupFacade,
                        BenchmarkService benchmarkService,
                        BenchmarkFacade benchmarkFacade,
                        TsHustClient tsHustClient) {
        this.mapper = mapper;
        this.schoolService = schoolService;
        this.schoolFacade = schoolFacade;
        this.facultyService = facultyService;
        this.facultyFacade = facultyFacade;
        this.groupService = groupService;
        this.groupFacade = groupFacade;
        this.benchmarkService = benchmarkService;
        this.benchmarkFacade = benchmarkFacade;
        this.tsHustClient = tsHustClient;
        configSchool();
        configGroup();
        configFaculty();
        configBenchmark();
        new Thread(this::configBenchmark).start();
    }

    public void configSchool() {
        File file = new File("./config/school.json");
        List<SchoolRequest> schoolRequests;
        if (!file.exists() || file.length() < 10) {
            log.info("Deeplink school path " + file.getAbsolutePath() + " invalid");
            return;
        }
        try {
            log.info("Load school application configuration");
            String json = Files.readString(file.toPath());
            TypeReference<List<SchoolRequest>> typeRef = new TypeReference<>() {
            };
            schoolRequests = mapper.readValue(json, typeRef);
            log.info("Found school number: {}", schoolRequests.size());
            schoolRequests.forEach(request -> {
                try {
                    Optional<School> school = schoolService.findByAbbreviations(request.getAbbreviations());
                    if (school.isEmpty()) {
                        schoolFacade.create(request);
                    } else {
                        schoolFacade.update(school.get().getId(), request);
                    }
                } catch (Exception e) {
                    log.error("configSchool in repo: {}", e.getMessage(), e);
                }
            });
        } catch (Exception e) {
            log.error("configSchool: {}", e.getMessage(), e);
        }
    }

    public void configGroup() {
        File file = new File("./config/group.json");
        List<GroupRequest> groupRequests;
        if (!file.exists() || file.length() < 10) {
            log.info("Deeplink group path " + file.getAbsolutePath() + " invalid");
            return;
        }
        try {
            log.info("Load group application configuration");
            String json = Files.readString(file.toPath());
            TypeReference<List<GroupRequest>> typeRef = new TypeReference<>() {
            };
            groupRequests = mapper.readValue(json, typeRef);
            log.info("Found group number: {}", groupRequests.size());
            groupRequests.forEach(request -> {
                try {
                    Optional<Group> group = groupService.getByCode(request.getCode());
                    if (group.isEmpty()) {
                        groupFacade.create(request);
                    } else {
                        groupFacade.update(group.get().getId(), request);
                    }
                } catch (Exception e) {
                    log.error("configGroup in repo: {}", e.getMessage(), e);
                }
            });
        } catch (Exception e) {
            log.error("configGroup: {}", e.getMessage(), e);
        }
    }

    public void configFaculty() {
        File file = new File("./config/faculty.json");
        if (!file.exists() || file.length() < 10) {
            log.info("Deeplink faculty path " + file.getAbsolutePath() + " invalid");
            return;
        }
        try {
            log.info("Load faculty application configuration");
            String json = Files.readString(file.toPath());
            TypeReference<List<FacultyRequest>> typeRef = new TypeReference<>() {
            };
            facultyRequests = mapper.readValue(json, typeRef);
            log.info("Found faculty number: {}", facultyRequests.size());
            facultyRequests.forEach(request -> {
                try {
                    Optional<Faculty> faculty = facultyService.getByCode(request.getCode());
                    if (faculty.isEmpty()) {
                        Optional<School> school = schoolService.findByAbbreviations(request.getAbbreviations());
                        if (school.isPresent()) {
                            request.setSchoolId(school.get().getId());
                            facultyFacade.create(request);
                        }
                    } else {
                        facultyFacade.update(faculty.get().getId(), request);
                    }
                } catch (Exception e) {
                    log.error("configFaculty in repo: {}", e.getMessage(), e);
                }
            });
        } catch (Exception e) {
            log.error("configFaculty: {}", e.getMessage(), e);
        }
    }

    public void configBenchmark() {
        if (benchmarkService.countAllByYear(2024) > 0) {
            return;
        }

        if (facultyRequests == null || CollectionUtils.isEmpty(facultyRequests)) {
            return;
        }

        long startTime = System.currentTimeMillis();
        log.info("Start migrate data benchmark: {}", startTime);
        List<String> years = List.of("2020", "2021", "2022", "2023", "2024", "2025");
        for (FacultyRequest facultyRequest : facultyRequests) {
            facultyService.getByCode(facultyRequest.getCode())
                    .ifPresent(faculty -> years
                            .forEach(year -> tsHustClient.getTsHustResponse(facultyRequest.getName(), year).ifPresent(ts -> {
                executeBenchmark(facultyRequest, faculty, year, ts);
            })));
        }
        log.info("Done migrate data benchmark in: {}", System.currentTimeMillis() - startTime);
    }

    private void executeBenchmark(FacultyRequest facultyRequest, Faculty faculty, String year, TsHustResponse ts) {
        try {
            if (!CollectionUtils.isEmpty(facultyRequest.getTsa()) && StringUtils.isNotEmpty(ts.getData().getPointDgtd())) {
                BenchmarkRequest benchmarkRequest = new BenchmarkRequest();
                benchmarkRequest.setGroupIds(groupService.getAllByCodes(facultyRequest.getTsa())
                        .stream()
                        .map(Group::getId)
                        .collect(Collectors.toSet()));
                benchmarkRequest.setYear(Integer.valueOf(year));
                benchmarkRequest.setFacultyId(faculty.getId());
                benchmarkRequest.setGroupType(GroupType.TSA);
                benchmarkRequest.setScore(Float.valueOf(ts.getData().getPointDgtd()));
                Optional<Benchmark> benchmark = benchmarkService.findOne(faculty, GroupType.TSA, Integer.valueOf(year));
                if (benchmark.isEmpty()) {
                    benchmarkFacade.create(benchmarkRequest);
                    log.info("Migrate data create benchmark TSA - year: {} - data: {}", year, benchmarkRequest);
                } else {
                    benchmarkFacade.update(benchmark.get().getId(), benchmarkRequest);
                    log.info("Migrate data update benchmark TSA id: {} - year: {} - data: {}", benchmark.get().getId(), year, benchmarkRequest);
                }
            }

            if (!CollectionUtils.isEmpty(facultyRequest.getBasic()) && StringUtils.isNotEmpty(ts.getData().getPointTn())) {
                BenchmarkRequest benchmarkRequest = new BenchmarkRequest();
                benchmarkRequest.setGroupIds(groupService.getAllByCodes(facultyRequest.getBasic())
                        .stream()
                        .map(Group::getId)
                        .collect(Collectors.toSet()));
                benchmarkRequest.setYear(Integer.valueOf(year));
                benchmarkRequest.setFacultyId(faculty.getId());
                benchmarkRequest.setGroupType(GroupType.BASIC);
                benchmarkRequest.setScore(Float.valueOf(ts.getData().getPointTn()));
                Optional<Benchmark> benchmark = benchmarkService.findOne(faculty, GroupType.BASIC, Integer.valueOf(year));
                if (benchmark.isEmpty()) {
                    benchmarkFacade.create(benchmarkRequest);
                    log.info("Migrate data create benchmark BASIC - year: {} - data: {}", year, benchmarkRequest);
                } else {
                    benchmarkFacade.update(benchmark.get().getId(), benchmarkRequest);
                    log.info("Migrate data update benchmark BASIC id: {} - year: {} - data: {}", benchmark.get().getId(), year, benchmarkRequest);
                }
            }
        } catch (Exception e) {
            log.error("Failed to migrate data benchmark: {}", e.getMessage(), e);
        }
    }
}
