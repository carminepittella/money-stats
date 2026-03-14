package carmine.pittella.home.controller;

import carmine.pittella.home.model.dto.UtenteDto;
import carmine.pittella.home.model.dto.request.UtenteCreateRequestDto;
import carmine.pittella.home.service.UtenteService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Path("/utenti")
@RequestScoped
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;

    //TODO: aggiungere autorizzazione
    @GET
    @Path("/find-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<UtenteDto> findAll () {
        return utenteService.findAll();
    }

    //TODO: aggiungere autorizzazione
    @POST
    @Path("/create")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public UtenteDto create (@NonNull @Valid UtenteCreateRequestDto newUtente) {
        return utenteService.createUtente(newUtente);
    }
}
