package vn.edu.benchmarkhust.model.request;

import lombok.Data;
import vn.edu.benchmarkhust.model.entity.School;

import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Set;

@Data
public class FacultyRequest {

    private String code;
    private String name;
    private Long schoolId;
    private Set<Long> groupIds;

}
