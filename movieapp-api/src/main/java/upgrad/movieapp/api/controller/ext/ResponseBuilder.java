/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: ResponseBuilder.java
 * Date: Oct 11, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package upgrad.movieapp.api.controller.ext;

import static upgrad.movieapp.api.data.ResourceConstants.HEADER_ACCESS_TOKEN;
import static upgrad.movieapp.api.data.ResourceConstants.HEADER_LOCATION;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Response builder.
 */
public class ResponseBuilder<T> {

    private final HttpStatus status;
    private final HttpHeaders headers = new HttpHeaders();
    private T payload;

    private ResponseBuilder(final HttpStatus status) {
        this.status = status;
    }

    public static <T> ResponseBuilder ok() {
        return new ResponseBuilder<T>(HttpStatus.OK);
    }

    public static <T> ResponseBuilder created() {
        return new ResponseBuilder<T>(HttpStatus.CREATED);
    }

    public ResponseBuilder<T> payload(T payload) {
        this.payload = payload;
        return this;
    }

    public ResponseBuilder<T> accessToken(final String value) {
        this.headers.add(HEADER_ACCESS_TOKEN, value);
        return this;
    }

    public ResponseBuilder<T> location(final String value) {
        this.headers.add(HEADER_LOCATION, value);
        return this;
    }

    public ResponseEntity<T> build() {
        return new ResponseEntity<T>(payload, headers, status);
    }

}