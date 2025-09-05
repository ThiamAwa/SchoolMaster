package sn.dev.schoolmaster.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import sn.dev.schoolmaster.entity.ClasseEntity;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ClasseDao {

    private final EntityManager em;


    public EntityManager getEm() {
        return em;
    }

    public Optional<ClasseEntity> getByClassName(String className) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ClasseEntity> cr = cb.createQuery(ClasseEntity.class);
        Root<ClasseEntity> classe = cr.from(ClasseEntity.class);

        cr.select(classe).where(cb.equal(classe.get("className"), className));

        List<ClasseEntity> result = em.createQuery(cr).getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public List<ClasseEntity> allClasseOrderByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ClasseEntity> cr = cb.createQuery(ClasseEntity.class);
        Root<ClasseEntity> classe = cr.from(ClasseEntity.class);

        cr.select(classe);
        cr.orderBy(cb.asc(classe.get("className")));

        return em.createQuery(cr).getResultList();
    }

    public Long countAllClasses() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cr = cb.createQuery(Long.class);
        Root<ClasseEntity> classe = cr.from(ClasseEntity.class);

        cr.select(cb.count(classe));

        return em.createQuery(cr).getSingleResult();
    }

    public List<Tuple> allIdAndName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cr = cb.createTupleQuery();
        Root<ClasseEntity> classe = cr.from(ClasseEntity.class);

        cr.multiselect(classe.get("id"), classe.get("className"));
        cr.orderBy(cb.asc(classe.get("className")));

        return em.createQuery(cr).getResultList();
    }
}
