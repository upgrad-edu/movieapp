package upgrad.movieapp.service.movie.dao;

import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import upgrad.movieapp.service.common.dao.BaseDao;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ShowBookingEntity;
import upgrad.movieapp.service.movie.model.BookingSearchQuery;

public interface BookingDao extends BaseDao<ShowBookingEntity> {

    ShowBookingEntity findByCustomer(@NotNull String customerUuid, @NotNull String bookingUuid);

    ShowBookingEntity findByBookingReference(@NotNull String bookingRef);

    SearchResult<ShowBookingEntity> findBookings(@NotNull BookingSearchQuery searchQuery);

    List<String> findBookedTickets(@NotNull String showUuid, @NotNull @Size(min = 1) Set<String> ticketNumbers);

}
