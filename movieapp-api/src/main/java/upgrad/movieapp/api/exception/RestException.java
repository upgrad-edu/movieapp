package upgrad.movieapp.api.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.MessageFormat;

import upgrad.movieapp.service.common.exception.ErrorCode;

public class RestException extends RuntimeException {

    private final ErrorCode errorCode;
    private Throwable cause;
    private final Object[] parameters;

    public RestException(final ErrorCode errorCode, final Object... parameters) {
        super();
        this.errorCode = errorCode;
        this.parameters = parameters;
    }

    public RestException(final ErrorCode errorCode, final Throwable cause, final Object... parameters) {
        super();
        this.errorCode = errorCode;
        this.cause = cause;
        this.parameters = parameters;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format(errorCode.getDefaultMessage(), this.parameters);
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public final void printStackTrace(final PrintStream stream) {
        super.printStackTrace(stream);
    }

    @Override
    public final void printStackTrace(final PrintWriter writer) {
        super.printStackTrace(writer);
    }

}
