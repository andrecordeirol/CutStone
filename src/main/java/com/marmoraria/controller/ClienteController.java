package com.marmoraria.controller;

import com.marmoraria.entity.Cliente;
import com.marmoraria.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService service;

    @GetMapping
    public String listar(@RequestParam(required = false) String busca, Model model) {
        model.addAttribute("clientes", busca != null && !busca.isBlank()
                ? service.buscarPorNome(busca) : service.listarTodos());
        model.addAttribute("busca", busca);
        return "clientes/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Novo Cliente");
        return "clientes/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", service.buscarPorId(id));
        model.addAttribute("titulo", "Editar Cliente");
        return "clientes/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Cliente cliente, BindingResult result,
                         Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", cliente.getId() == null ? "Novo Cliente" : "Editar Cliente");
            return "clientes/formulario";
        }
        service.salvar(cliente);
        ra.addFlashAttribute("sucesso", "Cliente salvo com sucesso!");
        return "redirect:/clientes";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", service.buscarPorId(id));
        return "clientes/detalhe";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes ra) {
        try { service.deletar(id); ra.addFlashAttribute("sucesso", "Cliente removido!");
        } catch (Exception e) { ra.addFlashAttribute("erro", "Não foi possível remover o cliente."); }
        return "redirect:/clientes";
    }
}
