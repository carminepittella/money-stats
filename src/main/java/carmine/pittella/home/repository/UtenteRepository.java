package carmine.pittella.home.repository;

import carmine.pittella.home.model.entity.UtenteEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UtenteRepository implements PanacheRepository<UtenteEntity> {
}
