package upgrad.movieapp.service.movie.dao;

import upgrad.movieapp.service.common.dao.BaseDao;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ArtistEntity;
import upgrad.movieapp.service.movie.model.ArtistRoleType;

public interface ArtistDao extends BaseDao<ArtistEntity> {

    SearchResult<ArtistEntity> findArtists(int page, int limit);

    SearchResult<ArtistEntity> findArtists(int page, int limit, ArtistRoleType... roleTypes);

}
