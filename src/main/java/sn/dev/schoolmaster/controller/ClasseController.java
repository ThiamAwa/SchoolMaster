package sn.dev.schoolmaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.dev.schoolmaster.dto.ClasseDTO;
import sn.dev.schoolmaster.dto.SectorDTO;
import sn.dev.schoolmaster.exception.EntityNotFoundException;
import sn.dev.schoolmaster.service.IClasseService;
import sn.dev.schoolmaster.service.ISectorService;

import java.util.List;

@Controller
@RequestMapping("/classes")
public class ClasseController {

    @Autowired
    private IClasseService classeService;

    @Autowired
    private ISectorService sectorService;

    // Afficher toutes les classes avec option de filtrage
    @GetMapping("/list")
    public String listClasses(
            @RequestParam(value = "search", required = false) String searchTerm,
            Model model) {

        List<ClasseDTO> classes;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            classes = classeService.searchByClassName(searchTerm);
        } else {
            classes = classeService.getAll();
        }

        model.addAttribute("classes", classes);
        model.addAttribute("searchTerm", searchTerm);
//        model.addAttribute("content", "classes/list :: classes-content");
        model.addAttribute("content", "classes/list");

        return "classes/list";
    }

    // Formulaire pour ajouter/modifier une classe
    @GetMapping({"/form", "/form/{id}"})
    public String showForm(@PathVariable(required = false) Long id,
                           Model model) {

        // Récupération de la classe existante ou création nouvelle
        ClasseDTO classe = id != null
                ? classeService.getById(id)

                .orElseThrow(() -> new EntityNotFoundException("Classe introuvable - ID: " + id))
                : new ClasseDTO();

        // ✅ Récupérer les secteurs pour affichage dans le <select>
        List<SectorDTO> sectors = sectorService.getAll();

        model.addAttribute("classe", classe);
        model.addAttribute("sectors", sectors);

        return "classes/form";
    }

    // Sauvegarde d'une nouvelle classe
    @PostMapping("/save")
    public String saveClasse(@ModelAttribute("classe") ClasseDTO classeDTO,
                             @RequestParam("sectorId") Long sectorId,
                             RedirectAttributes redirectAttributes) {

        classeDTO.setSectorId(sectorId);
        classeService.save(classeDTO);

        redirectAttributes.addFlashAttribute("successMessage", "Classe enregistrée avec succès !");
        return "redirect:/classes/list";
    }



    // Mise à jour d'une classe existante
    @PostMapping("/update/{id}")
    public String updateClasse(@PathVariable Long id,
                               @ModelAttribute("classe") ClasseDTO classeDTO,
                               @RequestParam("sectorId") Long sectorId) {
        classeDTO.setId(id);
        classeDTO.setSectorId(sectorId);
        classeService.save(classeDTO);
        return "redirect:/classes/list";
    }

    // Supprimer une classe
    @GetMapping("/delete/{id}")
    public String deleteClasse(@PathVariable Long id) {
        classeService.delete(id);
        return "redirect:/classes/list";
    }

    @GetMapping("/details/{id}")
    public String showClassDetails(@PathVariable Long id, Model model) {
        ClasseDTO classe = classeService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée avec ID: " + id));

        model.addAttribute("classe", classe);
        return "classes/details";
    }



}
