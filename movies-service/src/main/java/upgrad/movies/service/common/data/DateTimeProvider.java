/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: DateTimeProvider.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movies.service.common.data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * Provider for date time objects.
 */
public final class DateTimeProvider {

    private static final DateTimeProvider INSTANCE = new DateTimeProvider();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private DateTimeProvider() {
        // prohibit instantiation
    }

    public static ZonedDateTime currentSystemTime() {
        return ZonedDateTime.now();
    }

    public static ZonedDateTime currentProgramTime() {
        return ZonedDateTime.now();
    }

    public static ZonedDateTime fromTime(final long timeInMilliSecs) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilliSecs);
        return ZonedDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), //
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), 0, ZoneId.of("America/Indiana/Indianapolis"));
    }

    public static ZonedDateTime parse(final String dateTimeString) {
        return ZonedDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
    }

    public static String format(final ZonedDateTime zonedDateTime) {
        return zonedDateTime.format(DATE_TIME_FORMATTER);
    }

}