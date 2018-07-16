package upgrad.movieapp.api.controller.transformer;

import java.util.List;
import java.util.stream.Collectors;

import upgrad.movieapp.api.model.BookingCustomerInfoType;
import upgrad.movieapp.api.model.BookingStatusType;
import upgrad.movieapp.api.model.BookingType;
import upgrad.movieapp.service.common.data.DateTimeProvider;
import upgrad.movieapp.service.movie.entity.ShowBookingEntity;
import upgrad.movieapp.service.user.entity.UserEntity;

public final class BookingTransformer {

    private BookingTransformer() {
    }

    public static BookingType toResponse(final ShowBookingEntity bookingEntity) {
        final BookingType bookingResponse = new BookingType()
                .referenceNumber(bookingEntity.getBookingReference())
                .movieTitle(bookingEntity.getShow().getMovie().getTitle())
                .showTiming(DateTimeProvider.formatDateTime(bookingEntity.getShow().getStartTime()))
                .language(bookingEntity.getShow().getLanguage())
                .totalTickets(bookingEntity.getTotalSeats())
                .totalPrice(bookingEntity.getTotalPrice())
                .theatre(TheatreTransformer.toTheatreSummary(bookingEntity.getShow().getTheatre()))
                .customer(toBookingCustomerInfo(bookingEntity.getCustomer()))
                .tickets(tickets(bookingEntity))
                .status(BookingStatusType.fromValue(bookingEntity.getStatus()));

        return bookingResponse;
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

}
