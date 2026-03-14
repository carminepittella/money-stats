package carmine.pittella.home.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "CONTO", uniqueConstraints = @UniqueConstraint(columnNames = {"descrizione"}))
public class ContoEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id_conto")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conto_seq")
    @SequenceGenerator(name = "conto_seq", sequenceName = "SQC_CONTO", allocationSize = 1)
    private Long id;

    @Column(name = "descrizione", nullable = false, length = 50)
    private String descrizione;
}
