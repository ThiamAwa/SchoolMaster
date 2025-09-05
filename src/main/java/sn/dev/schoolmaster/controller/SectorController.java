package sn.dev.schoolmaster.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.dev.schoolmaster.dao.ClasseRepository;
import sn.dev.schoolmaster.dto.ClasseDTO;
import sn.dev.schoolmaster.dto.SectorDTO;
import sn.dev.schoolmaster.entity.ClasseEntity;
import sn.dev.schoolmaster.entity.SectorEntity;
import sn.dev.schoolmaster.exception.DuplicateException;
import sn.dev.schoolmaster.exception.EntityNotFoundException;
import sn.dev.schoolmaster.service.ClasseService;
import sn.dev.schoolmaster.service.SectorService;


import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/sectors")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;
    private final ClasseService classeService;
    private final MessageSource messageSource;

    @GetMapping("/list")
    public String listSectors(Model model) {
        List<SectorDTO> sectors = sectorService.getAll();
        model.addAttribute("sectors", sectors);
        return "sectors/list";
    }
    // Formulaire pour créer ou modifier un secteur
    @GetMapping({"/form", "/form/{id}"})
    public String showForm(@PathVariable(required = false) Long id, Model model) {
        SectorDTO sector = (id != null)
                ? sectorService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Secteur introuvable - ID: " + id))
                : new SectorDTO();

        model.addAttribute("sector", sector); // ⚠️ le nom doit correspondre au th:object
        return "sectors/form";
    }



    //    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        SectorDTO sector = sectorService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("sector.notfound", new Object[]{id}, Locale.getDefault())
                ));
        model.addAttribute("sector", sector);
        model.addAttribute("classes", classeService.getAll()); // ✅ Change "allClasses" to "classes"
        return "sectors/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("sector") SectorDTO sectorDTO, RedirectAttributes redirectAttributes) {
        try {
            sectorService.save(sectorDTO);
            redirectAttributes.addFlashAttribute("success", "Secteur enregistré avec succès !");
        } catch (DuplicateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/sectors/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteSector(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            sectorService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Secteur supprimé avec succès !");

        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/sectors/list";
    }
//
//    @GetMapping("/view/{id}")
//    public String viewSector(@PathVariable Long id, Model model) {
//        SectorDTO sector = sectorService.getById(id)
//                .orElseThrow(() -> new EntityNotFoundException(
//                        messageSource.getMessage("sector.notfound", new Object[]{id}, Locale.getDefault())
//                ));
//        model.addAttribute("sector", sector);
//        return "sectors/detail";
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public String handleEntityNotFound(EntityNotFoundException ex, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("error", ex.getMessage());
//        return "redirect:/sectors";
//    }
}