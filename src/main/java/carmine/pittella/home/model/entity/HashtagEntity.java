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
@Table(name = "HASHTAG", uniqueConstraints = @UniqueConstraint(columnNames = {"hashtag"}))
public class HashtagEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id_hashtag")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hashtag_seq")
    @SequenceGenerator(name = "hashtag_seq", sequenceName = "HASHTAG_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "hashtag", nullable = false, length = 50)
    private String hashtag;
}
