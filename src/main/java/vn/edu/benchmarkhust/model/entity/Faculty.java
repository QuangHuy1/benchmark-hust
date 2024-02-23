package vn.edu.benchmarkhust.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "faculty")
public class Faculty extends AbstractAuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private float avgBenchmark;

    @ManyToOne(fetch = FetchType.EAGER)
    private School school;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private Set<Benchmark> benchmarks = new HashSet<>();

}
