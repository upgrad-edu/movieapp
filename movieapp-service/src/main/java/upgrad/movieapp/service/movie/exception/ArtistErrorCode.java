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
public enum ArtistErrorCode implements ErrorCode {

    ART_001("ART-001", "Artist with identifier [{0}] does not exist"),

    ART_002("ART-002", "[{0}] is not a valid artist role. Supported roles are [{1}]"),
    ;

    private static final Map<String, ArtistErrorCode> LOOKUP = new HashMap<String, ArtistErrorCode>();

    static {
        for (final ArtistErrorCode enumeration : ArtistErrorCode.values()) {
            LOOKUP.put(enumeration.getCode(), enumeration);
        }
    }

    private final String code;

    private final String defaultMessage;

    private ArtistErrorCode(final String code, final String defaultMessage) {
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