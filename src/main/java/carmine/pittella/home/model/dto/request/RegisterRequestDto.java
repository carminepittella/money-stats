package carmine.pittella.home.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterRequestDto {

    @NotEmpty(message = "NOME obbligatorio")
    private String nome;

    @NotEmpty(message = "COGNOME obbligatorio")
    private String cognome;

    @NotEmpty(message = "USERNAME obbligatorio")
    private String username;

    @NotEmpty(message = "PASSWORD obbligatoria")
    private String password;

    @NotEmpty(message = "CONFERMA PASSWORD obbligatoria")
    private String confermaPassword;
}
