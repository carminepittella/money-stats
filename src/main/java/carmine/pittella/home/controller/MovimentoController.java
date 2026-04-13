package carmine.pittella.home.controller;

import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.model.dto.request.MovimentiFilterRequestDto;
import carmine.pittella.home.model.dto.response.DashboardStatsResponseDto;
import carmine.pittella.home.service.MovimentoService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Path("/movimenti")
@Authenticated
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

    @GET
    @Path("/filtered")
    public List<MovimentoDto> findAllFiltered (@BeanParam MovimentiFilterRequestDto filter) {
        return movimentoService.findAllFiltered(filter);
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadMovimenti (@RestForm("file") FileUpload fileUpload) {
        movimentoService.importExcelMovimenti(fileUpload);
        return Response.ok(Map.of("message", String.format("Dati del file: %s importati correttamente", fileUpload.fileName()))).build();
    }

    @GET
    @Path("/dashboard-stats")
    public DashboardStatsResponseDto dashboardStats (@BeanParam MovimentiFilterRequestDto filter) {
        return movimentoService.getDashboardStats(filter);
    }


}
