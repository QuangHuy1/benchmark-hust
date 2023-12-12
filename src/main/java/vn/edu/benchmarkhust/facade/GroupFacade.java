package vn.edu.benchmarkhust.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.edu.benchmarkhust.model.request.GroupRequest;
import vn.edu.benchmarkhust.model.response.GroupResponse;
import vn.edu.benchmarkhust.service.GroupService;
import vn.edu.benchmarkhust.transfromer.GroupTransformer;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class GroupFacade {

    private final GroupService service;
    private final GroupTransformer transformer;

    public GroupResponse getById(Long id) {
        return transformer.toResponse(service.getOrElseThrow(id));
    }

    public GroupResponse create(GroupRequest request) {
        service.validateDuplicateCode(request.getCode());
        var group = transformer.fromRequest(request);
        return transformer.toResponse(service.save(group));
    }

    public List<GroupResponse> saveAll(List<GroupRequest> requests) {
        var groups = requests.stream().map(req -> {
            service.validateDuplicateCode(req.getCode());
            return transformer.fromRequest(req);
        }).collect(Collectors.toList());
        return service.saveAll(groups).stream().map(transformer::toResponse).collect(Collectors.toList());
    }

    public GroupResponse update(Long id, GroupRequest request) {
        var group = service.getOrElseThrow(id);
        transformer.setGroup(group, request);
        return transformer.toResponse(service.save(group));
    }

    public void deleteById(Long id) {
        var group = service.getOrElseThrow(id);
        service.delete(group);
    }
}
