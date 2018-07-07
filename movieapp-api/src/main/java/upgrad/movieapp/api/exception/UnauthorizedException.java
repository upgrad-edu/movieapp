package upgrad.movieapp.api.exception;

import upgrad.movieapp.service.common.exception.ErrorCode;

public class UnauthorizedException extends RestException {

    public UnauthorizedException(final ErrorCode errorCode, final Object... parameters){
        super(errorCode, parameters);
    }

}
