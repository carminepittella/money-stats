package carmine.pittella.home.mapper;

import java.util.List;

public interface BaseMappingMapstruct<DTO, ENTITY> {

    DTO toDto (ENTITY entity);

    ENTITY toEntity (DTO dto);

    List<DTO> toDtoList (List<ENTITY> entityList);

    List<ENTITY> toEntityList (List<DTO> dtoList);
}
