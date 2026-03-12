package carmine.pittella.home.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "UTENTE", uniqueConstraints = @UniqueConstraint(columnNames = {"nome", "cognome"}))
public class UtenteEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id_utente")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utente_seq")
    @SequenceGenerator(name = "utente_seq", sequenceName = "SEQ_UTENTE", allocationSize = 1)
    private Long id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "cognome", nullable = false, length = 50)
    private String cognome;
}
