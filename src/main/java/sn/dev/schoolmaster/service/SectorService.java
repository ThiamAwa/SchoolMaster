package sn.dev.schoolmaster.service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.dev.schoolmaster.dao.SectorRepository;
import sn.dev.schoolmaster.dto.SectorDTO;
import sn.dev.schoolmaster.entity.SectorEntity;
import sn.dev.schoolmaster.exception.DuplicateException;
import sn.dev.schoolmaster.exception.EntityNotFoundException;
import sn.dev.schoolmaster.mapper.SectorMapper;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Setter
public class SectorService implements ISectorService {

    private final SectorRepository sectorRepository;
    private final SectorMapper sectorMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public List<SectorDTO> getAll() {
        List<SectorEntity> sectors = sectorRepository.findAll();
        return sectorMapper.listSectorEntityToListSectorDto(sectors);
    }

    @Override
    @Transactional
    public SectorDTO save(SectorDTO sectorDTO) {
        String name = sectorDTO.getName();
        List<SectorEntity> existingSector = sectorRepository.findByName(name);


        SectorEntity entity = sectorMapper.toSectorEntity(sectorDTO);
        if (sectorDTO.getId() != null) {
            entity.setId(sectorDTO.getId());
        }

        SectorEntity saved = sectorRepository.save(entity);
        return sectorMapper.toSectorDto(saved);
    }

    @Override

    public Optional<SectorDTO> getById(Long id) {
        SectorEntity entity = sectorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("sector.notfound",
                                new Object[]{id}, Locale.getDefault())
                ));

        return Optional.of(sectorMapper.toSectorDto(entity));
    }


    @Override
    @Transactional
    public void delete(Long id) {
        SectorEntity entity = sectorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("sector.notfound", new Object[]{id}, Locale.getDefault())
                ));
        sectorRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SectorDTO> searchByName(String searchTerm) {
        List<SectorEntity> sectors = sectorRepository.findByNameContainingIgnoreCase(searchTerm);
        return sectors.stream()
                .map(sectorMapper::toSectorDto)
                .collect(Collectors.toList());
    }
}
