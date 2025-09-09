package sn.dev.schoolmaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClasseDTO {
    private Long id;
    @NotBlank(message = "Le nom de la classe est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String className;

    @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
    private String description;
    private Long sectorId;
    private String sectorName;

}
