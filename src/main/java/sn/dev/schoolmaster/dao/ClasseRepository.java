package sn.dev.schoolmaster.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.dev.schoolmaster.entity.ClasseEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClasseRepository extends JpaRepository<ClasseEntity, Long> {
    Optional<ClasseEntity> findByClassName(String className);
    List<ClasseEntity> findByClassNameContainingIgnoreCase(String className);
    List<ClasseEntity> findBySectorId(Long sectorId);
}
