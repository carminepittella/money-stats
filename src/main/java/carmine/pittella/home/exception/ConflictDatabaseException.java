package carmine.pittella.home.exception;

import jakarta.ws.rs.core.Response;

public class ConflictDatabaseException extends BaseHttpException {

    public ConflictDatabaseException (String exceptionId, String message) {
        super(exceptionId, message, Response.Status.CONFLICT, "");
    }

    public ConflictDatabaseException (String exceptionId, String message, String customMessage) {
        super(exceptionId, message, Response.Status.CONFLICT, customMessage);
    }
}
