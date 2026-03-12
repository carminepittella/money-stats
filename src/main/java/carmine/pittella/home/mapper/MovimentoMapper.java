package carmine.pittella.home.mapper;

import carmine.pittella.home.model.dto.MovimentoDto;
import carmine.pittella.home.model.entity.MovimentoEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta",
        builder = @Builder(disableBuilder = true),
        uses = {
                ContoMapper.class,
                UtenteMapper.class,
                HashtagMapper.class,
                CategoriaMapper.class
        }
)
public interface MovimentoMapper extends BaseMappingMapstruct<MovimentoDto, MovimentoEntity> {

    @Override
    MovimentoDto toDto (MovimentoEntity entity);

    @Override
    MovimentoEntity toEntity (MovimentoDto dto);
}