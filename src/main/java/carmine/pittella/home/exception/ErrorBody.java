package carmine.pittella.home.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorBody {

    private String exceptionId;
    private String message;
    private String customCause;
}
