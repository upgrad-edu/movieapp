package upgrad.movieapp.service.movie.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.dao.GenreDao;
import upgrad.movieapp.service.movie.entity.GenreEntity;

@Component
public class GenreCacheProvider {

    @Autowired
    private GenreDao genreDao;

    @Cacheable("genres")
    public SearchResult<GenreEntity> findGenres() {
        return genreDao.findAll();
    }

    @Cacheable("genre")
    public GenreEntity findGenre(final String genreUuid) {
        return genreDao.findByUUID(genreUuid);
    }

}
