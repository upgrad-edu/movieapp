package upgrad.movieapp.service.movie.business;

import static upgrad.movieapp.service.movie.exception.BookingErrorCode.BKG_003;
import static upgrad.movieapp.service.movie.exception.BookingErrorCode.BKG_004;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import upgrad.movieapp.service.movie.entity.CouponEntity;
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

    @Autowired
    private CouponService couponService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ShowBookingEntity bookShow(final String showUuid, final String customerUuid,
                                      final Set<String> ticketNumbers, final String couponCode) throws ApplicationException {

        final UserEntity userEntity = userService.findUserByUuid(customerUuid);
        final ShowEntity showEntity = showService.findShowByUuid(showUuid);
        final int totalTickets = ticketNumbers.size();

        final ShowBookingEntity bookingEntity;
        synchronized (this) {
            final Integer availableSeats = showEntity.getAvailableSeats();
            if (availableSeats == 0 || availableSeats < totalTickets) {
                throw new ApplicationException(BookingErrorCode.BKG_005, availableSeats);
            }

            final List<String> bookedTickets = bookingDao.findBookedTickets(showUuid, ticketNumbers);
            if (CollectionUtils.isNotEmpty(bookedTickets)) {
                throw new ApplicationException(BKG_004, bookedTickets);
            }

            bookingEntity = new ShowBookingEntity();
            bookingEntity.setShow(showEntity);
            bookingEntity.setCustomer(userEntity);

            BigDecimal totalPrice = showEntity.getUnitPrice().multiply(new BigDecimal(totalTickets));
            if(StringUtils.isNotEmpty(couponCode)) {
                final CouponEntity couponEntity = couponService.findCoupon(couponCode);
                final Integer discountPercentage = couponEntity.getDiscountPercentage();
                totalPrice = totalPrice.subtract(totalPrice.multiply(new BigDecimal(discountPercentage)).divide(new BigDecimal(100)));
            }

            bookingEntity.setTotalSeats(totalTickets);
            bookingEntity.setTotalPrice(totalPrice);

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
            showService.revertTicketsAvailability(bookingEntity.getShow(), bookingEntity.getTotalSeats());
        }

    }

    @Override
    public SearchResult<ShowBookingEntity> findBookings(final BookingSearchQuery searchQuery) {
        return bookingDao.findBookings(searchQuery);
    }

    @Override
    public ShowBookingEntity findByUuid(final String customerUuid, final String bookingUuid) throws ApplicationException {
        final ShowBookingEntity booking = bookingDao.findByCustomer(customerUuid, bookingUuid);
        if (booking == null) {
            throw new EntityNotFoundException(BKG_003, bookingUuid);
        }
        return booking;
    }

    private String generateBookingReference(final ZonedDateTime bookingAt) {
        final StringBuilder builder = new StringBuilder();
        builder.append("BKG");
        builder.append(bookingAt.getLong(ChronoField.INSTANT_SECONDS));
        return builder.toString();
    }

}
