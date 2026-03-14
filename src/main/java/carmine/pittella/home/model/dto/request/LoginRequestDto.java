package carmine.pittella.home.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotEmpty(message = "USERNAME obbligatorio")
    private String username;

    @NotEmpty(message = "PASSWORD obbligatoria")
    private String password;
}
