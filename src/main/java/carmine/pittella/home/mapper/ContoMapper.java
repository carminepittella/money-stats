package carmine.pittella.home.mapper;

import carmine.pittella.home.model.dto.ContoDto;
import carmine.pittella.home.model.entity.ContoEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", builder = @Builder(disableBuilder = true))

public interface ContoMapper extends BaseMappingMapstruct<ContoDto, ContoEntity> {
}
