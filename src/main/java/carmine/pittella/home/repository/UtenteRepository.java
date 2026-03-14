package carmine.pittella.home.repository;

import carmine.pittella.home.exception.ConflictDatabaseException;
import carmine.pittella.home.model.entity.UtenteEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;

@Slf4j
@ApplicationScoped
public class UtenteRepository implements PanacheRepository<UtenteEntity> {

    public UtenteEntity save (UtenteEntity utente) {
        try {
            persistAndFlush(utente);
            return utente;
        } catch (ConstraintViolationException e) {
            String errorMsg = String.format("username: %s esiste già, scegli un altro username", utente.getUsername());
            throw new ConflictDatabaseException("UTENTE_DUPLICATO", errorMsg, e.getCause().getMessage());
        }
    }

    public UtenteEntity findByUsername (String username) {
        return find("username", username).firstResult();
    }
}
