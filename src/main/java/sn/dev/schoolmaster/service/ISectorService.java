package sn.dev.schoolmaster.service;

import sn.dev.schoolmaster.dto.SectorDTO;

import java.util.List;
import java.util.Optional;

public interface ISectorService {
    List<SectorDTO> getAll();

    SectorDTO save(SectorDTO sectorDTO);

    Optional<SectorDTO> getById(Long id);

    void delete(Long id);

    List<SectorDTO> searchByName(String searchTerm);
}
