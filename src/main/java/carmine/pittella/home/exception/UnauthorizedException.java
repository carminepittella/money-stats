package carmine.pittella.home.exception;

import jakarta.ws.rs.core.Response;

public class UnauthorizedException extends BaseHttpException {

    public UnauthorizedException (String exceptionId, String message) {
        super(exceptionId, message, Response.Status.UNAUTHORIZED, "");
    }

    public UnauthorizedException (String exceptionId, String message, String customCause) {
        super(exceptionId, message, Response.Status.UNAUTHORIZED, customCause);
    }
}
