/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: ErrorResponseBuilder.java
 * Date: Oct 13, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package upgrad.movieapp.api.controller.ext;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import upgrad.movieapp.service.common.exception.ApplicationException;

/**
 * Error response builder.
 */
public class ErrorResponseBuilder<E extends ApplicationException> {

    private final HttpStatus status;
    private ErrorResponse errorResponse;

    public ErrorResponseBuilder(final HttpStatus status) {
        this.status = status;
    }

    public ErrorResponseBuilder<E> payload(E exc) {
        this.errorResponse = new ErrorResponse(exc.getErrorCode().getCode(), exc.getMessage());
        return this;
    }

    public ResponseEntity<ErrorResponse> build() {
        return new ResponseEntity<ErrorResponse>(errorResponse, null, status);
    }

}