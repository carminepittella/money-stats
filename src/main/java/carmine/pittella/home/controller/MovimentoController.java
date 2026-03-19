package carmine.pittella.home.controller;

import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.service.MovimentoService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;
import java.util.Map;

@Slf4j
@Path("/movimenti")
@RequestScoped
@RequiredArgsConstructor
@Produces({MediaType.APPLICATION_JSON})
public class MovimentoController {

    private final MovimentoService movimentoService;

    @GET
    @Path("/find-all")
    public List<MovimentoDto> findAll () {
        return movimentoService.findAll();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadMovimenti (@RestForm("file") FileUpload fileUpload) {
        movimentoService.importExcelMovimenti(fileUpload);
        return Response.ok(Map.of("message", String.format("Dati del file: %s importati correttamente", fileUpload.fileName()))).build();
    }

}
