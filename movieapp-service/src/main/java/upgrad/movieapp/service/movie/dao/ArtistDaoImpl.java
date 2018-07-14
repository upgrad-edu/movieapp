package upgrad.movieapp.service.movie.dao;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import upgrad.movieapp.service.common.dao.BaseDaoImpl;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ArtistEntity;
import upgrad.movieapp.service.movie.entity.ArtistEntity_;
import upgrad.movieapp.service.movie.model.ArtistRoleType;
import upgrad.movieapp.service.movie.model.ArtistSearchQuery;

@Repository
public class ArtistDaoImpl extends BaseDaoImpl<ArtistEntity> implements ArtistDao {

    @Override
    public SearchResult<ArtistEntity> findArtists(final ArtistSearchQuery searchQuery) {

        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ArtistEntity> payloadQuery = builder.createQuery(ArtistEntity.class);
        final Root<ArtistEntity> from = payloadQuery.from(ArtistEntity.class);

        final Predicate[] payloadPredicates = buildPredicates(searchQuery, builder, from);

        payloadQuery.select(from).where(payloadPredicates);

        final List<ArtistEntity> payload = entityManager.createQuery(payloadQuery)
                .setFirstResult(getOffset(searchQuery.getPage(), searchQuery.getLimit()))
                .setMaxResults(searchQuery.getLimit())
                .getResultList();

        final CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        final Root<ArtistEntity> countFrom = countQuery.from(ArtistEntity.class);

        final Predicate[] countPredicates = buildPredicates(searchQuery, builder, countFrom);
        countQuery.select(builder.count(countFrom)).where(countPredicates);
        final Integer totalCount = entityManager.createQuery(countQuery).getSingleResult().intValue();

        return new SearchResult(totalCount, payload);

    }

    private Predicate[] buildPredicates(final ArtistSearchQuery searchQuery, final CriteriaBuilder builder, final Root<ArtistEntity> root) {
        final List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(searchQuery.getName())) {
            final Predicate firstNamePredicate = builder.like(builder.lower(root.get(ArtistEntity_.firstName)), like(searchQuery.getName().toLowerCase()));
            final Predicate lastNamePredicate = builder.like(builder.lower(root.get(ArtistEntity_.lastName)), like(searchQuery.getName().toLowerCase()));
            predicates.add(builder.or(firstNamePredicate, lastNamePredicate));
        }

        final EnumSet<ArtistRoleType> roleTypes = searchQuery.getRoleTypes();
        if (CollectionUtils.isNotEmpty(roleTypes)) {
            Set<String> roleTypeNames = roleTypes.stream().map(roleType -> {
                return roleType.name();
            }).collect(Collectors.toSet());
            predicates.add(root.get(ArtistEntity_.type).in(roleTypeNames));
        }

        return predicates.toArray(new Predicate[]{});
    }

}
