package upgrad.movieapp.service.movie.business;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.CouponEntity;

public interface CouponService {

    SearchResult<CouponEntity> findAllCoupons();

    CouponEntity findCoupon(@NotNull String couponCode) throws ApplicationException;

}
