package carmine.pittella.home.bean;

import carmine.pittella.home.mapper.CategoriaMapper;
import carmine.pittella.home.model.dto.CategoriaDto;
import carmine.pittella.home.repository.CategoriaRepository;
import carmine.pittella.home.service.CategoriaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class CategoriaBean implements CategoriaService {

    private final CategoriaMapper categoriaMapper;
    private final CategoriaRepository categoriaRepository;

    @Override
    public List<CategoriaDto> findAll () {
        return categoriaMapper.toDtoList(categoriaRepository.findAllSorted());
    }

    @Override
    @Transactional
    public CategoriaDto findOrCreate (String categoria) {
        return categoriaMapper.toDto(categoriaRepository.findOrCreate(categoria));
    }


}
