/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: AuthenticationFailedException.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.common.exception;

/**
 * User defined exception for unauthenticated access.
 */
public class AuthenticationFailedException extends ApplicationException {

    private static final long serialVersionUID = 7660768556081121813L;

    public AuthenticationFailedException(ErrorCode errorCode, Object... parameters) {
        super(errorCode, parameters);
    }

}