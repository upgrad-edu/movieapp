package upgrad.movieapp.service.movie.business;

import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.CityEntity;

public interface CityService {

    SearchResult<CityEntity> findAllCities();

    CityEntity findCity(final String code) ;

}
