package upgrad.movieapp.service.movie.dao;

import upgrad.movieapp.service.common.dao.BaseDao;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.TheatreEntity;
import upgrad.movieapp.service.movie.model.TheatreSearchQuery;

public interface TheatreDao extends BaseDao<TheatreEntity> {

    SearchResult<TheatreEntity> findTheatres(TheatreSearchQuery searchQuery);

}
