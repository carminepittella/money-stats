package carmine.pittella.home.controller;

import carmine.pittella.home.model.dto.request.MovimentiFilterRequestDto;
import carmine.pittella.home.model.dto.response.StatisticsResponseDto;
import carmine.pittella.home.service.StatisticService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/statistics")
@Authenticated
@RequestScoped
@RequiredArgsConstructor
@Produces({MediaType.APPLICATION_JSON})
public class StatisticsController {

    private final StatisticService statisticService;

    @GET
    @Path("")
    public StatisticsResponseDto getStatistics (@BeanParam MovimentiFilterRequestDto filter) {
        return statisticService.getStatistics(filter);
    }
}
