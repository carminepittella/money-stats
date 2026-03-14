package carmine.pittella.home.bean;

import carmine.pittella.home.exception.InternalServerErrorException;
import carmine.pittella.home.mapper.UtenteMapper;
import carmine.pittella.home.model.dto.UtenteDto;
import carmine.pittella.home.model.dto.request.UtenteCreateRequestDto;
import carmine.pittella.home.repository.UtenteRepository;
import carmine.pittella.home.service.PasswordService;
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
    private final PasswordService passwordService;

    @Override
    @Transactional
    public UtenteDto createUtente (UtenteCreateRequestDto newUtente) {
        UtenteDto utenteDto = UtenteDto.builder()
                .nome(newUtente.getNome())
                .cognome(newUtente.getCognome())
                .username(newUtente.getUsername())
                .password(passwordService.cryptPassword(newUtente.getPassword()))
                .ruolo(newUtente.getRuolo())
                .build();

        utenteDto = utenteMapper.toDto(utenteRepository.save(utenteMapper.toEntity(utenteDto)));

        log.info("Utente creato con successo --> USERNAME: {}, PASSWORD: {}", newUtente.getUsername(), newUtente.getPassword());
        return utenteDto;
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
