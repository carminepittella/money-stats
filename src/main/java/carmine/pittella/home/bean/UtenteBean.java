package carmine.pittella.home.bean;

import carmine.pittella.home.exception.InternalServerErrorException;
import carmine.pittella.home.mapper.UtenteMapper;
import carmine.pittella.home.model.dto.UtenteDto;
import carmine.pittella.home.repository.UtenteRepository;
import carmine.pittella.home.service.UtenteService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class UtenteBean implements UtenteService {

    private final UtenteMapper utenteMapper;
    private final UtenteRepository utenteRepository;

    @Override
    @Transactional
    public void createUtente (UtenteDto newUtente) {
        utenteRepository.save(utenteMapper.toEntity(newUtente));
        log.info("Utente creato con successo --> USERNAME: {}, PASSWORD: {}", newUtente.getUsername(), newUtente.getPassword());
    }

    @Override
    public UtenteDto findByUsername (String username) {
        if (username == null || username.isBlank()) {
            throw new InternalServerErrorException("FIND_UTENTE", "username null");
        }
        return utenteMapper.toDto(utenteRepository.findByUsername(username));
    }

    @Override
    public List<UtenteDto> findAll () {
        return utenteMapper.toDtoList(utenteRepository.findAll().stream().toList());
    }
}
