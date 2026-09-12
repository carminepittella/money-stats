package carmine.pittella.home.model.dto;

import carmine.pittella.home.model.enums.TipologiaEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Comparator;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MovimentoDto {

    public Long id;
    public LocalDateTime data;
    public TipologiaEnum tipologia;
    public String titolo;
    public Double importo;
    public String commento;
    public ContoDto conto;
    public CategoriaDto categoria;
    public HashtagDto hashtag;
    public UtenteDto ricevente;

    public static final Comparator<MovimentoDto> COMPARATOR_DATA_ASC =
            Comparator.comparing(MovimentoDto::getData).reversed();

}
