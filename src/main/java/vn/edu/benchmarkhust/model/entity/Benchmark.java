package vn.edu.benchmarkhust.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "benchmark")
public class Benchmark extends AbstractAuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String year;
    private Float mark;

    @ManyToOne(fetch = FetchType.EAGER)
    private Faculty faculty;

}
