package carmine.pittella.home.model.dto;

import carmine.pittella.home.model.enums.RuoloUtenteEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"username"})
public class UtenteDto {

    private Long id;
    private String nome;
    private String cognome;
    private String username;
    private RuoloUtenteEnum ruolo;

    @JsonIgnore
    private String password;
}
