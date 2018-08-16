package upgrad.movieapp.service.movie.dao;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.dao.BaseDao;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.CouponEntity;
import upgrad.movieapp.service.movie.entity.GenreEntity;

public interface CouponDao {

    SearchResult<CouponEntity> findAll();

    CouponEntity findByCode(@NotNull String couponCode);

}
