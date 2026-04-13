package carmine.pittella.home.service;

import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.model.dto.request.MovimentiFilterRequestDto;
import carmine.pittella.home.model.dto.response.DashboardStatsResponseDto;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

public interface MovimentoService {

    List<MovimentoDto> findAll ();
    List<MovimentoDto> findAllFiltered (MovimentiFilterRequestDto filtri);
    void importExcelMovimenti(FileUpload fileUpload);
    DashboardStatsResponseDto getDashboardStats (MovimentiFilterRequestDto filter);
}
