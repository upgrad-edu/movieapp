package upgrad.movieapp.service.movie.business;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import upgrad.movieapp.service.common.data.DateTimeProvider;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.exception.EntityNotFoundException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.dao.BookingDao;
import upgrad.movieapp.service.movie.entity.BookingTicketEntity;
import upgrad.movieapp.service.movie.entity.ShowBookingEntity;
import upgrad.movieapp.service.movie.entity.ShowEntity;
import upgrad.movieapp.service.movie.exception.BookingErrorCode;
import upgrad.movieapp.service.movie.model.BookingSearchQuery;
import upgrad.movieapp.service.movie.model.BookingStatus;
import upgrad.movieapp.service.user.business.UserService;
import upgrad.movieapp.service.user.entity.UserEntity;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private UserService userService;

    @Autowired
    private ShowService showService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private BookingDao bookingDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ShowBookingEntity bookMovieShow(final String showUuid, final String customerUuid,
                                           final Set<String> ticketNumbers) throws ApplicationException {

        final UserEntity userEntity = userService.findUserByUuid(customerUuid);
        final ShowEntity showEntity = showService.findShowByUuid(showUuid);

        final ShowBookingEntity bookingEntity;
        synchronized (this) {
            bookingEntity = new ShowBookingEntity();
            bookingEntity.setShow(showEntity);
            bookingEntity.setCustomer(userEntity);

            final int totalTickets = ticketNumbers.size();
            bookingEntity.setTotalSeats(totalTickets);
            bookingEntity.setTotalPrice(showEntity.getUnitPrice().multiply(new BigDecimal(totalTickets)));

            final ZonedDateTime bookingAt = DateTimeProvider.currentProgramTime();
            bookingEntity.setBookingAt(bookingAt);
            bookingEntity.setBookingReference(generateBookingReference(bookingAt));
            bookingEntity.setStatus(BookingStatus.CONFIRMED.name());

            ticketNumbers.forEach(ticketNumber -> {
                BookingTicketEntity bookingTicketEntity = new BookingTicketEntity();
                bookingTicketEntity.setBooking(bookingEntity);
                bookingTicketEntity.setTicketNumber(ticketNumber);
                bookingEntity.getBookingTickets().add(bookingTicketEntity);
            });

            bookingDao.create(bookingEntity);

            showService.updateTicketsAvailability(showEntity, totalTickets);
        }

        return bookingEntity;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelBooking(final String customerUuid, final String bookingUuid) throws ApplicationException {
        final ShowBookingEntity bookingEntity = bookingDao.findByUUID(bookingUuid);
        if (bookingEntity == null) {
            throw new EntityNotFoundException(BookingErrorCode.BKG_001, bookingUuid);
        }
        bookingEntity.setStatus(BookingStatus.CANCELLED.name());
        bookingEntity.setCancelledAt(DateTimeProvider.currentProgramTime());

        synchronized (this) {
            bookingDao.update(bookingEntity);

            final ShowEntity showEntity = bookingEntity.getShow();
            showService.updateTicketsAvailability(showEntity, -bookingEntity.getTotalSeats());
        }

    }

    @Override
    public SearchResult<ShowBookingEntity> findBookings(final BookingSearchQuery searchQuery) {
        return null;
    }

    private String generateBookingReference(final ZonedDateTime bookingAt) {
        final StringBuilder builder = new StringBuilder();
        builder.append("BKG");
        builder.append(bookingAt.getLong(ChronoField.INSTANT_SECONDS));
        return builder.toString();
    }

}
