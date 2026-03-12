package carmine.pittella.home.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "hashtag")
public class HashtagDto {

    private Long id;
    private String hashtag;
}
