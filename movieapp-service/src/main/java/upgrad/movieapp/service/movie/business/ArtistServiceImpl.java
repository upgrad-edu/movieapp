package upgrad.movieapp.service.movie.business;

import static upgrad.movieapp.service.movie.exception.ArtistErrorCode.ART_001;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.exception.EntityNotFoundException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.cache.ArtistCacheProvider;
import upgrad.movieapp.service.movie.dao.ArtistDao;
import upgrad.movieapp.service.movie.entity.ArtistEntity;
import upgrad.movieapp.service.movie.entity.MovieEntity;
import upgrad.movieapp.service.movie.model.ArtistSearchQuery;

@Service
public class ArtistServiceImpl implements ArtistService {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ArtistDao artistDao;

    @Autowired
    private ArtistCacheProvider cacheProvider;

    @Override
    public SearchResult<ArtistEntity> findArtists(@NotNull ArtistSearchQuery searchQuery) {
        return artistDao.findArtists(searchQuery);
    }

    @Override
    public ArtistEntity findArtistByUuid(String artistUuid) throws ApplicationException {
        return findExistingArtist(artistUuid);
    }

    @Override
    public SearchResult<ArtistEntity> findArtists(String movieUuid) throws ApplicationException {

        final MovieEntity existingMovie = movieService.findMovieByUuid(movieUuid);
        final List<ArtistEntity> artists = new ArrayList<>();
        existingMovie.getArtists().forEach(movieArtistEntity -> {
            artists.add(movieArtistEntity.getArtist());
        });
        return new SearchResult(artists.size(), artists);
    }

    @Override
    public ArtistEntity findArtist(String movieUuid, String artistUuid) throws ApplicationException {
        movieService.findMovieByUuid(movieUuid); //just to check the movie status
        return findExistingArtist(artistUuid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ArtistEntity createArtist(final ArtistEntity newArtist) throws ApplicationException {
        newArtist.setActive(true);
        return artistDao.create(newArtist);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateArtist(final String artistUuid, final ArtistEntity updatedArtist) throws ApplicationException {

        final ArtistEntity existingArtistEntity = findExistingArtist(artistUuid);
        if (StringUtils.isNotEmpty(updatedArtist.getFirstName())) {
            existingArtistEntity.setFirstName(updatedArtist.getFirstName());
        }
        if (StringUtils.isNotEmpty(updatedArtist.getLastName())) {
            existingArtistEntity.setLastName(updatedArtist.getLastName());
        }
        if (StringUtils.isNotEmpty(updatedArtist.getProfilePictureUrl())) {
            existingArtistEntity.setProfilePictureUrl(updatedArtist.getProfilePictureUrl());
        }
        if (StringUtils.isNotEmpty(updatedArtist.getProfileDescription())) {
            existingArtistEntity.setProfileDescription(updatedArtist.getProfileDescription());
        }
        if (StringUtils.isNotEmpty(updatedArtist.getWikiUrl())) {
            existingArtistEntity.setWikiUrl(updatedArtist.getWikiUrl());
        }
        if (StringUtils.isNotEmpty(updatedArtist.getType())) {
            existingArtistEntity.setType(updatedArtist.getType());
        }

        artistDao.update(existingArtistEntity);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteArtist(@NotNull String artistUuid) throws ApplicationException {
        final ArtistEntity existingArtistEntity = findExistingArtist(artistUuid);
        existingArtistEntity.setActive(false);
        artistDao.update(existingArtistEntity);
    }

    private ArtistEntity findExistingArtist(final String artistUuid) throws ApplicationException {
        final ArtistEntity existingArtist = cacheProvider.findArtist(artistUuid);
        if (existingArtist == null) {
            throw new EntityNotFoundException(ART_001, artistUuid);
        }
        return existingArtist;
    }

}
