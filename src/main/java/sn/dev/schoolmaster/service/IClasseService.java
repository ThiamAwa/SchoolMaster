package sn.dev.schoolmaster.service;

import sn.dev.schoolmaster.dto.ClasseDTO;

import java.util.List;
import java.util.Optional;

public interface IClasseService {
    List<ClasseDTO> getAll();
    ClasseDTO save(ClasseDTO classeDTO);
    Optional<ClasseDTO> getById(Long id);
    void delete(Long id);
    List<ClasseDTO> searchByClassName(String searchTerm);
}
