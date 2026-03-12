package carmine.pittella.home.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "descrizione")
public class CategoriaDto {

    private Long id;
    private String descrizione;
}
