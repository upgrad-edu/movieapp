package upgrad.movieapp.service.movie.dao;

import static upgrad.movieapp.service.movie.entity.CityEntity.BY_ALL;
import static upgrad.movieapp.service.movie.entity.CityEntity.COUNT_BY_ALL;

import java.util.List;
import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;
import upgrad.movieapp.service.common.dao.BaseDaoImpl;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.CityEntity;
import upgrad.movieapp.service.movie.entity.CityEntity_;

@Repository
public class CityDaoImpl extends BaseDaoImpl<CityEntity> implements CityDao {

    @Override
    public SearchResult<CityEntity> findAll() {
        final int totalCount = entityManager.createNamedQuery(COUNT_BY_ALL, Long.class).getSingleResult().intValue();
        final List<CityEntity> payload = entityManager.createNamedQuery(BY_ALL, CityEntity.class).getResultList();
        return new SearchResult(totalCount, payload);
    }

    @Override
    public CityEntity findByCode(@NotNull String code) {
        try {
            return entityManager.createNamedQuery(CityEntity.BY_CODE, CityEntity.class)
                    .setParameter(CityEntity_.code.getName(), code)
                    .getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }
    }

}
