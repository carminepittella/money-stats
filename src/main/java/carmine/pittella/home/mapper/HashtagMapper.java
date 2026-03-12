package carmine.pittella.home.mapper;

import carmine.pittella.home.model.dto.HashtagDto;
import carmine.pittella.home.model.entity.HashtagEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", builder = @Builder(disableBuilder = true))

public interface HashtagMapper extends BaseMappingMapstruct<HashtagDto, HashtagEntity> {
}
