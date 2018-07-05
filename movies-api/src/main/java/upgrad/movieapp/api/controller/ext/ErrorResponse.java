/* 
 * Copyright 2017-2018, Redux Software. 
 * 
 * File: ErrorResponse.java
 * Date: Oct 10, 2017
 * Author: P7107311
 * URL: www.redux.com
*/
package upgrad.movieapp.api.controller.ext;

/**
 * Custom object for sending error information in response.
 */
public class ErrorResponse {

    private final String code;
    private final String message;

    public ErrorResponse(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}