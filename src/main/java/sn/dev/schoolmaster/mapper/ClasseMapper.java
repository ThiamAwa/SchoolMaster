package sn.dev.schoolmaster.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sn.dev.schoolmaster.dto.ClasseDTO;
import sn.dev.schoolmaster.entity.ClasseEntity;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SectorMapper.class})
public interface ClasseMapper {
    @Mapping(source = "sector.id", target = "sectorId")
    ClasseDTO toClasseDto(ClasseEntity classeEntity);

    @Mapping(source = "sectorId", target = "sector.id")
    ClasseEntity toClasseEntity(ClasseDTO classeDTO);

    List<ClasseDTO> listClasseEntityToListClasseDto(List<ClasseEntity> classes);
    List<ClasseEntity> listClasseDtoToListClasseEntity(List<ClasseDTO> classes);


}

