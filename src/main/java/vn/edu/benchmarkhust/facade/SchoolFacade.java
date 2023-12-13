package vn.edu.benchmarkhust.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.request.SchoolRequest;
import vn.edu.benchmarkhust.model.response.SchoolResponse;
import vn.edu.benchmarkhust.service.SchoolService;
import vn.edu.benchmarkhust.transfromer.SchoolTransformer;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchoolFacade {

    private final SchoolService service;
    private final SchoolTransformer transformer;

    public SchoolResponse getById(Long id) {
        return transformer.toResponse(service.getOrElseThrow(id));
    }

    public List<SchoolResponse> getAll() {
        return service.getAll().stream().map(transformer::toResponse).collect(Collectors.toList());
    }

    public void create(SchoolRequest request) {
        var school = transformer.fromRequest(request);
        service.save(school);
    }

    public SchoolResponse update(Long id, SchoolRequest request) {
        var school = service.getOrElseThrow(id);
        transformer.setSchool(school, request);
        return transformer.toResponse(service.save(school));
    }

    public void deleteById(Long id) {
        var school = service.getOrElseThrow(id);
        service.delete(school);
    }
}
