package upgrad.movieapp.service.movie.dao;

import javax.validation.constraints.NotNull;

import upgrad.movieapp.service.common.dao.BaseDao;
import upgrad.movieapp.service.movie.entity.ShowBookingEntity;

public interface BookingDao extends BaseDao<ShowBookingEntity> {

    ShowBookingEntity bookMovieShow(@NotNull ShowBookingEntity bookingEntity);

    ShowBookingEntity findByBookingReference(@NotNull String bookingRef);

}
