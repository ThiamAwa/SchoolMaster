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
                .map(classeMapper::toClasseDto) // MapStruct ou votre mapper
                .collect(Collectors.toList());

    }


    @Override
    public ClasseDTO save(ClasseDTO classeDTO) {
        String className = classeDTO.getClassName();
        Optional<ClasseEntity> existingClasse = classeRepository.findAll().stream()
                .filter(c -> c.getClassName().equalsIgnoreCase(className))
                .findFirst();

        if (existingClasse.isPresent()) {
            throw new DuplicateException(
                    String.format("Classe with the name '%s' already exists.", className)
            );
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

