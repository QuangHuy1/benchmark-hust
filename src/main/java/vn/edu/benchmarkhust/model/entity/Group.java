package vn.edu.benchmarkhust.model.entity;

import lombok.Getter;
import lombok.Setter;
import vn.edu.benchmarkhust.common.GroupType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "`Group`")
public class Group extends AbstractAuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String subject1;
    private String subject2;
    private String subject3;

    @Enumerated(EnumType.STRING)
    private GroupType groupType;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "groups")
    private Set<Benchmark> benchmarks = new HashSet<>();

}
