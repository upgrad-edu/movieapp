package upgrad.movieapp.service.movie.dao;

import static upgrad.movieapp.service.movie.entity.MovieEntity.*;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;
import upgrad.movieapp.service.common.dao.BaseDaoImpl;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieStatus;

@Repository
public class MovieDaoImpl extends BaseDaoImpl<MovieEntity> implements MovieDao {

    @Override
    public SearchResult<MovieEntity> findMovies(int page, int limit) {
        final int totalCount = entityManager.createNamedQuery(COUNT_BY_ALL, Long.class).getSingleResult().intValue();
        final List<MovieEntity> payload = entityManager.createNamedQuery(BY_ALL, MovieEntity.class).setFirstResult(getOffset(page, limit)).setMaxResults(limit).getResultList();
        return new SearchResult(totalCount, payload);
    }

    @Override
    public SearchResult<MovieEntity> findMovies(int page, int limit, MovieStatus... movieStatuses) {

        final Set<String> movieStatusNames = movieStatusNames(movieStatuses);

        final int totalCount = entityManager.createNamedQuery(COUNT_BY_STATUS, Long.class)
                .setParameter("status", movieStatusNames)
                .getSingleResult().intValue();
        final List<MovieEntity> payload = entityManager.createNamedQuery(BY_STATUS, MovieEntity.class)
                .setParameter("status", movieStatusNames)
                .setFirstResult(getOffset(page, limit)).setMaxResults(limit).getResultList();
        return new SearchResult(totalCount, payload);
    }

    @Override
    public SearchResult<MovieEntity> findMovies(int page, int limit, ZonedDateTime releaseAt) {
        final int totalCount = entityManager.createNamedQuery(COUNT_BY_RELEASE_AT, Long.class)
                .setParameter("releaseAt", releaseAt)
                .getSingleResult().intValue();
        final List<MovieEntity> payload = entityManager.createNamedQuery(BY_RELEASE_AT, MovieEntity.class)
                .setParameter("releaseAt", releaseAt)
                .setFirstResult(getOffset(page, limit)).setMaxResults(limit).getResultList();
        return new SearchResult(totalCount, payload);
    }

    @Override
    public SearchResult<MovieEntity> findMovies(int page, int limit, MovieStatus movieStatus, ZonedDateTime releaseAt) {
        final int totalCount = entityManager.createNamedQuery(COUNT_BY_STATUS_AND_RELEASE_AT, Long.class)
                .setParameter("status", movieStatus.name())
                .setParameter("releaseAt", releaseAt)
                .getSingleResult().intValue();
        final List<MovieEntity> payload = entityManager.createNamedQuery(BY_STATUS_AND_RELEASE_AT, MovieEntity.class)
                .setParameter("status", movieStatus.name())
                .setParameter("releaseAt", releaseAt)
                .setFirstResult(getOffset(page, limit)).setMaxResults(limit).getResultList();
        return new SearchResult(totalCount, payload);
    }

    private Set<String> movieStatusNames(final MovieStatus... movieStatuses) {
        final Set<String> movieStatusNames = new HashSet<>();
        for (MovieStatus movieStatus : movieStatuses) {
            movieStatusNames.add(movieStatus.name());
        }
        return movieStatusNames;
    }

}
