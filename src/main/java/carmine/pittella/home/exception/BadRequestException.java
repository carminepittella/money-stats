package carmine.pittella.home.exception;

import jakarta.ws.rs.core.Response;

public class BadRequestException extends BaseHttpException {

    public BadRequestException (String exceptionId, String message) {
        super(exceptionId + "_BAD_REQUEST", message, Response.Status.BAD_REQUEST, "");
    }

    public BadRequestException (String exceptionId, String message, String customCause) {
        super(exceptionId + "_BAD_REQUEST", message, Response.Status.BAD_REQUEST, customCause);
    }
}
