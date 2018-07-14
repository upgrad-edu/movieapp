package upgrad.movieapp.service.movie.business;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ArtistEntity;
import upgrad.movieapp.service.movie.model.ArtistSearchQuery;

public interface ArtistService {

    SearchResult<ArtistEntity> findArtists(@NotNull ArtistSearchQuery searchQuery);

    ArtistEntity findArtistByUuid(@NotNull String artistUuid) throws ApplicationException;

    SearchResult<ArtistEntity> findArtists(@NotNull String movieUuid) throws ApplicationException;

    ArtistEntity findArtist(@NotNull String movieUuid, @NotNull String artistUuid) throws ApplicationException;

    ArtistEntity createArtist(@NotNull ArtistEntity newArtist) throws ApplicationException;

    void updateArtist(@NotNull String artistUuid, @NotNull ArtistEntity updatedArtist) throws ApplicationException;

    void deleteArtist(@NotNull String artistUuid) throws ApplicationException;

}
