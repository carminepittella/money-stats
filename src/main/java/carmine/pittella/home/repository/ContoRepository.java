package carmine.pittella.home.repository;

import carmine.pittella.home.model.entity.ContoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
public class ContoRepository implements PanacheRepository<ContoEntity> {

    public ContoEntity findOrCreate (String contoStr) {
        ContoEntity exists = find("descrizione", contoStr).firstResult();
        if (exists != null) return exists;

        ContoEntity nuovoConto = new ContoEntity();
        nuovoConto.setDescrizione(contoStr);
        persist(nuovoConto);
        return nuovoConto;
    }

    public List<ContoEntity> findAllSorted () {
        return ContoEntity.list("order by descrizione asc");
    }
}
