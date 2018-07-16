package upgrad.movieapp.service.movie.business;

import static upgrad.movieapp.service.movie.exception.MovieErrorCode.*;

import java.time.ZonedDateTime;
import java.util.Set;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import upgrad.movieapp.service.common.data.DateTimeProvider;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.exception.EntityNotFoundException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.dao.MovieDao;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieSearchQuery;
import upgrad.movieapp.service.movie.model.MovieStatus;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ArtistService artistService;

    @Override
    public SearchResult<MovieEntity> findMovies(@NotNull MovieSearchQuery searchQuery) {
        return movieDao.findMovies(searchQuery);
    }

    @Override
    public MovieEntity findMovieByUuid(String movieUuid) throws ApplicationException {
        return findExistingMovie(movieUuid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public MovieEntity createMovie(MovieEntity newMovie) throws ApplicationException {
        newMovie.setStatus(MovieStatus.PUBLISHED.name());
//        if (newMovie.getReleaseAt().isBefore(DateTimeProvider.currentProgramTime())) {
//            throw new ApplicationException(MVI_003);
//        }

        for (String genreUUid : newMovie.getGenreUuids()) {
            newMovie.addGenre(genreService.findGenre(genreUUid));
        }

        for (String artistUuid : newMovie.getArtistUuids()) {
            newMovie.addArtist(artistService.findArtistByUuid(artistUuid));
        }

        return movieDao.create(newMovie);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateMovie(String movieUuid, MovieEntity updatedMovie) throws ApplicationException {

        final ZonedDateTime releaseAt = updatedMovie.getReleaseAt();
//        if (releaseAt != null && releaseAt.isBefore(DateTimeProvider.currentProgramTime())) {
//            throw new ApplicationException(MVI_003);
//        }

        final MovieEntity existingMovie = findExistingMovie(movieUuid);
        if (MovieStatus.DELETED == MovieStatus.valueOf(existingMovie.getStatus())) {
            throw new ApplicationException(MVI_004, movieUuid);
        }

        if (StringUtils.isNotEmpty(updatedMovie.getTitle())) {
            existingMovie.setTitle(updatedMovie.getTitle());
        }
        if (StringUtils.isNotEmpty(updatedMovie.getStoryline())) {
            existingMovie.setStoryline(updatedMovie.getStoryline());
        }
        if (StringUtils.isNotEmpty(updatedMovie.getPosterUrl())) {
            existingMovie.setPosterUrl(updatedMovie.getPosterUrl());
        }
        if (StringUtils.isNotEmpty(updatedMovie.getTrailerUrl())) {
            existingMovie.setTrailerUrl(updatedMovie.getTrailerUrl());
        }
        if (StringUtils.isNotEmpty(updatedMovie.getWikiUrl())) {
            existingMovie.setWikiUrl(updatedMovie.getWikiUrl());
        }
        if (StringUtils.isNotEmpty(updatedMovie.getCensorBoardRating())) {
            existingMovie.setCensorBoardRating(updatedMovie.getCensorBoardRating());
        }
        if (updatedMovie.getRating() != null) {
            existingMovie.setRating(updatedMovie.getRating());
        }
        if (updatedMovie.getDuration() != null) {
            existingMovie.setDuration(updatedMovie.getDuration());
        }
        if (releaseAt != null) {
            existingMovie.setReleaseAt(releaseAt);
        }
        if (StringUtils.isNotEmpty(updatedMovie.getStatus())) {
            existingMovie.setStatus(updatedMovie.getStatus());
        }

        final Set<String> updatedGenreUuids = updatedMovie.getGenreUuids();
        if (CollectionUtils.isNotEmpty(updatedGenreUuids)) {
            for (String updatedGenreUuid : updatedGenreUuids) {
                existingMovie.addGenre(genreService.findGenre(updatedGenreUuid));
            }
        }

        final Set<String> updatedArtistUuids = updatedMovie.getArtistUuids();
        if (CollectionUtils.isNotEmpty(updatedArtistUuids)) {
            for (String updatedArtistUuid : updatedArtistUuids) {
                existingMovie.addArtist(artistService.findArtistByUuid(updatedArtistUuid));
            }
        }

        movieDao.update(existingMovie);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteMovie(String movieUuid) throws ApplicationException {

        final MovieEntity existingMovie = findExistingMovie(movieUuid);
        if (MovieStatus.DELETED == MovieStatus.valueOf(existingMovie.getStatus())) {
            throw new ApplicationException(MVI_004, movieUuid);
        }

        existingMovie.setStatus(MovieStatus.DELETED.name());
        movieDao.update(existingMovie);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStatus(final String movieUuid, final MovieStatus newStatus) throws ApplicationException {
        final MovieEntity existingMovie = findExistingMovie(movieUuid);
        if (newStatus != MovieStatus.valueOf(existingMovie.getStatus())) {
            existingMovie.setStatus(newStatus.name());
            movieDao.update(existingMovie);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateReleaseDate(String movieUuid, ZonedDateTime newReleaseAt) throws ApplicationException {
        final MovieEntity existingMovie = findExistingMovie(movieUuid);
        existingMovie.setReleaseAt(newReleaseAt);
        movieDao.update(existingMovie);

    }

    private MovieEntity findExistingMovie(final String movieUuid) throws EntityNotFoundException {
        final MovieEntity existingMovie = movieDao.findByUUID(movieUuid);
        if (existingMovie == null) {
            throw new EntityNotFoundException(MVI_001, movieUuid);
        }
        return existingMovie;
    }

}
