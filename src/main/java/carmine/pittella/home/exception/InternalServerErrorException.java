package carmine.pittella.home.exception;

import jakarta.ws.rs.core.Response;

public class InternalServerErrorException extends BaseHttpException {

    public InternalServerErrorException (String exceptionId, String message) {
        super(exceptionId, message, Response.Status.INTERNAL_SERVER_ERROR, "");
    }

    public InternalServerErrorException (String exceptionId, String message, String customCause) {
        super(exceptionId, message, Response.Status.INTERNAL_SERVER_ERROR, customCause);
    }
}
