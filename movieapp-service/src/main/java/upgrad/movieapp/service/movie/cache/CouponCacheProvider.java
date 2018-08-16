package upgrad.movieapp.service.movie.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.dao.CouponDao;
import upgrad.movieapp.service.movie.entity.CouponEntity;

@Component
public class CouponCacheProvider {

    @Autowired
    private CouponDao couponDao;

    @Cacheable("coupons")
    public SearchResult<CouponEntity> findCoupons() {
        return couponDao.findAll();
    }

    @Cacheable("coupon")
    public CouponEntity findCoupon(final String couponCode) {
        return couponDao.findByCode(couponCode);
    }

}
