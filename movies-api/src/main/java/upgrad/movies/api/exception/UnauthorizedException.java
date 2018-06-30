package upgrad.movies.api.exception;

import upgrad.movies.service.common.exception.ErrorCode;

public class UnauthorizedException extends RestException {

    public UnauthorizedException(final ErrorCode errorCode, final Object... parameters){
        super(errorCode, parameters);
    }

}
