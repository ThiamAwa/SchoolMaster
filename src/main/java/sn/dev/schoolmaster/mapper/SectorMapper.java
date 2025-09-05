package sn.dev.schoolmaster.mapper;

import org.mapstruct.*;
import sn.dev.schoolmaster.dto.SectorDTO;
import sn.dev.schoolmaster.entity.ClasseEntity;
import sn.dev.schoolmaster.entity.SectorEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectorMapper {
    // ⬅️ Ajoutez cette ligne
    @Mapping(source = "classes", target = "classes")
    SectorDTO toSectorDto(SectorEntity sectorEntity);

    @Mapping(source = "classes", target = "classes")
    SectorEntity toSectorEntity(SectorDTO sectorDTO);

    List<SectorDTO> listSectorEntityToListSectorDto(List<SectorEntity> sectors);
    List<SectorEntity> listSectorDtoToListSectorEntity(List<SectorDTO> sectors);

}

