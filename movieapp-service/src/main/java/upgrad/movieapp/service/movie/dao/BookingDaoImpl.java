package upgrad.movieapp.service.movie.dao;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;
import upgrad.movieapp.service.common.dao.BaseDaoImpl;
import upgrad.movieapp.service.movie.entity.ShowBookingEntity;

@Repository
public class BookingDaoImpl extends BaseDaoImpl<ShowBookingEntity> implements BookingDao {

    @Override
    public ShowBookingEntity bookMovieShow(final ShowBookingEntity bookingEntity) {
        return null;
    }

    @Override
    public ShowBookingEntity findByBookingReference(final String bookingRef) {
        try {
            return entityManager.createNamedQuery(ShowBookingEntity.BY_BOOKING_REF, ShowBookingEntity.class)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
