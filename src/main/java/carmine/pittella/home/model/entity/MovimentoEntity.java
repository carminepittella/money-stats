package carmine.pittella.home.model.entity;

import carmine.pittella.home.model.enums.TipologiaEnum;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "MOVIMENTO",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_conto", "data", "importo", "titolo", "id_categoria"}))
public class MovimentoEntity extends PanacheEntityBase {

    public static final String ID = "id";
    public static final String DATA = "data";
    public static final String TIPOLOGIA = "tipologia";
    public static final String TITOLO = "titolo";
    public static final String IMPORTO = "importo";
    public static final String COMMENTO = "commento";
    public static final String CONTO = "conto";
    public static final String CATEGORIA = "categoria";
    public static final String HASHTAG = "hashtag";
    public static final String RICEVENTE = "ricevente";


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
    public Long importo;

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
