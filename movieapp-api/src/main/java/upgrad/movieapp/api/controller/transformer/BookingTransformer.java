package upgrad.movieapp.api.controller.transformer;

import static upgrad.movieapp.service.movie.exception.BookingErrorCode.BKG_002;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import upgrad.movieapp.api.exception.RestException;
import upgrad.movieapp.api.model.BookingCustomerInfoType;
import upgrad.movieapp.api.model.BookingStatusType;
import upgrad.movieapp.api.model.BookingType;
import upgrad.movieapp.api.model.BookingsSummaryResponse;
import upgrad.movieapp.api.model.CouponType;
import upgrad.movieapp.api.model.CouponsSummaryResponse;
import upgrad.movieapp.service.common.data.DateTimeProvider;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.entity.CouponEntity;
import upgrad.movieapp.service.movie.entity.ShowBookingEntity;
import upgrad.movieapp.service.movie.model.BookingSearchQuery;
import upgrad.movieapp.service.movie.model.BookingStatus;
import upgrad.movieapp.service.user.entity.UserEntity;

public final class BookingTransformer {

    private BookingTransformer() {
    }

    public static BookingSearchQuery toSearchQuery(final int page, final int limit, final String customerUuid, final String bookingRef, final String status) {

        final BookingSearchQuery searchQuery = new BookingSearchQuery(page, limit);
        if (StringUtils.isNotEmpty(customerUuid)) {
            searchQuery.customerUuid(customerUuid);
        }
        if (StringUtils.isNotEmpty(status)) {
            final EnumSet<BookingStatus> movieStatuses = EnumSet.noneOf(BookingStatus.class);
            final String[] statuses = StringUtils.split(status, ",");
            for (String splitStatus : statuses) {
                movieStatuses.add(toBookingStatus(splitStatus.toUpperCase()));
            }
            searchQuery.statuses(movieStatuses);
        }
        if (StringUtils.isNotEmpty(bookingRef)) {
            searchQuery.bookingReference(bookingRef);
        }
        return searchQuery;
    }

    public static BookingsSummaryResponse toBookingsSummaryResponse(final int page, final int limit, final SearchResult<ShowBookingEntity> searchResult) {

        final BookingsSummaryResponse bookingsSummaryResponse = new BookingsSummaryResponse().totalCount(searchResult.getTotalCount()).page(page).limit(limit);
        for (ShowBookingEntity bookingEntity : searchResult.getPayload()) {
            bookingsSummaryResponse.addBookingsItem(toBookingType(bookingEntity));
        }

        return bookingsSummaryResponse;
    }

    public static BookingType toBookingType(final ShowBookingEntity bookingEntity) {
        final BookingType bookingResponse = new BookingType()
                .id(UUID.fromString(bookingEntity.getUuid()))
                .referenceNumber(bookingEntity.getBookingReference())
                .couponCode(bookingEntity.getCouponCode())
                .movieTitle(bookingEntity.getShow().getMovie().getTitle())
                .showTiming(DateTimeProvider.formatDateTime(bookingEntity.getShow().getStartTime()))
                .language(bookingEntity.getShow().getLanguage())
                .totalTickets(bookingEntity.getTotalSeats())
                .totalPrice(bookingEntity.getTotalPrice())
                .theatre(TheatreTransformer.toTheatreSummary(bookingEntity.getShow().getTheatre()))
                .bookedBy(toBookingCustomerInfo(bookingEntity.getCustomer()))
                .tickets(tickets(bookingEntity))
                .status(BookingStatusType.fromValue(bookingEntity.getStatus()));

        return bookingResponse;
    }

    public static CouponsSummaryResponse toCouponsSummaryResponse(final CouponEntity couponEntity) {

        final CouponsSummaryResponse bookingsSummaryResponse = new CouponsSummaryResponse().code(couponEntity.getCode());
        bookingsSummaryResponse.description(couponEntity.getDescription());
        bookingsSummaryResponse.value(couponEntity.getDiscountPercentage());

        return bookingsSummaryResponse;
    }

    private static BookingCustomerInfoType toBookingCustomerInfo(final UserEntity customerEntity) {
        return new BookingCustomerInfoType().firstName(customerEntity.getFirstName())
                .lastName(customerEntity.getLastName())
                .emailAddress(customerEntity.getEmail())
                .mobileNumber(customerEntity.getMobilePhone());
    }

    private static List<String> tickets(final ShowBookingEntity bookingEntity) {
        return bookingEntity.getBookingTickets().stream().map(bookingTicketEntity -> {
            return bookingTicketEntity.getTicketNumber();
        }).collect(Collectors.toList());
    }

    private static BookingStatus toBookingStatus(final String status) {
        try {
            return BookingStatus.valueOf(status);
        } catch (IllegalArgumentException exc) {
            throw new RestException(BKG_002, status, StringUtils.join(BookingStatus.values(), ","));
        }
    }

}
