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
 * Error code for SHOWS module.
 */
public enum ShowErrorCode implements ErrorCode {

    SHW_001("SHW-001", "Show with identifier [{0}] does not exist"),

    SHW_002("SHW-002", "[{0}] is not a valid status. Supported statuses are [{1}]"),

    SHW_003("SHW-003", "For the movie [{0}] in theatre [{1}], show timing [{2}] on [{3}] conflicts with the other show timings"),

    SHW_004("SHW-004", "[{0}] is not a valid language. Supported languages are [{1}]"),;

    private static final Map<String, ShowErrorCode> LOOKUP = new HashMap<String, ShowErrorCode>();

    static {
        for (final ShowErrorCode enumeration : ShowErrorCode.values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final String code;

    private final String defaultMessage;

    private ShowErrorCode(final String code, final String defaultMessage) {
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