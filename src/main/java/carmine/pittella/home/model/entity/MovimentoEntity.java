package carmine.pittella.home.model.entity;

import carmine.pittella.home.model.enums.TipologiaEnum;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "MOVIMENTO",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_conto", "data", "importo", "titolo", "id_categoria"}))
public class MovimentoEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id_movimento")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimento_seq")
    @SequenceGenerator(name = "movimento_seq", sequenceName = "SEQ_MOVIMENTO", allocationSize = 1)
    public Long id;

    @Column(name = "data", nullable = false)
    public LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipologia", nullable = false, length = 50)
    public TipologiaEnum tipologia;

    @Column(name = "titolo", nullable = false, length = 100)
    public String titolo;

    @Column(name = "importo", nullable = false, precision = 10, scale = 2)
    public BigDecimal importo;

    @Column(name = "commento")
    public String commento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conto", nullable = false)
    public ContoEntity conto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    public CategoriaEntity categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hashtag")
    public HashtagEntity hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ricevente")
    public UtenteEntity ricevente;

    @Override
    public String toString () {
        return "MovimentoEntity {" +
               " data=" + data +
               ", tipologia=" + tipologia +
               ", titolo='" + titolo +
               ", importo=" + importo +
               ", commento='" + commento +
               ", conto=" + conto +
               ", categoria=" + categoria +
               ", hashtag=" + hashtag +
               ", ricevente=" + ricevente +
               '}';
    }
}
