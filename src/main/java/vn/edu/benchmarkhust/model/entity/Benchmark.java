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
@Table(name = "benchmark")
public class Benchmark extends AbstractAuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private Float score;

    @Enumerated(EnumType.STRING)
    private GroupType groupType;

    @ManyToOne
    private Faculty faculty;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "benchmark_group",
            joinColumns = @JoinColumn(name = "benchmark_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups = new HashSet<>();

}
