package upgrad.movieapp.service.movie.dao;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.dao.BaseDao;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.CityEntity;
import upgrad.movieapp.service.movie.entity.GenreEntity;

public interface CityDao extends BaseDao<CityEntity> {

    SearchResult<CityEntity> findAll();

    CityEntity findByCode(@NotNull String code);

}
