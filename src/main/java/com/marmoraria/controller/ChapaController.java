package com.marmoraria.controller;

import com.marmoraria.entity.Chapa;
import com.marmoraria.service.ChapaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/chapas")
@RequiredArgsConstructor
public class ChapaController {
    private final ChapaService service;

    @GetMapping
    public String listar(@RequestParam(required = false) String material, Model model) {
        var chapas = service.listarTodas();
        if (material != null && !material.isBlank())
            chapas = chapas.stream().filter(c -> c.getMaterial().equalsIgnoreCase(material)).toList();
        model.addAttribute("chapas", chapas);
        model.addAttribute("materiais", service.listarMateriais());
        model.addAttribute("filtroMaterial", material);
        return "chapas/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("chapa", new Chapa());
        model.addAttribute("titulo", "Nova Chapa");
        model.addAttribute("materiais", service.listarMateriais());
        return "chapas/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("chapa", service.buscarPorId(id));
        model.addAttribute("titulo", "Editar Chapa");
        model.addAttribute("materiais", service.listarMateriais());
        return "chapas/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Chapa chapa, BindingResult result,
                         Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", chapa.getId() == null ? "Nova Chapa" : "Editar Chapa");
            model.addAttribute("materiais", service.listarMateriais());
            return "chapas/formulario";
        }
        service.salvar(chapa);
        ra.addFlashAttribute("sucesso", "Chapa salva com sucesso!");
        return "redirect:/chapas";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes ra) {
        try { service.deletar(id); ra.addFlashAttribute("sucesso", "Chapa removida!");
        } catch (Exception e) { ra.addFlashAttribute("erro", "Não foi possível remover."); }
        return "redirect:/chapas";
    }
}
