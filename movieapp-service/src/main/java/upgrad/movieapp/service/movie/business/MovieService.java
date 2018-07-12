package upgrad.movieapp.service.movie.business;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieStatus;
import upgrad.movieapp.service.user.entity.UserEntity;

public interface MovieService {

    SearchResult<MovieEntity> findMovies(int page, int limit);

    SearchResult<MovieEntity> findMovies(int page, int limit, @NotNull MovieStatus movieStatus);

    SearchResult<MovieEntity> findMovies(int page, int limit, int releaseDateOffset);

    SearchResult<MovieEntity> findMovies(int page, int limit, @NotNull MovieStatus movieStatus, int releaseDateOffset);

    MovieEntity findMovieByUuid(@NotNull String movieUuid) throws ApplicationException;

    MovieEntity createMovie(@NotNull MovieEntity newMovie) throws ApplicationException;

    void updateMovie(@NotNull String movieUuid, @NotNull MovieEntity updatedMovie) throws ApplicationException;

    void deleteMovie(@NotNull String movieUuid) throws ApplicationException;

    void updateStatus(@NotNull String movieUuid, MovieStatus newStatus) throws ApplicationException;

    void updateReleaseDate(@NotNull String movieUuid, ZonedDateTime newReleaseAt) throws ApplicationException;

}
