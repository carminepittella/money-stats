package carmine.pittella.home.exception;

import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public abstract class BaseHttpException extends RuntimeException {

    private final String exceptionId;
    private final Response.Status httpStatus;
    private final String customCause;

    public BaseHttpException (String exceptionId, String message, Response.Status status, String customCause) {
        super(message);
        this.exceptionId = exceptionId;
        this.httpStatus = status;
        this.customCause = customCause;
    }
}
