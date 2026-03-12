package carmine.pittella.home.repository;

import carmine.pittella.home.model.entity.MovimentoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MovimentoRepository implements PanacheRepository<MovimentoEntity> {
}
