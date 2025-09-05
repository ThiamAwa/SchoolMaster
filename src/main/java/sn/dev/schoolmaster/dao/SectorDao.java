package sn.dev.schoolmaster.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import sn.dev.schoolmaster.entity.SectorEntity;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SectorDao {

    private final EntityManager em;

    public Optional<SectorEntity> getByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SectorEntity> cr = cb.createQuery(SectorEntity.class);
        Root<SectorEntity> sector = cr.from(SectorEntity.class);

        cr.select(sector).where(cb.equal(sector.get("name"), name));

        List<SectorEntity> result = em.createQuery(cr).getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public List<SectorEntity> allSectorsOrderByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SectorEntity> cr = cb.createQuery(SectorEntity.class);
        Root<SectorEntity> sector = cr.from(SectorEntity.class);

        cr.select(sector);
        cr.orderBy(cb.asc(sector.get("name")));

        return em.createQuery(cr).getResultList();
    }

    public Long countAllSectors() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cr = cb.createQuery(Long.class);
        Root<SectorEntity> sector = cr.from(SectorEntity.class);

        cr.select(cb.count(sector));

        return em.createQuery(cr).getSingleResult();
    }
}
