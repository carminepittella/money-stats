package carmine.pittella.home.bean;

import carmine.pittella.home.exception.BadRequestException;
import carmine.pittella.home.exception.InternalServerErrorException;
import carmine.pittella.home.exception.UnauthorizedException;
import carmine.pittella.home.model.dto.UtenteDto;
import carmine.pittella.home.model.dto.request.LoginRequestDto;
import carmine.pittella.home.model.dto.request.RegisterRequestDto;
import carmine.pittella.home.model.dto.response.LoginResponseDto;
import carmine.pittella.home.model.enums.RuoloUtenteEnum;
import carmine.pittella.home.service.AuthService;
import carmine.pittella.home.service.PasswordService;
import carmine.pittella.home.service.UtenteService;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class AuthBean implements AuthService {

    private final UtenteService utenteService;
    private final PasswordService passwordService;

    @Override
    public LoginResponseDto login (LoginRequestDto request) {
        // cerco l'utente per username
        UtenteDto utenteDto = utenteService.findByUsername(request.getUsername());
        if (utenteDto == null) {
            throw new UnauthorizedException("LOGIN_ERROR", "Username e/o password errati");
        }

        // verifico la password
        boolean passwordCorretta = passwordService.verifyPassword(request.getPassword(), utenteDto.getPassword());
        if (!passwordCorretta) {
            throw new UnauthorizedException("LOGIN_ERROR", "Username e/o password errati");
        }

        // genero il Token JWT
        String token;
        try {
            token = Jwt.issuer("carmine-home-app")
                    .upn(utenteDto.getUsername())
                    .groups(utenteDto.getRuolo().name())
//                    .claim("custom-info", "quello-che-vuoi")
                    .expiresIn(Duration.ofHours(6))
                    .sign();
        } catch (Exception e) {
            throw new InternalServerErrorException("TOKEN_GENERATION", "si è verificato un errore durante la generazione del Token", e.getMessage());
        }

        log.info("Login effettuato con successo per l'utente: {}", request.getUsername());
        return new LoginResponseDto(utenteDto.getNome(), utenteDto.getCognome(), utenteDto.getUsername(), utenteDto.getRuolo().name(), token);
    }

    @Override
    public LoginResponseDto register (RegisterRequestDto request) {
        // creo un nuovo utente
        utenteService.createUtente(UtenteDto.builder()
                .nome(request.getNome())
                .cognome(request.getCognome())
                .username(request.getUsername())
                .ruolo(RuoloUtenteEnum.GENERIC)
                .password(passwordService.cryptPassword(request.getPassword()))
                .build());

        // faccio la login direttamente
        return this.login(LoginRequestDto.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build());
    }
}
