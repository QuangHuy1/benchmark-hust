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
    @Column(name = "id")
    private Long id;

    private String code;
    private String name;

    @ManyToOne
    private School school;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private Set<Benchmark> benchmarks = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "faculties")
    private Set<Group> group = new HashSet<>();

}
