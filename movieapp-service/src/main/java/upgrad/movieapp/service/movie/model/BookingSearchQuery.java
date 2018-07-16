package upgrad.movieapp.service.movie.model;

import java.util.EnumSet;

import upgrad.movieapp.service.common.model.SearchQuery;

public class BookingSearchQuery extends SearchQuery {

    private String customerUuid;
    private String bookingReference;
    private EnumSet<BookingStatus> statuses;

    public BookingSearchQuery(final int page, final int limit) {
        super(page, limit);
    }

    public String getCustomerUuid() {
        return customerUuid;
    }

    public BookingSearchQuery customerUuid(String customerUuid) {
        this.customerUuid = customerUuid;
        return this;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public BookingSearchQuery bookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
        return this;
    }

    public EnumSet<BookingStatus> getStatuses() {
        return statuses;
    }

    public BookingSearchQuery statuses(EnumSet<BookingStatus> statuses) {
        this.statuses = statuses;
        return this;
    }

}
