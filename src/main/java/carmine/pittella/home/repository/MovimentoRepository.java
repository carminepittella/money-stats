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

    public DashboardStatsResponseDto getDashboardStats (MovimentiFilterRequestDto filter) {
        LocalDate dataInizio = filter.getDataInizio() != null ? filter.getDataInizio() : LocalDate.of(1900, 1, 1);
        LocalDate dataFine = filter.getDataFine() != null ? filter.getDataFine() : LocalDate.of(2100, 1, 1);

        String query = """
                SELECT
                    COALESCE(SUM(CASE WHEN m.importo > 0
                                      AND m.data BETWEEN :dataInizio AND :dataFine
                                      AND (:idCategoria IS NULL OR m.categoria.id = :idCategoria)
                                      AND (:idHashtag   IS NULL OR m.hashtag.id   = :idHashtag)
                                      AND (:idConto     IS NULL OR m.conto.id     = :idConto)
                                 THEN m.importo ELSE 0 END), 0) AS entrate,
                    COALESCE(SUM(CASE WHEN m.importo < 0
                                      AND m.data BETWEEN :dataInizio AND :dataFine
                                      AND (:idCategoria IS NULL OR m.categoria.id = :idCategoria)
                                      AND (:idHashtag   IS NULL OR m.hashtag.id   = :idHashtag)
                                      AND (:idConto     IS NULL OR m.conto.id     = :idConto)
                                 THEN m.importo ELSE 0 END), 0) AS uscite,
                    COALESCE(SUM(m.importo), 0)               AS saldo
                FROM MovimentoEntity m
                """;

        Object[] result = (Object[]) getEntityManager().createQuery(query)
                .setParameter("dataInizio", dataInizio.atStartOfDay())
                .setParameter("dataFine", dataFine.plusDays(1).atStartOfDay())
                .setParameter("idCategoria", filter.getIdCategoria())
                .setParameter("idHashtag", filter.getIdHashtag())
                .setParameter("idConto", filter.getIdConto())
                .getSingleResult();

        return DashboardStatsResponseDto.builder()
                .entrate(((Number) (result[0] != null ? result[0] : 0)).doubleValue())
                .uscite(((Number) (result[1] != null ? result[1] : 0)).doubleValue())
                .saldo(((Number) (result[2] != null ? result[2] : 0)).doubleValue())
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























