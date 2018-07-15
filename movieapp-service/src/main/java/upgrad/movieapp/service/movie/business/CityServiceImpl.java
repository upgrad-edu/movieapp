package upgrad.movieapp.service.movie.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.cache.CityCacheProvider;
import upgrad.movieapp.service.movie.entity.CityEntity;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityCacheProvider cacheProvider;

    @Override
    public SearchResult<CityEntity> findAllCities() {
        return cacheProvider.findCities();
    }

    @Override
    public CityEntity findCity(String code) {
        return cacheProvider.findCity(code);
    }
}
