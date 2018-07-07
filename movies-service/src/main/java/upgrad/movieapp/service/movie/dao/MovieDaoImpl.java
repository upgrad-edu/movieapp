package upgrad.movieapp.service.movie.dao;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Repository;
import upgrad.movieapp.service.common.dao.BaseDaoImpl;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieStatus;

@Repository
public class MovieDaoImpl extends BaseDaoImpl<MovieEntity> implements MovieDao {

    @Override
    public SearchResult<MovieEntity> findMovies(int offset, int limit) {
        return null;
    }

    @Override
    public SearchResult<MovieEntity> findMovies(MovieStatus userStatus, int offset, int limit) {
        return null;
    }

    @Override
    public SearchResult<MovieEntity> findMovies(MovieStatus userStatus, ZonedDateTime releaseDateOffset, int offset, int limit) {
        return null;
    }
}
