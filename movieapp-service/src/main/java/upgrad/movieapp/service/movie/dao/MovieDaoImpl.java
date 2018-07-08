package upgrad.movieapp.service.movie.dao;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;
import upgrad.movieapp.service.common.dao.BaseDaoImpl;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieStatus;

@Repository
public class MovieDaoImpl extends BaseDaoImpl<MovieEntity> implements MovieDao {

    @Override
    public SearchResult<MovieEntity> findMovies(int page, int limit) {
        final int totalCount = entityManager.createNamedQuery(MovieEntity.COUNT_BY_ALL, Long.class).getSingleResult().intValue();
        final List<MovieEntity> payload = entityManager.createNamedQuery(MovieEntity.BY_ALL, MovieEntity.class).setFirstResult(getOffset(page, limit)).setMaxResults(limit).getResultList();
        return new SearchResult(totalCount, payload);
    }

    @Override
    public SearchResult<MovieEntity> findMovies(int page, int limit, MovieStatus movieStatus) {
        final int totalCount = entityManager.createNamedQuery(MovieEntity.COUNT_BY_ALL, Long.class)
                .setParameter("status", movieStatus.name())
                .getSingleResult().intValue();
        final List<MovieEntity> payload = entityManager.createNamedQuery(MovieEntity.BY_ALL, MovieEntity.class)
                .setParameter("status", movieStatus.name())
                .setFirstResult(getOffset(page, limit)).setMaxResults(limit).getResultList();
        return new SearchResult(totalCount, payload);
    }

    @Override
    public SearchResult<MovieEntity> findMovies(int page, int limit, ZonedDateTime releaseAt) {
        return null;
    }

    @Override
    public SearchResult<MovieEntity> findMovies(int page, int limit, MovieStatus userStatus, ZonedDateTime releaseAt) {
        return null;
    }

    @Override
    public SearchResult<MovieEntity> findMovies(MovieStatus userStatus, ZonedDateTime releaseDateOffset, int offset, int limit) {
        return null;
    }
}
