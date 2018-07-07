/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthorizationFailedException.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.common.exception;

/**
 * User defined exception for unauthorized access.
 */
public class AuthorizationFailedException extends ApplicationException {

    private static final long serialVersionUID = 6409417559920703198L;

    public AuthorizationFailedException(ErrorCode errorCode, Object... parameters) {
        super(errorCode, parameters);
    }

}