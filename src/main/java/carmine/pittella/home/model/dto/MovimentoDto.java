package carmine.pittella.home.model.dto;

import carmine.pittella.home.model.enums.TipologiaEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    public BigDecimal importo;
    public String commento;
    public ContoDto conto;
    public CategoriaDto categoria;
    public HashtagDto hashtag;
    public UtenteDto ricevente;
}
