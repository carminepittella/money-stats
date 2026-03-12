package carmine.pittella.home.controller;

import carmine.pittella.home.model.dto.UtenteDto;
import carmine.pittella.home.service.UtenteService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Path("/utenti")
@RequestScoped
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;

    @GET
    @Path("/find-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<UtenteDto> findAll () {
        return utenteService.findAll();
    }
}
