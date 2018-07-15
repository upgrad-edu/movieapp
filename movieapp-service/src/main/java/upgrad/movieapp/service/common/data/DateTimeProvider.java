/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: DateTimeProvider.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.common.data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Provider for date time objects.
 */
public final class DateTimeProvider {

    public static final DateTimeFormatter DEFAULT_FORMAT = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    public static final DateTimeFormatter SIMPLE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private DateTimeProvider() {
        // prohibit instantiation
    }

    public static ZonedDateTime currentSystemTime() {
        return ZonedDateTime.now();
    }

    public static ZonedDateTime currentProgramTime() {
        return ZonedDateTime.now();
    }

    public static ZonedDateTime parse(final String dateTimeString) {
        return ZonedDateTime.parse(dateTimeString, DEFAULT_FORMAT);
    }

    public static ZonedDateTime parse(final String dateTimeString, final DateTimeFormatter formatter) {
        final LocalDate simpleDate = LocalDate.parse(dateTimeString, formatter);
        return simpleDate.atStartOfDay(ZoneId.systemDefault());
    }

    public static String format(final ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(DEFAULT_FORMAT);
    }

    public static String format(final ZonedDateTime zonedDateTime, final DateTimeFormatter formatter) {
        return zonedDateTime.format(formatter);
    }

}