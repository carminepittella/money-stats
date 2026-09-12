package carmine.pittella.home.repository;

import carmine.pittella.home.exception.ConflictDatabaseException;
import carmine.pittella.home.model.entity.UtenteEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class UtenteRepository implements PanacheRepository<UtenteEntity> {

    public void save (UtenteEntity utente) {
        UtenteEntity exists = find("username", utente.getUsername()).firstResult();
        if (exists == null) {
            persist(utente);
        } else {
            String errorMsg = String.format("username: %s esiste già, scegli un altro username", utente.getUsername());
            throw new ConflictDatabaseException("UTENTE_DUPLICATO", errorMsg);
        }
    }

    public UtenteEntity findByUsername (String username) {
        return find("username", username).firstResult();
    }
}
