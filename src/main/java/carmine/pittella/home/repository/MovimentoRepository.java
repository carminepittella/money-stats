package carmine.pittella.home.repository;

import carmine.pittella.home.model.dto.request.MovimentiFilterRequestDto;
import carmine.pittella.home.model.dto.response.DashboardStatsResponseDto;
import carmine.pittella.home.model.entity.CategoriaEntity;
import carmine.pittella.home.model.entity.ContoEntity;
import carmine.pittella.home.model.entity.HashtagEntity;
import carmine.pittella.home.model.entity.MovimentoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public DashboardStatsResponseDto getDashboardStats (LocalDate dataInizio, LocalDate dataFine) {
        String query = """
                SELECT
                	sum(CASE WHEN m.importo > 0 AND m.data BETWEEN :dataInizio AND :dataFine THEN m.importo ELSE 0 END) AS entrate,
                	sum(CASE WHEN m.importo < 0 AND m.data BETWEEN :dataInizio AND :dataFine THEN m.importo ELSE 0 END) uscite,
                	sum(m.importo) AS saldo
                FROM MovimentoEntity m
                """;

        Object[] result = (Object[]) getEntityManager().createQuery(query)
                .setParameter("dataInizio", dataInizio.atStartOfDay())
                .setParameter("dataFine", dataFine.atStartOfDay())
                .getSingleResult();

        return DashboardStatsResponseDto.builder()
                .entrate(((Number) result[0]).doubleValue())
                .uscite(((Number) result[1]).doubleValue())
                .saldo(((Number) result[2]).doubleValue())
                .build();
    }

    public List<MovimentoEntity> findAllFiltered (MovimentiFilterRequestDto filtri) {
        var cb = getEntityManager().getCriteriaBuilder();
        var cq = cb.createQuery(MovimentoEntity.class);
        var root = cq.from(MovimentoEntity.class);
        var predicates = new ArrayList<Predicate>();

        // applica filtri
        LocalDate dataInizio = filtri.getDataInizio();
        LocalDate dataFine = filtri.getDataFine();
        Long idCategoria = filtri.getIdCategoria();
        Long idConto = filtri.getIdConto();
        Long idHashtag = filtri.getIdHashtag();

        if (dataInizio != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(MovimentoEntity.DATA), dataInizio));
        }
        if (dataFine != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(MovimentoEntity.DATA), dataFine));
        }
        if (idCategoria != null) {
            predicates.add(cb.equal(root.get(MovimentoEntity.CATEGORIA).get(CategoriaEntity.ID), idCategoria));
        }
        if (idConto != null) {
            predicates.add(cb.equal(root.get(MovimentoEntity.CONTO).get(ContoEntity.ID), idConto));
        }
        if (idHashtag != null) {
            predicates.add(cb.equal(root.get(MovimentoEntity.HASHTAG).get(HashtagEntity.ID), idHashtag));
        }

        // ordinamento
        List<Order> ordinamento = new ArrayList<>();
        ordinamento.add(cb.desc(root.get(MovimentoEntity.DATA)));
        ordinamento.add(cb.desc(root.get(MovimentoEntity.TITOLO)));

        // eseguo query
        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(ordinamento);
        return getEntityManager().createQuery(cq).getResultList();
    }
}























