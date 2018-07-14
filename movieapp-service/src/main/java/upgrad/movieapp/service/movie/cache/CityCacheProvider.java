package upgrad.movieapp.service.movie.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.dao.CityDao;
import upgrad.movieapp.service.movie.entity.CityEntity;

@Component
public class CityCacheProvider {

    @Autowired
    private CityDao cityDao;

    @Cacheable("cities")
    public SearchResult<CityEntity> findCities() {
        return cityDao.findAll();
    }

    @Cacheable("city")
    public CityEntity findCity(final String code) {
        return cityDao.findByCode(code);
    }

}
