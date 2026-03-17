package carmine.pittella.home.service;

import carmine.pittella.home.model.dto.UtenteDto;

import java.util.List;

public interface UtenteService {

    void createUtente (UtenteDto newUtente);

    UtenteDto findByUsername (String username);
    List<UtenteDto> findAll ();
}
