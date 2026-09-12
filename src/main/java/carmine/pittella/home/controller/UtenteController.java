package carmine.pittella.home.controller;

import carmine.pittella.home.model.dto.UtenteDto;
import carmine.pittella.home.service.UtenteService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Slf4j
@Path("/utenti")
@RequestScoped
@RequiredArgsConstructor
@Produces({MediaType.APPLICATION_JSON})
public class UtenteController {

    private final JsonWebToken token;
    private final UtenteService utenteService;

    @GET
    @Path("/find-all")
    @Authenticated
    public List<UtenteDto> findAll () {
        System.out.println(token);
        return utenteService.findAll();
    }
}
