package carmine.pittella.home.repository;

import carmine.pittella.home.model.entity.MovimentoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;

@Slf4j
@ApplicationScoped
public class MovimentoRepository implements PanacheRepository<MovimentoEntity> {

    public boolean save (MovimentoEntity movimento) {
        try {
            persistAndFlush(movimento);
        } catch (ConstraintViolationException e) {
            log.error("Movimento già presente rilevato\n{}\n{}", e.getMessage(), movimento);
            return false;
        }
        return true;
    }
}
