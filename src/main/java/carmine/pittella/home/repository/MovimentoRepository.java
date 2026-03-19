package carmine.pittella.home.repository;

import carmine.pittella.home.model.entity.MovimentoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
public class MovimentoRepository implements PanacheRepository<MovimentoEntity> {

    public void save (MovimentoEntity m) {
        MovimentoEntity exists = find("conto=?1 AND data=?2 AND importo=?3 AND titolo=?4 AND categoria=?5",
                m.getConto(), m.getData(), m.getImporto(), m.getTitolo(), m.getCategoria()).firstResult();

        if (exists == null) persist(m);
        else log.error("Movimento già presente rilevato: {}", m);
    }

    public void saveAll (List<MovimentoEntity> entityList) {
        entityList.forEach(this::save);
    }
}
