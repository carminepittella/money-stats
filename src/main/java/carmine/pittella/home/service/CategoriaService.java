package carmine.pittella.home.service;

import carmine.pittella.home.model.dto.CategoriaDto;

import java.util.List;

public interface CategoriaService {

    List<CategoriaDto> findAll ();
    CategoriaDto findOrCreate (String c);
}
