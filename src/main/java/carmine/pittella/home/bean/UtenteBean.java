package carmine.pittella.home.bean;

import carmine.pittella.home.mapper.UtenteMapper;
import carmine.pittella.home.model.dto.UtenteDto;
import carmine.pittella.home.repository.UtenteRepository;
import carmine.pittella.home.service.UtenteService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class UtenteBean implements UtenteService {

    private final UtenteMapper utenteMapper;
    private final UtenteRepository utenteRepository;

    @Override
    public List<UtenteDto> findAll () {
        return utenteMapper.toDtoList(utenteRepository.findAll().stream().toList());
    }
}
