package upgrad.movieapp.service.movie.business;

import static upgrad.movieapp.service.movie.exception.MovieErrorCode.MVI_001;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upgrad.movieapp.service.common.data.DateTimeProvider;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.exception.EntityNotFoundException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.dao.MovieDao;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.MovieStatus;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Override
    public SearchResult<MovieEntity> findMovies(int page, int limit) {
        return movieDao.findMovies(page, limit);
    }

    @Override
    public SearchResult<MovieEntity> findMovies(int page, int limit, MovieStatus movieStatus) {
        return movieDao.findMovies(page, limit, movieStatus);
    }

    @Override
    public SearchResult<MovieEntity> findMovies(int page, int limit, int releaseDateOffset) {
        return movieDao.findMovies(page, limit, DateTimeProvider.currentProgramTime().plusDays(releaseDateOffset));
    }

    @Override
    public SearchResult<MovieEntity> findMovies(int page, int limit, MovieStatus movieStatus, int releaseDateOffset) {
        return movieDao.findMovies(page, limit, movieStatus, DateTimeProvider.currentProgramTime().plusDays(releaseDateOffset));
    }

    @Override
    public MovieEntity findMovieByUuid(String movieUuid) throws ApplicationException {
        return findExistingMovie(movieUuid);
    }

    @Override
    public MovieEntity createMovie(MovieEntity newMovie) throws ApplicationException {
        return movieDao.create(newMovie);
    }

    @Override
    public void updateMovie(String movieUuid, MovieEntity updatedMovie) throws ApplicationException {

        final MovieEntity existingMovie = findExistingMovie(movieUuid);
        if(StringUtils.isNotEmpty(updatedMovie.getTitle())) {
            existingMovie.setTitle(updatedMovie.getTitle());
        }
        if(StringUtils.isNotEmpty(updatedMovie.getGenres())) {
            existingMovie.setGenres(updatedMovie.getGenres());
        }
        if(StringUtils.isNotEmpty(updatedMovie.getStoryline())) {
            existingMovie.setStoryline(updatedMovie.getStoryline());
        }
        if(StringUtils.isNotEmpty(updatedMovie.getPosterUrl())) {
            existingMovie.setPosterUrl(updatedMovie.getPosterUrl());
        }
        if(StringUtils.isNotEmpty(updatedMovie.getTrailerUrl())) {
            existingMovie.setTrailerUrl(updatedMovie.getTrailerUrl());
        }
        if(StringUtils.isNotEmpty(updatedMovie.getWikiUrl())) {
            existingMovie.setWikiUrl(updatedMovie.getWikiUrl());
        }
        if(StringUtils.isNotEmpty(updatedMovie.getCensorBoardRating())) {
            existingMovie.setCensorBoardRating(updatedMovie.getCensorBoardRating());
        }
        if(updatedMovie.getCriticsRating() != null) {
            existingMovie.setCriticsRating(updatedMovie.getCriticsRating());
        }
        if(StringUtils.isNotEmpty(updatedMovie.getStatus())) {
            existingMovie.setStatus(updatedMovie.getStatus());
        }

        movieDao.update(existingMovie);
    }

    @Override
    public void deleteMovie(String movieUuid) throws ApplicationException {
        final MovieEntity existingMovie = findExistingMovie(movieUuid);
        existingMovie.setStatus(MovieStatus.DELETED.name());
        movieDao.update(existingMovie);
    }

    private MovieEntity findExistingMovie(final String movieUuid) throws EntityNotFoundException {
        final MovieEntity existingMovie = movieDao.findByUUID(movieUuid);
        if(existingMovie == null) {
            throw new EntityNotFoundException(MVI_001, movieUuid);
        }
        return existingMovie;
    }

}
