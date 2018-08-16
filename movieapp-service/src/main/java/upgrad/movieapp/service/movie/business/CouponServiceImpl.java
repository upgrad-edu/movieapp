package upgrad.movieapp.service.movie.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.cache.CouponCacheProvider;
import upgrad.movieapp.service.movie.entity.CouponEntity;
import upgrad.movieapp.service.movie.exception.BookingErrorCode;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponCacheProvider cacheProvider;

    @Override
    public SearchResult<CouponEntity> findAllCoupons() {
        return cacheProvider.findCoupons();
    }

    @Override
    public CouponEntity findCoupon(final String couponCode) throws ApplicationException {
        final CouponEntity coupon = cacheProvider.findCoupon(couponCode);
        if(coupon == null) {
            throw new ApplicationException(BookingErrorCode.BKG_006, couponCode);
        }
        return coupon;
    }

}
