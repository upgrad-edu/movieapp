package upgrad.movieapp.service.movie.business;

import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.GenreEntity;

public interface GenreService {

    SearchResult<GenreEntity> findAllGenres();

    GenreEntity findGenre(final String genreUuid) throws ApplicationException;

}
