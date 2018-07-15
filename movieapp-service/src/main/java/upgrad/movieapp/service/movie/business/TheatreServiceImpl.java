package upgrad.movieapp.service.movie.business;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.exception.EntityNotFoundException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.dao.TheatreDao;
import upgrad.movieapp.service.movie.entity.CityEntity;
import upgrad.movieapp.service.movie.entity.TheatreEntity;
import upgrad.movieapp.service.movie.exception.TheatreErrorCode;
import upgrad.movieapp.service.movie.model.TheatreSearchQuery;

@Service
public class TheatreServiceImpl implements TheatreService {

    @Autowired
    private CityService cityService;

    @Autowired
    private TheatreDao theatreDao;

    @Override
    public SearchResult<TheatreEntity> findTheatres(TheatreSearchQuery searchQuery) {

        SearchResult<TheatreEntity> theatres = theatreDao.findTheatres(searchQuery);
        theatres.getPayload().forEach(theatreEntity -> {
            theatreEntity.setCity(getCityName(theatreEntity.getCityCode()));
        });

        return theatres;
    }

    @Override
    public TheatreEntity findTheatreByUuid(String theatreUuid) throws ApplicationException {
        final TheatreEntity existingTheatre = findExistingTheatre(theatreUuid);
        existingTheatre.setCity(getCityName(existingTheatre.getCityCode()));
        return existingTheatre;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TheatreEntity createTheatre(TheatreEntity newTheatre) throws ApplicationException {
        newTheatre.setActive(true);
        validateCityCode(newTheatre.getCityCode());
        return theatreDao.create(newTheatre);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateTheatre(String theatreUuid, TheatreEntity updatedTheatre) throws ApplicationException {

        final TheatreEntity existingTheatre = findExistingTheatre(theatreUuid);
        existingTheatre.setActive(true);
        if (StringUtils.isNotEmpty(updatedTheatre.getName())) {
            existingTheatre.setName(updatedTheatre.getName());
        }
        if (StringUtils.isNotEmpty(updatedTheatre.getPostalAddress())) {
            existingTheatre.setPostalAddress(updatedTheatre.getPostalAddress());
        }
        if (StringUtils.isNotEmpty(updatedTheatre.getCityCode())) {
            validateCityCode(updatedTheatre.getCityCode());
            existingTheatre.setCityCode(updatedTheatre.getCityCode());
        }

        theatreDao.update(existingTheatre);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTheatre(String theatreUuid) throws ApplicationException {

        final TheatreEntity existingTheatre = findExistingTheatre(theatreUuid);
        existingTheatre.setActive(false);
        theatreDao.update(existingTheatre);
    }

    private TheatreEntity findExistingTheatre(final String theatreUuid) throws ApplicationException {
        final TheatreEntity theatreEntity = theatreDao.findByUUID(theatreUuid);
        if (theatreEntity == null) {
            throw new EntityNotFoundException(TheatreErrorCode.THR_001, theatreUuid);
        }
        return theatreEntity;
    }

    private void validateCityCode(final String cityCode) throws ApplicationException {
        if (cityService.findCity(cityCode) == null) {
            throw new EntityNotFoundException(TheatreErrorCode.THR_002, cityCode);
        }
    }

    private String getCityName(final String cityCode) {
        final CityEntity city = cityService.findCity(cityCode);
        if (city != null) {
            return city.getName();
        }
        return null;
    }

}
