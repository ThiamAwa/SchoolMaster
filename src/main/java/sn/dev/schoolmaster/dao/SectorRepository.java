package sn.dev.schoolmaster.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.dev.schoolmaster.entity.SectorEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<SectorEntity, Long> {
    // Méthode pour rechercher un secteur par son nom
    List<SectorEntity> findByName(String name);
    // vérifier si un secteur existe déjà par nom

    List<SectorEntity> findByNameContainingIgnoreCase(String name);
}
