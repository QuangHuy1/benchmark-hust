package vn.edu.benchmarkhust.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.entity.School;
import vn.edu.benchmarkhust.model.request.SchoolRequest;
import vn.edu.benchmarkhust.model.response.SchoolResponse;
import vn.edu.benchmarkhust.service.SchoolService;
import vn.edu.benchmarkhust.transfromer.SchoolTransformer;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchoolFacade {

    private final SchoolService service;
    private final SchoolTransformer transformer;

    public SchoolResponse getById(Long id) {
        return transformer.toResponse(service.getOrElseThrow(id));
    }

    public SchoolResponse create(SchoolRequest request) {
        var school = transformer.fromRequest(request);
        var saved = service.save(school);
        return transformer.toResponse(saved);
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
