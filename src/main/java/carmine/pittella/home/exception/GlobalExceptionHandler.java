package carmine.pittella.home.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<BaseHttpException> {

    @Override
    public Response toResponse (BaseHttpException e) {
        log.error("Gestione eccezione: {} - {}", e.getExceptionId(), e.getMessage());

        ErrorBody errorBody = ErrorBody.builder()
                .exceptionId(e.getExceptionId())
                .message(e.getMessage())
                .customCause(e.getCustomCause())
                .build();

        return Response.status(e.getHttpStatus())
                .entity(errorBody)
                .build();
    }
}
