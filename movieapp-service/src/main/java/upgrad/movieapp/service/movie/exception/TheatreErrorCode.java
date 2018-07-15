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
 * Error code for MOVIE module.
 */
public enum TheatreErrorCode implements ErrorCode {

    THR_001("THR-001", "Theatre with identifier [{0}] does not exist"),

    THR_002("THR-002", "City with code [{0}] does not exist"),

    THR_003("THR-003", "[{0}] is not a valid status. Supported statuses are [{1}]"),;

    private static final Map<String, TheatreErrorCode> LOOKUP = new HashMap<String, TheatreErrorCode>();

    static {
        for (final TheatreErrorCode enumeration : TheatreErrorCode.values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final String code;

    private final String defaultMessage;

    private TheatreErrorCode(final String code, final String defaultMessage) {
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