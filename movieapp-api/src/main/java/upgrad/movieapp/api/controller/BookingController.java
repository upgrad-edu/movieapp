package upgrad.movieapp.api.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static upgrad.movieapp.api.data.ResourceConstants.AUTHORIZED_USER_UUID;
import static upgrad.movieapp.api.data.ResourceConstants.BASE_URL;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import upgrad.movieapp.api.controller.ext.ResponseBuilder;
import upgrad.movieapp.api.controller.transformer.BookingTransformer;
import upgrad.movieapp.api.model.BookingRequest;
import upgrad.movieapp.api.model.BookingType;
import upgrad.movieapp.api.model.BookingsSummaryResponse;
import upgrad.movieapp.service.common.exception.ApplicationException;
import upgrad.movieapp.service.common.model.SearchResult;
import upgrad.movieapp.service.movie.business.BookingService;
import upgrad.movieapp.service.movie.business.CouponService;
import upgrad.movieapp.service.movie.entity.CouponEntity;
import upgrad.movieapp.service.movie.entity.ShowBookingEntity;
import upgrad.movieapp.service.movie.model.BookingSearchQuery;

@RestController
@RequestMapping(BASE_URL)
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method = GET, path = "/coupons/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BookingsSummaryResponse> getCoupons(@RequestHeader("authorization") String accessToken,
                                                              @PathVariable("id") final String couponId) throws ApplicationException {
        return ResponseBuilder.ok().payload(BookingTransformer.toCouponsSummaryResponse(couponService.findCoupon(couponId))).build();
    }

    @RequestMapping(method = GET, path = "/bookings", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BookingsSummaryResponse> getBookings(@RequestHeader("authorization") String accessToken,
                                                               @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                               @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                                               @RequestParam(value = "booking_ref", required = false) String bookingRef,
                                                               @RequestParam(value = "status", required = false) String status)
            throws ApplicationException {

        final BookingSearchQuery searchQuery = BookingTransformer.toSearchQuery(page, limit, (String)request.getAttribute(AUTHORIZED_USER_UUID), bookingRef, status);
        final SearchResult<ShowBookingEntity> bookings = bookingService.findBookings(searchQuery);
        return ResponseBuilder.ok().payload(BookingTransformer.toBookingsSummaryResponse(page, limit, bookings)).build();
    }

    @RequestMapping(method = GET, path = "/bookings/{booking_id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BookingType> getBooking(@RequestAttribute(AUTHORIZED_USER_UUID) final String customerUuid,
                                                  @PathVariable("booking_id") final String bookingUuid)
            throws ApplicationException {

        ShowBookingEntity booking = bookingService.findByUuid(customerUuid, bookingUuid);
        return ResponseBuilder.ok().payload(BookingTransformer.toBookingType(booking)).build();
    }

    @RequestMapping(method = POST, path = "/bookings", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BookingType> book(@RequestHeader("authorization") String accessToken,
                                            @RequestAttribute(AUTHORIZED_USER_UUID) final String customerUuid,
                                            @RequestBody final BookingRequest bookingRequest) throws ApplicationException {

        final ShowBookingEntity bookingEntity = bookingService.bookShow(bookingRequest.getShowId().toString(), customerUuid, new HashSet<>(bookingRequest.getTickets()), bookingRequest.getCouponCode());
        return ResponseBuilder.created().payload(BookingTransformer.toBookingType(bookingEntity)).build();
    }

    @RequestMapping(method = DELETE, path = "/bookings/{booking_id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity cancelBooking(@RequestHeader("authorization") String accessToken,
                                        @PathVariable("booking_id") final String bookingUuid,
                                        @RequestAttribute(AUTHORIZED_USER_UUID) final String customerUuid) throws ApplicationException {

        bookingService.cancelBooking(customerUuid, bookingUuid);
        return ResponseBuilder.ok().build();
    }

}
