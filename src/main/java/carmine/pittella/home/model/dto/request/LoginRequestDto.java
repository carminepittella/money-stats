package carmine.pittella.home.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDto {

    @NotEmpty(message = "USERNAME obbligatorio")
    private String username;

    @NotEmpty(message = "PASSWORD obbligatoria")
    private String password;
}
