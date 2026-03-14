package carmine.pittella.home.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private String username;
    private String ruolo;
    private String token;
}
