package upgrad.movies.api.handler;

import static org.springframework.http.HttpStatus.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import upgrad.movies.api.exception.RestException;
import upgrad.movies.api.exception.UnauthorizedException;
import upgrad.movies.api.model.ErrorResponse;
import upgrad.movies.service.common.exception.ApplicationException;
import upgrad.movies.service.common.exception.AuthenticationFailedException;
import upgrad.movies.service.common.exception.AuthorizationFailedException;
import upgrad.movies.service.common.exception.EntityNotFoundException;
import upgrad.movies.service.common.exception.GenericErrorCode;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationFailedException.class)
    public final ResponseEntity<ErrorResponse> handleAuthenticationFailedException(AuthenticationFailedException ex, WebRequest request) {
        return new ResponseEntity(errorResponse(ex), UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        return new ResponseEntity(errorResponse(ex), UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    public final ResponseEntity<ErrorResponse> handleAuthorizationFailedException(AuthorizationFailedException ex, WebRequest request) {
        return new ResponseEntity(errorResponse(ex), FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return new ResponseEntity(errorResponse(ex), NOT_FOUND);
    }

    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex, WebRequest request) {
        return new ResponseEntity(errorResponse(ex), UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(RestException.class)
    public final ResponseEntity<ErrorResponse> handleRestException(RestException ex, WebRequest request) {
        return new ResponseEntity(errorResponse(ex), UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity(errorResponse(ex), INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse errorResponse(final ApplicationException exc) {
        exc.printStackTrace();
        return new ErrorResponse().code(exc.getErrorCode().getCode()).message(exc.getMessage());
    }

    private ErrorResponse errorResponse(final RestException exc) {
        exc.printStackTrace();
        return new ErrorResponse().code(exc.getErrorCode().getCode()).message(exc.getMessage());
    }

    private ErrorResponse errorResponse(final RuntimeException exc) {
        exc.printStackTrace();

        final StringWriter stringWriter = new StringWriter();
        exc.printStackTrace(new PrintWriter(stringWriter));

        String message = exc.getMessage();
        if(message == null) {
            message = GenericErrorCode.GEN_001.getDefaultMessage();
        }
        return new ErrorResponse().code(GenericErrorCode.GEN_001.getCode()).message(message).rootCause(stringWriter.getBuffer().toString());
    }

}