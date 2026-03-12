package carmine.pittella.home.repository;

import carmine.pittella.home.model.entity.HashtagEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HashtagRepository implements PanacheRepository<HashtagEntity> {
}
