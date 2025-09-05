package sn.dev.schoolmaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SectorDTO {
    private Long id;

    @NotBlank(message = "Le nom du secteur est obligatoire")
    private String name;


    private List<ClasseDTO> classes;



}
