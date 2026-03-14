package carmine.pittella.home.service;

import carmine.pittella.home.model.dto.MovimentoDto;

import java.util.List;

public interface ExcelReaderService {

    List<MovimentoDto> extractMovimenti();
}
