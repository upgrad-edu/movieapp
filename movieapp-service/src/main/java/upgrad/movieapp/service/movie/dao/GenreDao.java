package upgrad.movieapp.service.movie.dao;

import upgrad.movieapp.service.common.dao.BaseDao;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.GenreEntity;

public interface GenreDao extends BaseDao<GenreEntity> {

    SearchResult<GenreEntity> findAll();

}
