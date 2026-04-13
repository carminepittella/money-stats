package carmine.pittella.home.model.dto.request;

import jakarta.ws.rs.QueryParam;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovimentiFilterRequestDto {

    @QueryParam("dataInizio")
    private LocalDate dataInizio;

    @QueryParam("dataFine")
    private LocalDate dataFine;

    @QueryParam("idCategoria")
    private Long idCategoria;

    @QueryParam("idConto")
    private Long idConto;

    @QueryParam("idHashtag")
    private Long idHashtag;
}
