package carmine.pittella.home.repository;

import carmine.pittella.home.model.entity.UtenteEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.exception.ConstraintViolationException;

@ApplicationScoped
public class UtenteRepository implements PanacheRepository<UtenteEntity> {

    public UtenteEntity save (UtenteEntity utente) {
        try {
            persistAndFlush(utente);
            return utente;
        } catch (ConstraintViolationException e) {
            //TODO: eccezione custom
            return null;
        }
    }

    public UtenteEntity findByUsername (String username) {
        return find("username", username).firstResult();
    }
}
