package carmine.pittella.home.service;

import carmine.pittella.home.model.dto.ContoDto;

import java.util.List;

public interface ContoService {

    List<ContoDto> findAll ();
    ContoDto findOrCreate (String contoStr);
}
