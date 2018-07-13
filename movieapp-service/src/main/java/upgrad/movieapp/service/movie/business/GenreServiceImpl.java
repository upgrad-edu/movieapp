package upgrad.movieapp.service.movie.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.cache.GenreCacheProvider;
import upgrad.movieapp.service.movie.entity.GenreEntity;
import upgrad.movieapp.service.movie.exception.MovieErrorCode;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreCacheProvider cacheProvider;

    @Override
    public SearchResult<GenreEntity> findAllGenres() {
        return cacheProvider.findGenres();
    }

    @Override
    public GenreEntity findGenre(String genreUuid) throws ApplicationException {
        final GenreEntity genre = cacheProvider.findGenre(genreUuid);
        if (genre == null) {
            throw new ApplicationException(MovieErrorCode.MVI_005, genreUuid);
        }
        return genre;
    }
}
