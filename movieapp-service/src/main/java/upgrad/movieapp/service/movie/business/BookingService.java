package upgrad.movieapp.service.movie.business;

import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.ShowBookingEntity;
import upgrad.movieapp.service.movie.model.BookingSearchQuery;

public interface BookingService {

    ShowBookingEntity bookMovieShow(@NotNull String showUuid,
                                    @NotNull String customerUuid, @NotNull @Size(min = 1) Set<String> ticketNumbers) throws ApplicationException;

    void cancelBooking(@NotNull String customerUuid, @NotNull String bookingUuid) throws ApplicationException;

    SearchResult<ShowBookingEntity> findBookings(@NotNull BookingSearchQuery searchQuery);

}
