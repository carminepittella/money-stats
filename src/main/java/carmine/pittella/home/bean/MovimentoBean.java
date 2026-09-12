package carmine.pittella.home.bean;

import carmine.pittella.home.exception.BadRequestException;
import carmine.pittella.home.mapper.MovimentoMapper;
import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.model.dto.request.MovimentiFilterRequestDto;
import carmine.pittella.home.model.dto.response.DashboardStatsResponseDto;
import carmine.pittella.home.repository.MovimentoRepository;
import carmine.pittella.home.service.ExcelReaderService;
import carmine.pittella.home.service.MovimentoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class MovimentoBean implements MovimentoService {

    private final MovimentoMapper movimentoMapper;
    private final MovimentoRepository movimentoRepository;
    private final ExcelReaderService excelReaderService;

    @Override
    public List<MovimentoDto> findAll () {
        return movimentoMapper.toDtoList(movimentoRepository.listAll().stream().toList());
    }

    @Override
    public List<MovimentoDto> findAllFiltered (MovimentiFilterRequestDto filtri) {
        if (filtri == null) {
            return List.of();
        }
        return movimentoMapper.toDtoList(movimentoRepository.findAllFiltered(filtri));
    }


    @Override
    @Transactional
    public void importExcelMovimenti (FileUpload fileUpload) {
        if (fileUpload == null) {
            String errorMsg = "il file importato è NULL";
            log.error(errorMsg);
            throw new BadRequestException("IMPORT_MOVIMENTI", errorMsg);
        }

        // estraggo i movimenti dall'excel
        List<MovimentoDto> movimentiDtoList = excelReaderService.extractMovimenti(fileUpload);

        if (movimentiDtoList == null || movimentiDtoList.isEmpty()) {
            String errorMsg = String.format("Il caricamento del file %s ha restituito una lista vuota", fileUpload.fileName());
            log.error(errorMsg);
            throw new BadRequestException("IMPORT_MOVIMENTI", errorMsg);
        }

        movimentoRepository.saveAll(movimentoMapper.toEntityList(movimentiDtoList));
    }

    @Override
    public DashboardStatsResponseDto getDashboardStats (MovimentiFilterRequestDto filter) {
        LocalDate dataInizio = filter.getDataInizio() != null ? filter.getDataInizio() : LocalDate.of(1900, 1, 1);
        LocalDate dataFine = filter.getDataFine() != null ? filter.getDataFine() : LocalDate.of(2100, 1, 1);

        return movimentoRepository.getDashboardStats(dataInizio, dataFine);
    }
}
