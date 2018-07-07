package upgrad.movieapp.service.movie.business;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieStatus;

public interface MovieService {

    SearchResult<MovieEntity> findMovies(int page, int limit);

    SearchResult<MovieEntity> findMovies(int page, int limit, @NotNull MovieStatus movieStatus);

    SearchResult<MovieEntity> findMovies(int page, int limit, int releaseDateOffset);

    SearchResult<MovieEntity> findMovies(int page, int limit, @NotNull MovieStatus movieStatus, int releaseDateOffset);

    MovieEntity createMovie(@NotNull MovieEntity newMovie) throws ApplicationException;

    void updateMovie(@NotNull String movieUuid, @NotNull MovieEntity updatedMovie) throws ApplicationException;

    void deleteMovie(@NotNull String movieUuid) throws ApplicationException;

}
