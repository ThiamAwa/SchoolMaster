package sn.dev.schoolmaster.service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import sn.dev.schoolmaster.dao.ClasseRepository;
import sn.dev.schoolmaster.dto.ClasseDTO;
import sn.dev.schoolmaster.entity.ClasseEntity;
import sn.dev.schoolmaster.exception.DuplicateException;
import sn.dev.schoolmaster.exception.EntityNotFoundException;
import sn.dev.schoolmaster.mapper.ClasseMapper;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Setter

public class ClasseService implements IClasseService {
    private ClasseRepository classeRepository;
    private ClasseMapper classeMapper;
    private MessageSource messageSource;


    @Override
    public List<ClasseDTO> getAll() {
        List<ClasseEntity> classes = classeRepository.findAll();
        return classes.stream()
                .map(entity -> {
                    ClasseDTO dto = classeMapper.toClasseDto(entity);
                    if(entity.getSector() != null) {
                        dto.setSectorName(entity.getSector().getName());
                    } else {
                        dto.setSectorName("Non défini");
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }



    @Override
    public ClasseDTO save(ClasseDTO classeDTO) {
        String className = classeDTO.getClassName();
        Optional<ClasseEntity> existingClasse = classeRepository.findByClassName(className);

        if (existingClasse.isPresent()) {
            // Si c'est une mise à jour, ignorer si c'est la même classe
            if (classeDTO.getId() == null || !existingClasse.get().getId().equals(classeDTO.getId())) {
                throw new DuplicateException("Classe with the name '" + classeDTO.getClassName() + "' already exists.");
            }
        }
        ClasseEntity entity = classeMapper.toClasseEntity(classeDTO);
        ClasseEntity saved = classeRepository.save(entity);
        return classeMapper.toClasseDto(saved);
    }

    @Override
    public Optional<ClasseDTO> getById(Long id) {
        ClasseEntity entity = classeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("classe.notfound",
                                new Object[]{id}, Locale.getDefault())
                ));
        return Optional.of(classeMapper.toClasseDto(entity));
    }

    @Override
    public void delete(Long id) {
        ClasseEntity entity = classeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("classe.notfound",
                                new Object[]{id}, Locale.getDefault())
                ));
        classeRepository.delete(entity);
    }

    @Override
    public List<ClasseDTO> searchByClassName(String searchTerm) {
        if(searchTerm ==null || searchTerm.trim().isEmpty()){
            return  getAll();
        }
        List<ClasseEntity> foundClasses =classeRepository.findByClassNameContainingIgnoreCase(searchTerm.trim());
        return foundClasses.stream().map(classeMapper::toClasseDto).collect(Collectors.toList());
    }


}

