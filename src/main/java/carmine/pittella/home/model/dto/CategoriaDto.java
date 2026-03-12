package carmine.pittella.home.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "categoria")
public class CategoriaDto {

    private Long id;
    private String categoria;
}
