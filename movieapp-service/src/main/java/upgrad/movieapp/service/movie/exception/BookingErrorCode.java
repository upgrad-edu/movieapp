/*
 * Copyright 2018-2019, https://beingtechie.io
 *
 * File: UserErrorCode.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.movie.exception;

import java.util.HashMap;
import java.util.Map;

import upgrad.movieapp.service.common.exception.ErrorCode;

/**
 * Error code for BOOKING module.
 */
public enum BookingErrorCode implements ErrorCode {

    BKG_001("BKG-001", "Booking with reference number [{0}] does not exist"),

    BKG_002("BKG-002", "[{0}] is not a valid booking status. Supported statuses are [{1}]"),

    BKG_003("BKG-003", "Booking with identifier [{0}] does not exist"),

    BKG_004("BKG-004", "Tickets {0} already booked"),

    BKG_005("BKG-005", "Shortage of seats availability. Available number of seats [{0}]"),

    BKG_006("BKG-006", "Invalid coupon code [{0}]"),
    ;

    private static final Map<String, BookingErrorCode> LOOKUP = new HashMap<String, BookingErrorCode>();

    static {
        for (final BookingErrorCode enumeration : BookingErrorCode.values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final String code;

    private final String defaultMessage;

    private BookingErrorCode(final String code, final String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDefaultMessage() {
        return defaultMessage;
    }

}