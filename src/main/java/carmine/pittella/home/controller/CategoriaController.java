package carmine.pittella.home.controller;

import carmine.pittella.home.model.dto.CategoriaDto;
import carmine.pittella.home.service.CategoriaService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@Path("/categorie")
@Authenticated
@RequestScoped
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GET
    @Path("/find-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<CategoriaDto> findAll () {
        return categoriaService.findAll();
    }
}
