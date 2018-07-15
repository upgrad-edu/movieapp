package upgrad.movieapp.service.movie.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import upgrad.movieapp.service.common.dao.BaseDaoImpl;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.TheatreEntity;
import upgrad.movieapp.service.movie.entity.TheatreEntity_;
import upgrad.movieapp.service.movie.model.TheatreSearchQuery;

@Repository
public class TheatreDaoImpl extends BaseDaoImpl<TheatreEntity> implements TheatreDao {

    @Override
    public SearchResult<TheatreEntity> findTheatres(TheatreSearchQuery searchQuery) {

        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<TheatreEntity> payloadQuery = builder.createQuery(TheatreEntity.class);
        final Root<TheatreEntity> from = payloadQuery.from(TheatreEntity.class);

        final Predicate[] payloadPredicates = buildPredicates(searchQuery, builder, from);

        payloadQuery.select(from).where(payloadPredicates);
        payloadQuery.orderBy(builder.asc(from.get(TheatreEntity_.name)));

        final List<TheatreEntity> payload = entityManager.createQuery(payloadQuery)
                .setFirstResult(getOffset(searchQuery.getPage(), searchQuery.getLimit()))
                .setMaxResults(searchQuery.getLimit())
                .getResultList();

        final CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        final Root<TheatreEntity> countFrom = countQuery.from(TheatreEntity.class);

        final Predicate[] countPredicates = buildPredicates(searchQuery, builder, countFrom);
        countQuery.select(builder.count(countFrom)).where(countPredicates);
        final Integer totalCount = entityManager.createQuery(countQuery).getSingleResult().intValue();

        return new SearchResult(totalCount, payload);
    }

    private Predicate[] buildPredicates(final TheatreSearchQuery searchQuery, final CriteriaBuilder builder, final Root<TheatreEntity> root) {

        final List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(searchQuery.getName())) {
            predicates.add(builder.like(builder.lower(root.get(TheatreEntity_.name)), like(searchQuery.getName().toLowerCase())));
        }
        if (searchQuery.isActive() != null) {
            predicates.add(builder.equal(root.get(TheatreEntity_.active), searchQuery.isActive().booleanValue()));
        }
        if (StringUtils.isNotEmpty(searchQuery.getCityCode())) {
            predicates.add(builder.equal(root.get(TheatreEntity_.cityCode), searchQuery.getCityCode()));
        }
        return predicates.toArray(new Predicate[]{});
    }

}
