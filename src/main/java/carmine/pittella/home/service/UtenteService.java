package carmine.pittella.home.service;

import carmine.pittella.home.model.dto.UtenteDto;
import carmine.pittella.home.model.dto.request.UtenteCreateRequestDto;

import java.util.List;

public interface UtenteService {

    UtenteDto createUtente (UtenteCreateRequestDto newUtente);

    UtenteDto findByUsername (String username);
    List<UtenteDto> findAll ();
}
