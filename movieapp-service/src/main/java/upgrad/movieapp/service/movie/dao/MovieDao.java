package upgrad.movieapp.service.movie.dao;

import java.time.ZonedDateTime;
import java.util.EnumSet;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.dao.BaseDao;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ArtistEntity;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieStatus;

public interface MovieDao extends BaseDao<MovieEntity> {

    SearchResult<MovieEntity> findMovies(int page, int limit);

    SearchResult<MovieEntity> findMovies(int page, int limit, @NotNull MovieStatus... movieStatuses);

    SearchResult<MovieEntity> findMovies(int page, int limit, ZonedDateTime releaseAt);

    SearchResult<MovieEntity> findMovies(int page, int limit, MovieStatus movieStatus, ZonedDateTime releaseAt);

}
