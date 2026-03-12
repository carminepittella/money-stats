package carmine.pittella.home.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UtenteDto {

    private Long id;
    private String nome;
    private String cognome;
}
