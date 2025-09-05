package sn.dev.schoolmaster.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.dev.schoolmaster.entity.ClasseEntity;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "sectors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SectorEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClasseEntity> classes;
}

