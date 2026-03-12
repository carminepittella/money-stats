package carmine.pittella.home.controller;

import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.service.MovimentoService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Path("/movimenti")
@RequestScoped
@RequiredArgsConstructor
public class MovimentoController {

    private final MovimentoService movimentoService;

    @GET
    @Path("/find-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<MovimentoDto> findAll () {
        return movimentoService.findAll();
    }
}
