package upgrad.movieapp.service.movie.dao;

import upgrad.movieapp.service.common.dao.BaseDao;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieSearchQuery;

public interface MovieDao extends BaseDao<MovieEntity> {

    SearchResult<MovieEntity> findMovies(MovieSearchQuery searchQuery);

}
