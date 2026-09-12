package carmine.pittella.home.exception;

import jakarta.ws.rs.core.Response;

public class EntityNotFoundException extends BaseHttpException {

    public EntityNotFoundException (String entityName, String message) {
        super(entityName + "_NOT_FOUND", message, Response.Status.NOT_FOUND, "");
    }

    public EntityNotFoundException (String entityName, String message, String customCause) {
        super(entityName + "_NOT_FOUND", message, Response.Status.NOT_FOUND, customCause);
    }
}
