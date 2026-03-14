package carmine.pittella.home.bean;

import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.service.ExcelReaderService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
public class ExcelReaderBean implements ExcelReaderService {
    
    @Override
    public List<MovimentoDto> extractMovimenti () {
        return List.of();
    }
}
