package vn.edu.benchmarkhust.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "school")
public class School extends AbstractAuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vnName;
    private String enName;
    private String abbreviations;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private Set<Faculty> faculties = new HashSet<>();
}
