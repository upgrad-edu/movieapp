package upgrad.movieapp.service.movie.dao;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.dao.BaseDao;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ArtistEntity;
import upgrad.movieapp.service.movie.model.ArtistSearchQuery;

public interface ArtistDao extends BaseDao<ArtistEntity> {

    SearchResult<ArtistEntity> findArtists(@NotNull ArtistSearchQuery searchQuery);

}
