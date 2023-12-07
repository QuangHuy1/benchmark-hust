package vn.edu.benchmarkhust.model.entity;

import lombok.Getter;
import lombok.Setter;

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
    @Column(name = "id")
    private Long id;
    private String code;
    private String subject1;
    private String subject2;
    private String subject3;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "faculty_group",
            joinColumns = @JoinColumn(name = "faculty_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Faculty> faculties = new HashSet<>();


}
