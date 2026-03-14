package carmine.pittella.home.bean;

import carmine.pittella.home.mapper.MovimentoMapper;
import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.repository.MovimentoRepository;
import carmine.pittella.home.service.ExcelReaderService;
import carmine.pittella.home.service.MovimentoService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    public void importExcelMovimenti () {
        //TODO: passare il file excel
        // facciamo finta che abbiamo estratto la lista dei movimenti
        // passare il file excel
        List<MovimentoDto> movimentiDtoList = excelReaderService.extractMovimenti();

        if (movimentiDtoList == null || movimentiDtoList.isEmpty()) {
            //TODO: lanciare eccezione custom
            String errorMsg = String.format("Il caricamento del file %s ha restituito una lista vuota", "NOME_FILE");
            log.error(errorMsg);
            return; // da rimuovere
        }

        movimentiDtoList.forEach(movimento ->
                movimentoRepository.save(movimentoMapper.toEntity(movimento)));
    }
}
