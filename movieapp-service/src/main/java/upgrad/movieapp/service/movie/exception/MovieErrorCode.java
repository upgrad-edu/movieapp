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
public enum MovieErrorCode implements ErrorCode {

    MVI_001("MVI-001", "Movie with identifier [{0}] does not exist"),

    MVI_002("MVI-002", "[{0}] is not a valid movie status. Supported statuses are [{1}]"),

    MVI_003("MVI-003", "Release date must be in future"),

    MVI_004("MVI-004", "Movie with identifier [{0}] cannot be updated as it is in DELETED status"),

    MVI_005("MVI-005", "Genre with identifier [{0}] does not exist"),

    MVI_006("MVI-006", "Not a valid movie sort field. Supported fields are [{0}]")
    ;

    private static final Map<String, MovieErrorCode> LOOKUP = new HashMap<String, MovieErrorCode>();

    static {
        for (final MovieErrorCode enumeration : MovieErrorCode.values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final String code;

    private final String defaultMessage;

    private MovieErrorCode(final String code, final String defaultMessage) {
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