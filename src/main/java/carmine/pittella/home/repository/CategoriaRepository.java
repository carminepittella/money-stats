package carmine.pittella.home.repository;

import carmine.pittella.home.model.entity.CategoriaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<CategoriaEntity> {

    public CategoriaEntity findOrCreate (String categoria) {
        CategoriaEntity exists = find("categoria", categoria).firstResult();
        if (exists != null) return exists;

        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoria(categoria);
        persist(categoriaEntity);
        return categoriaEntity;
    }

    public List<CategoriaEntity> findAllSorted() {
        return CategoriaEntity.list("order by categoria asc");
    }
}
