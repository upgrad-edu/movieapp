/*
 * Copyright 2018-2019, https://beingtechie.io.
 *
 * File: EntityNotFoundException.java
 * Date: May 5, 2018
 * Author: Thribhuvan Krishnamurthy
 */
package upgrad.movieapp.service.common.exception;

/**
 * User defined exception for all services that does a lookup of entity.
 */
public class EntityNotFoundException extends ApplicationException {

    private static final long serialVersionUID = 2848416716183340588L;

    public EntityNotFoundException(ErrorCode errorCode, Object... parameters) {
        super(errorCode, parameters);
    }

}