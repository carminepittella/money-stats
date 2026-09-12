package carmine.pittella.home.service;

import carmine.pittella.home.model.dto.MovimentoDto;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

public interface ExcelReaderService {

    List<MovimentoDto> extractMovimenti(FileUpload fileUpload);
}
