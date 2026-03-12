package carmine.pittella.home.bean;

import carmine.pittella.home.mapper.ContoMapper;
import carmine.pittella.home.model.dto.ContoDto;
import carmine.pittella.home.repository.ContoRepository;
import carmine.pittella.home.service.ContoService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class ContoBean implements ContoService {

    private final ContoMapper contoMapper;
    private final ContoRepository contoRepository;


    @Override
    public List<ContoDto> findAll () {
        return contoMapper.toDtoList(contoRepository.findAll().stream().toList());
    }
}
