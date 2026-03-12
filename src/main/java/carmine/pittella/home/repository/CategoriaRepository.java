package carmine.pittella.home.repository;

import carmine.pittella.home.model.entity.CategoriaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<CategoriaEntity> {
}
