package carmine.pittella.home.model.entity;

import carmine.pittella.home.model.enums.RuoloUtenteEnum;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "UTENTE", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
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

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo", nullable = false, length = 50)
    private RuoloUtenteEnum ruolo;
}
