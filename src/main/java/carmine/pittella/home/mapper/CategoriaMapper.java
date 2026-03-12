package carmine.pittella.home.mapper;

import carmine.pittella.home.model.dto.CategoriaDto;
import carmine.pittella.home.model.entity.CategoriaEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta", builder = @Builder(disableBuilder = true))

public interface CategoriaMapper extends BaseMappingMapstruct<CategoriaDto, CategoriaEntity> {
}
