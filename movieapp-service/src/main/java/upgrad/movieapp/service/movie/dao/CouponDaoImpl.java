package upgrad.movieapp.service.movie.dao;

import static upgrad.movieapp.service.movie.entity.CouponEntity.*;

import java.util.List;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;
import upgrad.movieapp.service.common.dao.BaseDaoImpl;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.CouponEntity;
import upgrad.movieapp.service.movie.entity.CouponEntity_;

@Repository
public class CouponDaoImpl extends BaseDaoImpl<CouponEntity> implements CouponDao {

    @Override
    public SearchResult<CouponEntity> findAll() {
        final int totalCount = entityManager.createNamedQuery(COUNT_BY_ALL, Long.class).getSingleResult().intValue();
        final List<CouponEntity> payload = entityManager.createNamedQuery(BY_ALL, CouponEntity.class).getResultList();
        return new SearchResult(totalCount, payload);
    }

    @Override
    public CouponEntity findByCode(String couponCode) {
        try {
            return entityManager.createNamedQuery(BY_CODE, CouponEntity.class).setParameter(CouponEntity_.code.getName(), couponCode).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
