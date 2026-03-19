package carmine.pittella.home.service;

import carmine.pittella.home.model.dto.MovimentoDto;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

public interface MovimentoService {

    List<MovimentoDto> findAll ();
    void importExcelMovimenti(FileUpload fileUpload);
}
