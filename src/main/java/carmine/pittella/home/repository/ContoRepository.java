package carmine.pittella.home.repository;

import carmine.pittella.home.model.entity.ContoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContoRepository implements PanacheRepository<ContoEntity> {
}
