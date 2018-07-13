package upgrad.movieapp.service.movie.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import upgrad.movieapp.service.movie.dao.ArtistDao;
import upgrad.movieapp.service.movie.entity.ArtistEntity;

@Component
public class ArtistCacheProvider {

    @Autowired
    private ArtistDao artistDao;

    @Cacheable("artist")
    public ArtistEntity findArtist(final String artistUuid) {
        return artistDao.findByUUID(artistUuid);
    }

}
