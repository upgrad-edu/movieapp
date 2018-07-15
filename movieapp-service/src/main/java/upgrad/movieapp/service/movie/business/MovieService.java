package upgrad.movieapp.service.movie.business;

import java.time.ZonedDateTime;
import java.util.Set;
import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ArtistEntity;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieSearchQuery;
import upgrad.movieapp.service.movie.model.MovieStatus;

public interface MovieService {

    SearchResult<MovieEntity> findMovies(@NotNull MovieSearchQuery searchQuery);

    MovieEntity findMovieByUuid(@NotNull String movieUuid) throws ApplicationException;

    MovieEntity createMovie(@NotNull MovieEntity newMovie) throws ApplicationException;

    void updateMovie(@NotNull String movieUuid, @NotNull MovieEntity updatedMovie) throws ApplicationException;

    void deleteMovie(@NotNull String movieUuid) throws ApplicationException;

    void updateStatus(@NotNull String movieUuid, MovieStatus newStatus) throws ApplicationException;

    void updateReleaseDate(@NotNull String movieUuid, ZonedDateTime newReleaseAt) throws ApplicationException;

}
