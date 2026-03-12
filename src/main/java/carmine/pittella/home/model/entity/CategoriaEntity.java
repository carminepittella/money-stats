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
@Table(name = "CATEGORIA", uniqueConstraints = @UniqueConstraint(columnNames = {"categoria"}))
public class CategoriaEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id_categoria")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_seq")
    @SequenceGenerator(name = "categoria_seq", sequenceName = "CATEGORIA_SEQ")
    private Long id;

    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;
}
