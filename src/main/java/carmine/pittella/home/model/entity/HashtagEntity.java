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
@EqualsAndHashCode(callSuper = false)
@Table(name = "HASHTAG", uniqueConstraints = @UniqueConstraint(columnNames = {"hashtag"}))
public class HashtagEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id_hashtag")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hashtag_seq")
    @SequenceGenerator(name = "hashtag_seq", sequenceName = "HASHTAG_SEQ")
    private Long id;

    @Column(name = "hashtag", nullable = false, length = 50)
    private String hashtag;
}
