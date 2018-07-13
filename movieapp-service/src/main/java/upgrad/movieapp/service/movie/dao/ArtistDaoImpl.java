package upgrad.movieapp.service.movie.dao;

import static upgrad.movieapp.service.movie.entity.ArtistEntity.BY_ALL;
import static upgrad.movieapp.service.movie.entity.ArtistEntity.COUNT_BY_ALL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;
import upgrad.movieapp.service.common.dao.BaseDaoImpl;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ArtistEntity;
import upgrad.movieapp.service.movie.model.ArtistRoleType;

@Repository
public class ArtistDaoImpl extends BaseDaoImpl<ArtistEntity> implements ArtistDao {

    @Override
    public SearchResult<ArtistEntity> findArtists(int page, int limit) {
        final int totalCount = entityManager.createNamedQuery(COUNT_BY_ALL, Long.class).getSingleResult().intValue();
        final List<ArtistEntity> payload = entityManager.createNamedQuery(BY_ALL, ArtistEntity.class).setFirstResult(getOffset(page, limit)).setMaxResults(limit).getResultList();
        return new SearchResult(totalCount, payload);
    }

    @Override
    public SearchResult<ArtistEntity> findArtists(int page, int limit, ArtistRoleType... roleTypes) {

        final Set<String> roleTypeNames = roleTypes(roleTypes);

        final int totalCount = entityManager.createNamedQuery(ArtistEntity.COUNT_BY_TYPE, Long.class)
                .setParameter("types", roleTypeNames)
                .getSingleResult().intValue();
        final List<ArtistEntity> payload = entityManager.createNamedQuery(ArtistEntity.BY_TYPE, ArtistEntity.class)
                .setParameter("types", roleTypeNames)
                .setFirstResult(getOffset(page, limit)).setMaxResults(limit).getResultList();
        return new SearchResult(totalCount, payload);
    }

    private Set<String> roleTypes(final ArtistRoleType... roleTypes) {
        final Set<String> roleTypeNames = new HashSet<>();
        for (ArtistRoleType roleType : roleTypes) {
            roleTypeNames.add(roleType.name());
        }
        return roleTypeNames;
    }

}
