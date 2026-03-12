package carmine.pittella.home.mapper;

import carmine.pittella.home.model.dto.UtenteDto;
import carmine.pittella.home.model.entity.UtenteEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", builder = @Builder(disableBuilder = true))

public interface UtenteMapper extends BaseMappingMapstruct<UtenteDto, UtenteEntity> {
}
