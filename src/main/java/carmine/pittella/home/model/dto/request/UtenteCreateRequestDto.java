package carmine.pittella.home.model.dto.request;

import carmine.pittella.home.model.enums.RuoloUtenteEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UtenteCreateRequestDto {

    @NotBlank(message = "NOME obbligatorio")
    private String nome;

    @NotBlank(message = "COGNOME obbligatorio")
    private String cognome;

    @NotBlank(message = "USERNAME obbligatorio")
    private String username;

    @NotBlank(message = "PASSWORD obbligatoria")
    private String password;

    @NotBlank(message = "CONFERMA PASSWORD obbligatoria")
    private String confermaPassword;

    private RuoloUtenteEnum ruolo = RuoloUtenteEnum.GENERIC;
}
