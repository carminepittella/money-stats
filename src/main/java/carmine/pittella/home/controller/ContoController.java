package carmine.pittella.home.controller;

import carmine.pittella.home.model.dto.ContoDto;
import carmine.pittella.home.service.ContoService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Path("/conti")
@RequestScoped
@RequiredArgsConstructor
public class ContoController {

    private final ContoService contoService;

    @GET
    @Path("/find-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ContoDto> findAll () {
        return contoService.findAll();
    }
}
