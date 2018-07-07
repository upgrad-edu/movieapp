package upgrad.movieapp.service.movie.dao;

import java.time.ZonedDateTime;

import upgrad.movieapp.service.common.dao.BaseDao;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieStatus;

public interface MovieDao extends BaseDao<MovieEntity> {

    SearchResult<MovieEntity> findMovies(int offset, int limit);

    SearchResult<MovieEntity> findMovies(MovieStatus userStatus, int offset, int limit);

    SearchResult<MovieEntity> findMovies(MovieStatus userStatus, ZonedDateTime releaseDateOffset, int offset, int limit);

}
